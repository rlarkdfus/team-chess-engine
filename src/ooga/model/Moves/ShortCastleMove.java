package ooga.model.Moves;

import java.util.List;
import ooga.Location;
import ooga.model.PieceInterface;

public class ShortCastleMove extends Move {

    @Override
    public void executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end) {
        // move rook as well
        PieceInterface rook = findRook(piece.getLocation().getRow(), 7, pieces); // TODO not hardcode column 7

        movePiece(piece, end);
        movePiece(rook, new Location(piece.getLocation().getRow(), 5));
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
//        System.out.println(king);

        PieceInterface rook = findRook(king.getLocation().getRow(), 7, pieces); // TODO not hardcode column 7

        if(rook == null) {
//            System.out.println("rook not found");
            return false;
        }

        // make sure none have moved
        if(king.hasMoved() || rook.hasMoved()) {
//            System.out.println("moved");
            return false;
        }

        // construct location 1 above, and 2 above, make sure they're clear
        Location intermediateLocation = new Location(potentialLocation.getRow(), potentialLocation.getCol()-getdCol()/2);
        if(!isClear(List.of(potentialLocation, intermediateLocation), pieces)) {
//            System.out.println("not clear");
            return false;
        }
        // must make sure nothing in that row is under attack
        List<Location> kingLocations = List.of(king.getLocation(), potentialLocation, intermediateLocation);
        List<PieceInterface> attackingPieces = getAttackingPieces(king, pieces);
        for(Location loc : kingLocations){
            if(underAttack(loc, attackingPieces)){
//                System.out.println("under attack");
                return false;
            }
        }
//        System.out.println("can castle");
        return true;
    }

    private PieceInterface findRook(int row, int col, List<PieceInterface> pieces) {
        return pieceAt(new Location(row, col), pieces);
    }
}