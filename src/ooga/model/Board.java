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
        updateLegalMoves();
    }

    private void updateLegalMoves() {
        allPieces = new ArrayList<>();
        for(PlayerInterface player : players) {
            for(PieceInterface piece : player.getPieces()){
                player.setLegalMoves(piece, piece.getAllMoves());
                allPieces.addAll(player.getPieces());
            }
        }
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
            }
        }

        Move move = piece.getMove(end);
        allPieces = move.executeMove(piece, allPieces, end);

        // increment turn
        turnCount++;

        // update legal moves
        updateLegalMoves();

        return move.getTurn();
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
        return findPlayerTurn(turnCount).getLegalMoves(location);
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
}
