package ooga.model.Moves;

import java.util.ArrayList;
import java.util.List;
import ooga.Location;
import ooga.model.PieceInterface;

public class PawnMove extends Move { //TODO: pawn move takes in +-2 depending on side
    /**
     * manages initial move of pawns, moving 2 squares
     * @param dRow delta row
     * @param dCol delta col
     * @param take move can take
     * @param limited move is limited
     */
    public PawnMove(int dRow, int dCol, boolean take, boolean limited) {
        super(dRow, dCol, take, limited);
    }

    /**
     * @param piece current piece
     * @param pieces all pieces
     * @param end end location
     */
    @Override
    public void executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end) {
        movePiece(piece, end);
    }

    /**
     * @param pawn piece to move
     * @param potentialLocation location to move to
     * @param pieces all pieces
     * @return whether a location is a legal move for the pawn
     */
    @Override
    protected boolean isLegal(PieceInterface pawn, Location potentialLocation, List<PieceInterface> pieces) {
        // construct location 1 above, and 2 above, make sure they're clear
        Location intermediateLocation = new Location(potentialLocation.getRow() - getdRow()/2, potentialLocation.getCol());
        if(!MoveUtility.isClear(List.of(potentialLocation, intermediateLocation), pieces)) {
            return false;
        }

        if(pawn.hasMoved()) {
            return false;
        }

        return tryMove(pawn, potentialLocation, new ArrayList<>(pieces));
    }
}
