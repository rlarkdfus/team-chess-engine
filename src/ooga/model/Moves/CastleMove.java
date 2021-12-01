package ooga.model.Moves;

import ooga.Location;
import ooga.model.Moves.Move;
import ooga.model.PieceInterface;

import java.util.List;

public abstract class CastleMove extends Move {

    protected abstract Location findRookLocation(PieceInterface piece);

    @Override
    public void executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end) {
        // move rook as well
        PieceInterface rook = pieceAt(findRookLocation(piece), pieces);

        movePiece(piece, end);
        movePiece(rook, new Location(piece.getLocation().getRow(), piece.getLocation().getCol() - getdCol()/2));
    }

    @Override
    protected boolean isLegal(PieceInterface king, Location potentialLocation, List<PieceInterface> pieces) {
        PieceInterface rook = pieceAt(findRookLocation(king), pieces);

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
            if(underAttack(loc, attackingPieces, pieces)){
//                System.out.println("under attack");
                return false;
            }
        }
//        System.out.println("can castle");
        return true;
    }
}