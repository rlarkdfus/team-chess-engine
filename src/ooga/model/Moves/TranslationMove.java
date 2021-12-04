package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

import java.util.ArrayList;
import java.util.List;

public class TranslationMove extends Move {

    public TranslationMove(int dRow, int dCol, boolean take, boolean limited) {
        super(dRow, dCol, take, limited);
    }

    @Override
    public void executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end) {
        PieceInterface takenPiece = MoveUtility.pieceAt(end, pieces);
        if(takenPiece != null) {
            removePiece(takenPiece, pieces);
        }
        movePiece(piece, end);
    }

    @Override
    protected boolean isLegal(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces) {
        return tryMove(piece, potentialLocation, new ArrayList<>(pieces));
    }
}