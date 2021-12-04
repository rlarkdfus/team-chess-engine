package ooga.model.Moves;

import ooga.Location;
import ooga.model.Moves.CastleMove;
import ooga.model.PieceInterface;

public class ShortCastleMove extends CastleMove {

    public ShortCastleMove(int dRow, int dCol, boolean take, boolean limited) {
        super(dRow, dCol, take, limited);
    }

    @Override
    protected Location findRookLocation(PieceInterface king) {
        return new Location(king.getLocation().getRow(), 7);
    }
}