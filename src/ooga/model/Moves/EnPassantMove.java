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

    @Override
    protected PieceInterface tryTakeMove(Location location, List<PieceInterface> pieces) {
        Location otherPawnLocation = new Location(location.getRow() - getdRow(), location.getCol());
        return pieceAt(otherPawnLocation, pieces);
    }
}
