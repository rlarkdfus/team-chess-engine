package ooga.model;

import java.util.ArrayList;
import java.util.List;
import ooga.Location;
import ooga.model.Moves.Move;
import ooga.model.Moves.NoRestrictionMove;

public class EditorBoard extends Board {


    public EditorBoard(List<PlayerInterface> players, Location bounds) {
        super(players,bounds);
        for(PlayerInterface player : players) {
            for(PieceInterface piece : player.getPieces()) {
                player.removePiece(piece);
            }
            player.getPieces().clear();
            System.out.println("player pieces: " + player.getPieces().size());
        }
        pieces = new ArrayList<>();
    }

    @Override
    protected Move getMove(Location end, PieceInterface piece) {
        Move move = new NoRestrictionMove(0, 0, false, false, getBounds());
        move.updateMoveLocations(piece, new ArrayList<>());
        return move;
    }

    @Override
    public List<Location> getLegalMoves(Location location) {
        return new NoRestrictionMove(0, 0, false, false, getBounds()).findAllEndLocations(null, null);
    }

    @Override
    protected void updateGameRules() {
        for(PieceInterface piece : pieces) {
            PieceInterface newPiece = new Piece(piece.getTeam(), piece.getName(), piece.getLocation(), new ArrayList<>(), piece.getScore());
            piece.transform(newPiece);
        }
    }

    @Override
    public GameState checkGameState() {
        return GameState.RUNNING;
    }

    @Override
    public boolean canMovePiece(Location location) {
        List<Location> pieceLocations = new ArrayList<>();
        for (PieceInterface piece : pieces) {
            pieceLocations.add(piece.getLocation());
        }
        return location.inList(pieceLocations);
    }
}
