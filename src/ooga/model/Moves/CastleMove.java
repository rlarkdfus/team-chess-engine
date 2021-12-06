package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

import java.util.List;

public abstract class CastleMove extends Move {
    /**
     * castling with the king and rook
     * @param dRow delta row
     * @param dCol delta col
     * @param take whether move can take
     * @param limited whether move is limited
     */
    public CastleMove(int dRow, int dCol, boolean take, boolean limited, Location bounds) {
        super(dRow, dCol, take, limited, bounds);
    }

    /**
     * gets the rook location
     * @param piece king
     * @return location of the rook
     */
    protected abstract Location findRookLocation(PieceInterface piece);

    /**
     * @param piece current piece
     * @param pieces all pieces
     * @param end end location
     */
    @Override
    public void executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end) {
        // move rook as well
        PieceInterface rook = MoveUtility.pieceAt(findRookLocation(piece), pieces);

        movePiece(piece, end);
        movePiece(rook, new Location(piece.getLocation().getRow(), piece.getLocation().getCol() - getdCol()/2));
    }

    /**
     * @param king player's king piece
     * @param potentialLocation location to move to
     * @param pieces all pieces
     * @return whether castling is legal
     */
    @Override
    protected boolean isLegal(PieceInterface king, Location potentialLocation, List<PieceInterface> pieces) {
        PieceInterface rook = MoveUtility.pieceAt(findRookLocation(king), pieces);

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
        if(!MoveUtility.isClear(List.of(potentialLocation, intermediateLocation), pieces)) {
//            System.out.println("not clear");
            return false;
        }
        // must make sure nothing in that row is under attack
        List<Location> kingLocations = List.of(king.getLocation(), potentialLocation, intermediateLocation);
        List<PieceInterface> attackingPieces = MoveUtility.getAttackingPieces(king.getTeam(), pieces);
        for(Location loc : kingLocations){
            if(MoveUtility.underAttack(loc, attackingPieces, pieces)){
//                System.out.println("under attack");
                return false;
            }
        }
//        System.out.println("can castle");
        return true;
    }
}