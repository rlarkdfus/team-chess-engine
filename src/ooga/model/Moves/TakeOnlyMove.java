package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

import java.util.ArrayList;
import java.util.List;

public class TakeOnlyMove extends TranslationMove {

    @Override
    protected boolean isLegal(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces) {
        if(pieceAt(potentialLocation, pieces) == null) {
            return false;
        }
        return tryMove(piece, potentialLocation, new ArrayList<>(pieces));
    }
}
