package ooga.model.Moves;

import java.util.ArrayList;
import java.util.List;
import ooga.Location;
import ooga.model.PieceInterface;

public class PawnMove extends Move { //TODO: pawn move takes in +-2 depending on side

    @Override
    public void executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end) {
        movePiece(piece, end);
    }

    @Override
    public void updateMoveLocations(PieceInterface pawn, List<PieceInterface> pieces) {
        resetMove();
        int row = pawn.getLocation().getRow() + getdRow();
        int col = pawn.getLocation().getCol()+ getdCol();

        Location potentialLocation = new Location(row, col);

        if(isLegal(pawn, potentialLocation, pieces)) {
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

        if(pawn.hasMoved()) {
            return false;
        }

        return tryMove(pawn, potentialLocation, new ArrayList<>(pieces));
    }
}
