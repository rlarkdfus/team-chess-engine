package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;
import java.util.ArrayList;
import java.util.List;

public class EnPassantMove extends Move {

    @Override
    public void executeMove(PieceInterface pawn, List<PieceInterface> pieces, Location end) {
        Location enemyPawnLocation = new Location(end.getRow() - getdRow(), end.getCol());

        Location removeFrom = MoveUtility.pieceAt(enemyPawnLocation, pieces) == null ? end : enemyPawnLocation;
        System.out.println("EnPassantMove.executeMove removed " + MoveUtility.pieceAt(removeFrom, pieces));
        removePiece(MoveUtility.pieceAt(removeFrom, pieces), pieces);
        movePiece(pawn, end);
    }

    @Override
    protected boolean isLegal(PieceInterface pawn, Location potentialLocation, List<PieceInterface> pieces) {
        Location otherPawnLocation = new Location(pawn.getLocation().getRow(), potentialLocation.getCol());

        if(MoveUtility.pieceAt(potentialLocation, pieces) != null || MoveUtility.pieceAt(otherPawnLocation, pieces) == null) {
            return false;
        }

        PieceInterface otherPawn = MoveUtility.pieceAt(otherPawnLocation, pieces);
//        if(otherPawn != null) {
//            System.out.println(pawn.toString() + " " + pawn.getLocation() + ", " + otherPawn.toString() + " " + otherPawnLocation);
//        }

        if (otherPawn == null || !otherPawn.getName().equals("P") || !otherPawn.isFirstMove()){
            return false;
        }
        return tryMove(pawn, potentialLocation, new ArrayList<>(pieces));
    }

    @Override
    protected PieceInterface tryTakeMove(Location potentialLocation, List<PieceInterface> pieces) {
        Location otherPawnLocation = new Location(potentialLocation.getRow() - getdRow(), potentialLocation.getCol());
        return MoveUtility.pieceAt(otherPawnLocation, pieces);
    }
}
