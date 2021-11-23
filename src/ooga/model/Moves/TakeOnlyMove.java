package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

import java.util.List;

public class TakeOnlyMove extends Move {

    @Override
    public void executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end) {
        PieceInterface takenPiece = pieceAt(end, pieces);
        removePiece(takenPiece, pieces);
        movePiece(piece, end);
    }

    @Override
    public void updateMoveLocations(PieceInterface piece, List<PieceInterface> pieces) {
        resetMove();

        int row = piece.getLocation().getRow() + getdRow();
        int col = piece.getLocation().getCol() + getdCol();
        Location potentialLocation = new Location(row, col);

        while(isLegal(piece, potentialLocation, pieces)){
            addEndLocation(potentialLocation);

            if(isLimited()) {
                break;
            }

            row += getdRow();
            col += getdCol();
            potentialLocation = new Location(row, col);
        }
    }

    @Override
    boolean isLegal(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces) {
        if(!inBounds(potentialLocation.getRow(), potentialLocation.getCol())) {
            return false;
        }
        if(pieceAt(potentialLocation, pieces) == null || pieceAt(potentialLocation, pieces).getTeam().equals(piece.getTeam())) {
            return false;
        }
        return tryMove(piece, potentialLocation, pieces);
    }
}
