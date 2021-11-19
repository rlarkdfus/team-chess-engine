package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

import java.util.ArrayList;
import java.util.List;

public class ShortCastleMove extends Move {

    @Override
    protected List<PieceInterface> executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end) {
        Location location = new Location(piece.getLocation().getRow() + getdRow(), piece.getLocation().getCol() + getdCol());

        // move rook as well
        PieceInterface rook = findRook(piece.getLocation().getRow(), 7, pieces); // TODO not hardcode column 7

        piece.moveTo(location);
        rook.moveTo(new Location(piece.getLocation().getRow(), 5));

        return pieces;
    }

    @Override
    void updateMoveLocations(PieceInterface king, List<PieceInterface> pieces) {
        resetEndLocations();
        int row = king.getLocation().getRow() + getdRow();
        int col = king.getLocation().getCol() + getdCol();

        Location potentialLocation = new Location(row, col);

        if(isLegal(king, potentialLocation, pieces)) {
            addEndLocation(potentialLocation);
        }
    }

    @Override
    boolean isLegal(PieceInterface king, Location potentialLocation, List<PieceInterface> pieces) {
        if(!inBounds(potentialLocation.getRow(), potentialLocation.getCol())) {
            return false;
        }

        PieceInterface rook = findRook(king.getLocation().getRow(), 7, pieces); // TODO not hardcode column 7

        // make sure none have moved
        if(king.hasMoved() || rook.hasMoved()) {
            return false;
        }

        // construct location 1 above, and 2 above, make sure they're clear
        Location intermediateLocation = new Location(potentialLocation.getRow(), potentialLocation.getCol()-getdCol()/2);
        if(!isClear(List.of(potentialLocation, intermediateLocation), pieces)) {
            return false;
        }
        // must make sure nothing in that row is under attack
        List<Location> kingLocations = List.of(king.getLocation(), potentialLocation, intermediateLocation);
        for(Location l : kingLocations){
            if(underAttack(l, pieces)){
                return false;
            }
        }
        return true;
    }

    private PieceInterface findRook(int row, int col, List<PieceInterface> pieces) {
        return pieceAt(new Location(row, col), pieces);
    }
}