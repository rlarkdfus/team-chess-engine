package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

import java.util.ArrayList;
import java.util.List;

public class TakeOnlyMove extends TranslationMove {
    /**
     * moves that can only be executed if they take another piece
     * @param dRow delta row
     * @param dCol delta col
     * @param take move takes
     * @param limited move is limited
     */
    public TakeOnlyMove(int dRow, int dCol, boolean take, boolean limited, Location bounds) {
        super(dRow, dCol, take, limited,bounds);
    }

    /**
     * whether a take move is legal
     * @param piece piece to move
     * @param potentialLocation location to move to
     * @param pieces all pieces
     * @return take move is legal or not
     */
    @Override
    protected boolean isLegal(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces) {
        if(MoveUtility.pieceAt(potentialLocation, pieces) == null) {
            return false;
        }
        return tryMove(piece, potentialLocation, new ArrayList<>(pieces));
    }
}
