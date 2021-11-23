package ooga.model.Moves;

import ooga.Location;
import ooga.model.Moves.CastleMove;
import ooga.model.PieceInterface;

public class ShortCastleMove extends CastleMove {

    @Override
    protected Location findRookLocation(PieceInterface king) {
        return new Location(king.getLocation().getRow(), 7);
    }
}