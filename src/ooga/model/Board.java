package ooga.model;

import ooga.Location;
import ooga.Turn;
import ooga.controller.BoardBuilder;
import ooga.controller.InvalidPieceConfigException;
import ooga.model.EndConditionHandler.EndConditionInterface;
import ooga.model.Moves.Move;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import ooga.Location;
import ooga.Turn;
import ooga.controller.BoardBuilder;
import ooga.controller.InvalidPieceConfigException;
import ooga.model.EndConditionHandler.EndConditionInterface;
import ooga.model.Moves.Move;

//import static ooga.controller.BoardBuilder.DEFAULT_CHESS_CONFIGURATION;

public class Board implements Engine {
    private static final int Rows = 8;
    private static final int Cols = 8;

    public enum GameState {
        RUNNING,
        CHECKMATE,
        STALEMATE,
        CHECK
    }

    private List<PlayerInterface> players;
    private List<PieceInterface> allPieces;
    private EndConditionInterface endCondition;
    private int turnCount;
    private PlayerInterface currentPlayer;

    public Board(List<PlayerInterface> players) {
        this.players = players;
        turnCount = 0;
        allPieces = new ArrayList<>();
        for(PlayerInterface player : players) {
            allPieces.addAll(player.getPieces());
        }
        System.out.println(this);

        for (PieceInterface piece : allPieces){
            piece.updateMoves(allPieces);
        }
        System.out.println(this);
        updateLegalMoves();
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
    public Turn movePiece(Location start, Location end) {
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

//        for(Move move : piece.getMove(end)) {
//            System.out.println(move.getClass());
//            turn.addTurn(move.getTurn());
//            move.executeMove(piece, allPieces, end);
//        }

        System.out.println(this);

        // remove piece from player if needed after turn
        for(Location removeLocation : turn.getRemoved()){
            for(PlayerInterface player : players){
                for(PieceInterface p : player.getPieces()){
                    if (p.getLocation().equals(removeLocation)) {
                        player.removePiece(p);
                    }
                }
            }
        }

//        //Check for pawn promotion
//        if(piece.getName().equals("P")){
//            if(end.getRow() == 0 || end.getRow() ==Rows-1){
//                System.out.println("pawn at end");
//                promotePiece(piece);
//            }
//        }

        // increment turn
        turnCount++;
        toggleTimers();
        updateLegalMoves();
        System.out.println(findPlayerTurn(turnCount).getTeam() + " turn");
        return turn;
    }

    /**
     * pause current player timer, add increment, start next player time
     */
    private void toggleTimers() {
        PlayerInterface currentPlayer = findPlayerTurn(turnCount-1);
        PlayerInterface nextPlayer = findPlayerTurn(turnCount);
        currentPlayer.toggleTimer();
        currentPlayer.incrementTime();
        nextPlayer.toggleTimer();
    }


//    private void promotePiece(PieceInterface pieceInterface) {
////        //need to initial
////        PlayerInterface currentPlayer = null;
////        for(PlayerInterface playerInterface: players){
////            if(playerInterface.getTeam().equals(pieceInterface.getTeam())){
////                currentPlayer = playerInterface;
////            }
////        }
//        BoardBuilder builder = new BoardBuilder();
//        PieceInterface newPiece = null;
//        try {
//            newPiece = builder.convertPiece(pieceInterface,"Q");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (InvalidPieceConfigException e) {
//            e.printStackTrace();
//        }
//        try {
//            Field f = newPiece.getClass().getDeclaredField("moves");
//            f.setAccessible(true);
//            List<Move> moves = (List<Move>) f.get(newPiece);
//            System.out.println(moves);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//
//        System.out.println(currentPlayer.getScore());
//        System.out.println("^ Score before removing");
//
//
//        currentPlayer.removePiece(pieceInterface);
//        allPieces.remove(pieceInterface);
//        allPieces.add(newPiece);
//        System.out.println(currentPlayer.getScore());
//        System.out.println("^Score after removing");
//
//        System.out.println("Ne piece name: " + newPiece.getName());
//        currentPlayer.addPiece(newPiece);
//        System.out.println("New piece team: " + newPiece.getTeam());
//        System.out.println("^Score with new piece added");
//        System.out.println(currentPlayer.getScore());
//        System.out.println("Current player team" + currentPlayer.getTeam());
//        for (PieceInterface p : currentPlayer.getPieces()){
//            System.out.println(p.getName());
//        }
//    }

//    private void promotePiece2(PieceInterface pieceInterface){
//        //Should make currentPlayer a private reference variable instead
//        PlayerInterface currentPlayer = null;
//        for(PlayerInterface playerInterface: players){
//            if(playerInterface.getTeam().equals(pieceInterface.getTeam())){
//                currentPlayer = playerInterface;
//            }
//        }
//
//    }


    /**
     * see if the game is still running or if its over
     * @return
     */
    @Override
    public GameState checkGameState() {
        if (endCondition.isGameOver(players).equals(GameState.CHECKMATE)){
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
//        for(PieceInterface piece : findPlayerTurn(turnCount).getPieces()) {
//            if(piece.getLocation().equals(location)) {
//                return true;
//            }
//        }
        return false;
    }

    private PlayerInterface findPlayerTurn(int turn) {
        currentPlayer = players.get((turn+1) % players.size());
        //FIXME: check if you can return this
//        return currentPlayer;
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
        str.append("____________________________________\n");
        return str.toString();
    }
}
