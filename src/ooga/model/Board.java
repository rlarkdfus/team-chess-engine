package ooga.model;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import ooga.Location;
import ooga.Turn;
import ooga.controller.InvalidPieceConfigException;
import ooga.model.EndConditionHandler.EndConditionInterface;
import ooga.model.Moves.Move;

public class Board implements Engine {
private static final int Rows = 8;
private static final int Cols = 8;
private static final int lastRow = Rows -1;
private static final int firstRow = 0;


    public enum GameState {
        RUNNING,
        CHECKMATE,
        STALEMATE,
    }

    private List<PlayerInterface> players;
    private List<PieceInterface> allPieces;
    private EndConditionInterface endCondition;
    private int turnCount;
    private PlayerInterface currentPlayer;
    private List<Location> promotionSquares;
    private GameState currGameState;

    public Board(List<PlayerInterface> players) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        this.players = players;
        turnCount = 0;
        allPieces = new ArrayList<>();
        for(PlayerInterface player : players) {
            allPieces.addAll(player.getPieces());
        }

        for (PieceInterface piece : allPieces){
            piece.updateMoves(allPieces);
        }
        System.out.println(this);
        updateLegalMoves();
        promotionSquares = new ArrayList<>();
      initializePromotionSquares();
    }

    private void initializePromotionSquares() {
    }
    /**
     * this method returns the list of all players
     * @return
     */
    public List<PlayerInterface> getPlayers() {
        return players;
    }

    /**
     * this method sets the end conditions of the board
     * @param endCondition
     */
    public void setEndCondition(EndConditionInterface endCondition) {
        this.endCondition = endCondition;
    }

    private void updateLegalMoves() {
        for(PieceInterface piece : allPieces) {
            piece.updateMoves(new ArrayList<>(allPieces));
        }
    }

    /**
     * Moves piece from start to end and updates the board
     * @param start is piece initial location
     * @param end is piece new location
     */
    public Turn movePiece(Location start, Location end) throws FileNotFoundException, InvocationTargetException, InvalidPieceConfigException, NoSuchMethodException, IllegalAccessException {
        // pause current player timer, start next player time
        PieceInterface piece = null;
        for(PieceInterface p : allPieces) {
            if(p.getLocation().equals(start)) {
                piece = p;
                break;
            }
        }

        Move move = piece.getMove(end);
        Turn turn = move.getTurn();
        move.executeMove(piece, allPieces, end);

        // remove piece from player if needed after turn
        for(Location removeLocation : turn.getRemoved()){
            for(PlayerInterface player : players){
                for(PieceInterface p : player.getPieces()){
                    if (p.getLocation().equals(removeLocation) && !p.equals(piece)) {
                        player.removePiece(p);
                    }
                }
            }
        }


        //Check for pawn promotion
        checkPromotion(piece, end);

        // increment turn
        turnCount++;
        toggleTimers();

        //update game data
        updateLegalMoves();
        currGameState = endCondition.isGameOver(players);

        return turn;
    }

    private void checkPromotion(PieceInterface piece, Location end) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        //check pawn promotion specifically

        checkPawnPromotion(piece, end);

        //Check piece promotion specifically
        for(Location promotionLocation: promotionSquares){
            if(end.equals(promotionLocation)){
                promotePiece(piece);
            }
        }
    }
    private void checkPawnPromotion(PieceInterface piece, Location end) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        if(piece.getName().equals("P")){
            if(end.getRow() == firstRow || end.getRow() ==lastRow){
                System.out.println("pawn at end");
                promotePiece(piece);
            }
        }
    }

    /**
     * pause current player timer, add increment, start next player time
     */
    private void toggleTimers() {
        PlayerInterface prevPlayer = findPlayerTurn(turnCount-1);
        prevPlayer.toggleTimer();
        prevPlayer.incrementTime();
        currentPlayer.toggleTimer();
    }

    private void promotePiece(PieceInterface pieceInterface) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException{
        PieceInterface newPiece = currentPlayer.createQueen();
        newPiece.moveTo(pieceInterface.getLocation());

        currentPlayer.removePiece(pieceInterface);
        allPieces.remove(pieceInterface);

        allPieces.add(newPiece);
        currentPlayer.addPiece(newPiece);

    }




    /**
     * see if the game is still running or if its over
     * @return
     */
    @Override
    public GameState checkGameState() {
        if (currGameState == GameState.CHECKMATE){
            return GameState.CHECKMATE;
        }
        int totalLegalMoves = 0;
        for (PieceInterface piece : findPlayerTurn(turnCount).getPieces()){
            totalLegalMoves += getLegalMoves(piece.getLocation()).size();
        }

        if (totalLegalMoves == 0){
            return GameState.STALEMATE;
        }
        // game still going
        return GameState.RUNNING;
    }

    /**
     * return a list of all legal moves for a piece at a location
     * @param location
     * @return
     */
    public List<Location> getLegalMoves(Location location){
        for(PieceInterface piece : allPieces) {
            if(piece.getLocation().equals(location)) {
//                System.out.println(piece.getName() + " " + piece.getTeam());
                return piece.getEndLocations();
            }
        }
        return null;
    }

    @Override
    public String getWinner() {
        if (currGameState == GameState.CHECKMATE){
            return endCondition.getWinner();
        }
        return null;
    }


        /**
         * determine whether player selects their own piece on their turn
         * @param location
         * @return
         */
    public boolean canMovePiece(Location location) {
        String turn = findPlayerTurn(turnCount).getTeam();
        for(PieceInterface piece : allPieces) {
            if(piece.getTeam().equals(turn) && piece.getLocation().equals(location)) {
                return true;
            }
        }

        return false;
    }

    private PlayerInterface findPlayerTurn(int turn) {
        currentPlayer = players.get((turn) % players.size());

        return players.get(turn % players.size());
    }

    /**
     * this method overrides toString and prints out the current board state in an easily digestable format
     * @return
     */
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("\t 0\t 1\t 2\t 3\t 4\t 5\t 6\t 7\n");
        for(int i = 0; i < 8; i++) {
            str.append(i+"\t|");
//            str.append("|");
            for(int j = 0; j < 8; j++) {
                Location location = new Location(i, j);
                boolean found = false;

                for(PieceInterface piece : allPieces) {
                    if(piece.getLocation().equals(location)) {
                        str.append(piece.toString() + "\t");
                        found = true;
                    }
                }
                if(!found){
                    str.append("\t");
                }
                str.append("|");
            }
            str.append("\n");
        }
        str.append("__________________________________\n");
        return str.toString();
    }
}
