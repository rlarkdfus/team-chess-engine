package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;
import java.util.ArrayList;
import java.util.List;

public class EnPassantMove extends Move {

    @Override
    public void executeMove(PieceInterface pawn, List<PieceInterface> pieces, Location end) {
        Location enemyPawnLocation = new Location(end.getRow() - getdRow(), end.getCol());

        Location removeFrom = pieceAt(enemyPawnLocation, pieces) == null ? end : enemyPawnLocation;
        System.out.println("EnPassantMove.executeMove removed " + pieceAt(removeFrom, pieces));
        removePiece(pieceAt(removeFrom, pieces), pieces);
        movePiece(pawn, end);
    }

    @Override
    public void updateMoveLocations(PieceInterface pawn, List<PieceInterface> pieces) {
        resetMove();
        int row = pawn.getLocation().getRow() + getdRow();
        int col = pawn.getLocation().getCol() + getdCol();

        Location potentialLocation = new Location(row, col);

        if(isLegal(pawn, potentialLocation, pieces)) {
            addEndLocation(potentialLocation);
        }
    }

    @Override
    protected boolean isLegal(PieceInterface pawn, Location potentialLocation, List<PieceInterface> pieces) {
        Location otherPawnLocation = new Location(pawn.getLocation().getRow(), potentialLocation.getCol());

        PieceInterface otherPawn = pieceAt(otherPawnLocation, pieces);

        if(pieceAt(potentialLocation, pieces) != null) {
            return false;
        }
        if (otherPawn == null || !otherPawn.isFirstMove()){
            return false;
        }
        return tryMove(pawn, potentialLocation, new ArrayList<>(pieces));
    }

    /**
     * Helper function to see if potential move is legal
     * @param piece is the piece player is attempting to move
     * @param potentialLocation is the location the player is attempting to move the piece to
     * @return if the move is legal or not
     */
    @Override
    public boolean tryMove(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces) {
        Location pieceLocation = new Location(piece.getLocation().getRow(), piece.getLocation().getCol());
        Location otherPawnLocation = new Location(potentialLocation.getRow() - getdRow(), potentialLocation.getCol());
        PieceInterface takenPiece = pieceAt(otherPawnLocation, pieces);
        
        piece.tryMove(potentialLocation);
        pieces.remove(takenPiece);

        // look for checks
        List<PieceInterface> attackingPieces = getAttackingPieces(piece, pieces);

        // if the king is in check, undo move and return false
        if(underAttack(findKing(piece, pieces).getLocation(), attackingPieces)) {
            piece.tryMove(pieceLocation);
            return false;
        }

        //otherwise undo the move and return true
        piece.tryMove(pieceLocation);
        return true;
    }
}
