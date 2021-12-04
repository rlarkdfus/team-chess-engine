package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

import java.util.ArrayList;
import java.util.List;

public class TakeOnlyMove extends TranslationMove {

    public TakeOnlyMove(int dRow, int dCol, boolean take, boolean limited) {
        super(dRow, dCol, take, limited);
    }

    @Override
    protected boolean isLegal(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces) {
        if(MoveUtility.pieceAt(potentialLocation, pieces) == null) {
            return false;
        }
        return tryMove(piece, potentialLocation, new ArrayList<>(pieces));
    }
}
