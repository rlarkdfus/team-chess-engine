package ooga.model;

import ooga.Location;
import ooga.Turn;
import ooga.model.Moves.Move;

import java.util.ArrayList;
import java.util.List;

public class Board implements Engine {

    public enum GameState {
        RUNNING,
        CHECKMATE,
        STALEMATE
    };

    private List<PlayerInterface> players;
    private List<PieceInterface> allPieces;
    private int turnCount;

    public Board(List<PlayerInterface> players) {
        this.players = players;
        turnCount = 0;
        allPieces = new ArrayList<>();
        for(PlayerInterface player : players) {
            allPieces.addAll(player.getPieces());
        }
        updateLegalMoves();
    }

    private void updateLegalMoves() {
        for(PieceInterface piece : allPieces) {
            piece.updateMoves(new ArrayList<>(allPieces));
        }

//        for(Iterator<PieceInterface> iterator = allPieces.iterator(); iterator.hasNext();){
//            iterator.next().updateMoves(allPieces);
//        }
    }

    /**
     * Moves piece from start to end and updates the board
     * @param start is piece initial location
     * @param end is piece new location
     */
    public Turn movePiece(Location start, Location end){
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
                    if (p.getLocation().equals(removeLocation)) {
                        player.removePiece(p);
                    }
                }
            }
        }

        // increment turn
        turnCount++;


//        System.out.println("before");
//        System.out.println(findPlayerTurn(turnCount).getTeam() + " turn");
//        System.out.println(this);

        updateLegalMoves();

//        System.out.println("after");
        System.out.println(findPlayerTurn(turnCount).getTeam() + " turn");
        System.out.println(this);

        return turn;
    }

    /**
     * see if the game is still running or if its over
     * @return
     */
    @Override
    public GameState checkGameState() {
        for(PlayerInterface player : players) {
            PlayerInterface otherPlayer = findPlayerTurn(turnCount + 1);
            int legalMovesCount = 0;
            for(PieceInterface piece : player.getPieces()) {
                legalMovesCount += player.getLegalMoves(piece.getLocation()).size();
            }

            if(legalMovesCount == 0) {
                //checkmate
                return null;
//                return (moveFactory.inCheck(player.getKing(), otherPlayer.getPieces())) ? GameState.CHECKMATE : GameState.STALEMATE;
            }
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
        for(PieceInterface piece : findPlayerTurn(turnCount).getPieces()) {
            if(piece.getLocation().equals(location)) {
                return true;
            }
        }
        return false;
    }

    private PlayerInterface findPlayerTurn(int turn) {
        return players.get(turn % players.size());
    }

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
