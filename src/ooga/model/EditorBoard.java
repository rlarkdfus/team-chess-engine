package ooga.model;

import java.util.ArrayList;
import java.util.List;
import ooga.Location;
import ooga.model.Moves.Move;
import ooga.model.Moves.NoRestrictionMove;

public class EditorBoard extends Board {

    public EditorBoard(List<PlayerInterface> players) {
        super(players);
    }

    @Override
    protected Move getMove(Location end, PieceInterface piece) {
        Move move = new NoRestrictionMove(0, 0, false, false);
        move.updateMoveLocations(piece, new ArrayList<>());
        return move;
    }

    @Override
    public List<Location> getLegalMoves(Location location) {
        return new NoRestrictionMove(0, 0, false, false).findAllEndLocations(null, null);
    }

    @Override
    protected void updateGameRules() {

    }

    @Override
    public GameState checkGameState() {
        return GameState.RUNNING;
    }

    @Override
    public boolean canMovePiece(Location location) {
        List<Location> pieceLocations = new ArrayList<>();
        for (PieceInterface piece : allPieces) {
            pieceLocations.add(piece.getLocation());
        }
        return location.inList(pieceLocations);
    }
}
