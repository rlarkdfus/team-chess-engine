package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

import java.util.List;

public class PromoteMove extends Move {
    /**
     * promotions of a pawn to another piece
     * @param dRow delta row
     * @param dCol delta col
     * @param take move takes
     * @param limited move is limited
     */
    public PromoteMove(int dRow, int dCol, boolean take, boolean limited) {
        super(dRow, dCol, take, limited);
    }

    /**
     * @param piece current piece
     * @param pieces all pieces
     */
    @Override
    public void updateMoveLocations(PieceInterface piece, List<PieceInterface> pieces) {
        if(isLegal(piece, null, pieces)) {
            executeMove(piece, pieces, null);
        }
    }

    /**
     * @param piece current piece
     * @param pieces all pieces
     * @param end end location
     */
    @Override
    public void executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end) {
        System.out.println("Execute promote move");
        for(PieceInterface newPiece : pieces) {
            if(newPiece.getName().equals("Q") && newPiece.isSameTeam(piece)) { // TODO Q or whatever other otp
                piece.transform(newPiece);
            }
        }
    }

    /**
     * @param piece current piece
     * @param potentialLocation location to move to
     * @param pieces all pieces
     * @return whether promotion is legal
     */
    @Override
    protected boolean isLegal(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces) {
        return (piece.getLocation().getRow() == getdRow() || getdRow() == -1) &&
                (piece.getLocation().getCol() == getdCol() || getdCol() == -1) &&
                piece.canTransform();
    }
}
