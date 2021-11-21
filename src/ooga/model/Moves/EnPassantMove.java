package ooga.model.Moves;

import ooga.Location;
import ooga.Turn;
import ooga.model.PieceInterface;

import java.util.ArrayList;
import java.util.List;

public class EnPassantMove extends Move {
    @Override
    public List<PieceInterface> executeMove(PieceInterface pawn, List<PieceInterface> pieces, Location end) {
        pawn.moveTo(end);
        Location enemyPawnLocation = new Location(end.getRow() - getdRow(), end.getCol());
        pieces.remove(pieceAt(enemyPawnLocation, pieces));

        turn.movePiece(pawn.getLocation(), end);
        turn.removePiece(enemyPawnLocation);
        return pieces;
    }

    @Override
    void updateMoveLocations(PieceInterface pawn, List<PieceInterface> pieces) {
        resetEndLocations();
        int row = pawn.getLocation().getRow() + getdRow();
        int col = pawn.getLocation().getCol() + getdCol();

        Location potentialLocation = new Location(row, col);

        if(isLegal(pawn, potentialLocation, pieces)) {
            addEndLocation(potentialLocation);
        }
    }

    @Override
    boolean isLegal(PieceInterface pawn, Location potentialLocation, List<PieceInterface> pieces) {
        Location otherPawnLocation = new Location(pawn.getLocation().getRow(), potentialLocation.getCol());

        PieceInterface otherPawn = pieceAt(otherPawnLocation, pieces);

        if (otherPawn == null || !otherPawn.isFirstMove()){
            return false;
        }

        return tryMove(pawn, potentialLocation, pieces);
    }

    /**
     * Helper function to see if potential move is legal
     * @param piece is the piece player is attempting to move
     * @param potentialLocation is the location the player is attempting to move the piece to
     * @return if the move is legal or not
     */
    @Override
    public boolean tryMove(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces) {
        Location otherPawnLocation = new Location(potentialLocation.getRow() - getdRow(), potentialLocation.getCol());
        PieceInterface takenPiece = pieceAt(otherPawnLocation, pieces);
        
        piece.tryMove(potentialLocation);
        pieces.remove(takenPiece);

        // look for checks
        List<PieceInterface> attackingPieces = new ArrayList<>();
        for(PieceInterface attackingPiece : pieces) {
            if(!piece.getTeam().equals(attackingPiece.getTeam())) {
                attackingPieces.add(attackingPiece);
            }
        }

        // if the king is in check, undo move and return false
        if(underAttack(findKing(pieces).getLocation(), attackingPieces)) {
            undoTryMove(piece, otherPawnLocation, takenPiece, pieces);
            return false;
        }

        //otherwise undo the move and return true
        undoTryMove(piece, otherPawnLocation, takenPiece, pieces);
        return true;
    }
}
