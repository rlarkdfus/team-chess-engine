package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

import java.util.List;

public class LongCastleMove extends Move {

    @Override
    public void executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end) {
    }

    @Override
    public void updateMoveLocations(PieceInterface piece, List<PieceInterface> pieces) {

    }

    @Override
    boolean isLegal(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces) {
        return false;
    }
}
