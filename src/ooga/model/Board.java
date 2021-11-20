package ooga.model;

import ooga.Location;
import ooga.Turn;

import java.util.List;

public class Board implements Engine {

    public enum GameState {
        RUNNING,
        CHECKMATE,
        STALEMATE
    };

    private MoveFactory moveFactory;
    private List<PlayerInterface> players;
    private int turnCount;

    public Board(List<PlayerInterface> players) {
        this.players = players;
        moveFactory = new MoveFactory(players, 8, 8); //FIXME
        turnCount = 0;
        updateLegalMoves();
    }

    private void updateLegalMoves() {
        for(PlayerInterface player : players) {
            for(PieceInterface piece : player.getPieces()){
                player.setLegalMoves(piece, moveFactory.findLegalMoves(player, piece));
            }
        }
    }

    /**
     * Moves piece from start to end and updates the board
     * @param start is piece initial location
     * @param end is piece new location
     */
    public Turn movePiece(Location start, Location end){
        Turn currentTurn = new Turn();
        PlayerInterface currentPlayer = findPlayerTurn(turnCount);
        PlayerInterface otherPlayer = findPlayerTurn(turnCount + 1);


        // check for castling

        // add to removed list if piece exists at destination
        if(otherPlayer.getPiece(end) != null) {
            currentTurn.removePiece(end);
            otherPlayer.removePiece(end);
        }

        //basic move piece
        currentTurn.movePiece(start, end);
        currentPlayer.movePiece(moveFactory.pieceAt(start), end);

        // increment turn
        turnCount++;
        // pause current player timer, start next player time
        

        // update legal moves
        updateLegalMoves();

        return currentTurn;
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
                return (moveFactory.inCheck(player.getKing(), otherPlayer.getPieces())) ? GameState.CHECKMATE : GameState.STALEMATE;
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
        PieceInterface piece = moveFactory.pieceAt(location);
        return (piece != null && piece.getTeam().equals(findPlayerTurn(turnCount).getTeam()));
    }

    private PlayerInterface findPlayerTurn(int turn) {
        return players.get(turn % players.size());
    }
}
