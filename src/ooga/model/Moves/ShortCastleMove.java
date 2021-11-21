package ooga.model.Moves;

import java.util.List;
import ooga.Location;
import ooga.model.PieceInterface;

public class ShortCastleMove extends Move {

    @Override
    public List<PieceInterface> executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end) {
        // move rook as well
        PieceInterface rook = findRook(piece.getLocation().getRow(), 7, pieces); // TODO not hardcode column 7

        movePiece(piece, end);
        movePiece(rook, new Location(piece.getLocation().getRow(), 5));

        return pieces;
    }

    @Override
    public void updateMoveLocations(PieceInterface king, List<PieceInterface> pieces) {
        resetMove();
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