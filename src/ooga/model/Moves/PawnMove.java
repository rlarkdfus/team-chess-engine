package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

import java.util.List;

public class PawnMove extends Move { //TODO: pawn move takes in +-2 depending on side

    @Override
    public List<PieceInterface> executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end) {
        movePiece(piece, end);
        return pieces;
    }

    @Override
    public void updateMoveLocations(PieceInterface piece, List<PieceInterface> pieces) {
        resetMove();
        int row = piece.getLocation().getRow() + getdRow();
        int col = piece.getLocation().getCol();

        Location potentialLocation = new Location(row, col);

        if(isLegal(piece, potentialLocation, pieces)) {
            addEndLocation(potentialLocation);
        }
    }

    @Override
    boolean isLegal(PieceInterface pawn, Location potentialLocation, List<PieceInterface> pieces) {
        // construct location 1 above, and 2 above, make sure they're clear
        Location intermediateLocation = new Location(potentialLocation.getRow() - getdRow()/2, potentialLocation.getCol());
        if(!isClear(List.of(potentialLocation, intermediateLocation), pieces)) {
            return false;
        }

        return tryMove(pawn, potentialLocation, pieces);
    }
}
