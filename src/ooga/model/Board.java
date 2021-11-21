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
        List<PieceInterface> allPiecesCopy = new ArrayList<>(allPieces);
        for(PieceInterface piece : allPiecesCopy) {
            piece.updateMoves(allPieces);
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
                break;
            }
        }

        Move move = piece.getMove(end);
        Turn turn = move.getTurn();
        allPieces = new ArrayList<>(move.executeMove(piece, allPieces, end));

        // increment turn
        turnCount++;

        updateLegalMoves();

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
}
