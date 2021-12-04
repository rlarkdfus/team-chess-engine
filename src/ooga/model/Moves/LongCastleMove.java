package ooga.model.Moves;

import ooga.Location;
import ooga.model.Moves.CastleMove;
import ooga.model.PieceInterface;

public class LongCastleMove extends CastleMove {
    /**
     * queen side castling
     * @param dRow delta row
     * @param dCol delta col
     * @param take move takes
     * @param limited move is limited
     */
    public LongCastleMove(int dRow, int dCol, boolean take, boolean limited) {
        super(dRow, dCol, take, limited);
    }

    /**
     * @param king location of king
     * @return location of rook
     */
    @Override
    protected Location findRookLocation(PieceInterface king) {
        return new Location(king.getLocation().getRow(), 0);
    }
}
