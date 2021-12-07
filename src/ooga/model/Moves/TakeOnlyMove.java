package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * @authors gordon, sam
 * purpose - takeonlymove defines moves that can only be taken if they will take another piece
 * assumptions - it assumes that piece and player are valid
 * dependencies - it depends on Location and PieceInterface
 * usage - if a piece has this move the move class will check if it is legal and return it
 * as part of its end locations
 */
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
