package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * @authors gordon, sam
 * purpose - translation allows a piece to translate along the board
 * assumptions - it assumes that piece and players are all valid
 * dependencies - it depends on Location and PieceInterface
 * usage - if a piece has this move the move class will check if it is legal and return it
 * as part of its end locations
 */
public class TranslationMove extends Move {
    /**
     * translation move of piece moving along board
     * @param dRow delta row
     * @param dCol delta col
     * @param take move takes
     * @param limited move is limited
     */
    public TranslationMove(int dRow, int dCol, boolean take, boolean limited, Location bounds) {
        super(dRow, dCol, take, limited, bounds);
    }

    /**
     * @param piece current piece
     * @param pieces all pieces
     * @param end end location
     */
    @Override
    public void executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end) {
        PieceInterface takenPiece = MoveUtility.pieceAt(end, pieces);
        if(takenPiece != null) {
            removePiece(takenPiece, pieces);
        }
        movePiece(piece, end);
    }

    /**
     * @param piece current piece
     * @param potentialLocation location to move to
     * @param pieces all pieces
     * @return whether move is legal or not
     */
    @Override
    protected boolean isLegal(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces) {
        return tryMove(piece, potentialLocation, new ArrayList<>(pieces));
    }
}