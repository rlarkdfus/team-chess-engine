package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

import java.util.ArrayList;
import java.util.List;

public class TranslationMove extends Move {

    public TranslationMove() {
        super();
    }

    @Override
    public void executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end) {
        List<PieceInterface> board = new ArrayList<>(pieces);
        for(PieceInterface occupied : new ArrayList<>(pieces)) {
            if(occupied.getLocation().equals(end)) {
                removePiece(occupied, pieces);
                board.remove(occupied);
            }
        }
        movePiece(piece, end);

//        return board;
    }

    @Override
    public void updateMoveLocations(PieceInterface piece, List<PieceInterface> pieces) {
        resetMove();
        int row = piece.getLocation().getRow() + getdRow();
        int col = piece.getLocation().getCol() + getdCol();

        Location potentialLocation = new Location(row, col);

        while(isLegal(piece, potentialLocation, pieces)){
//            System.out.println(piece.getTeam() + piece.getName() + " " + isLegal(piece, potentialLocation, pieces));
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

        // find potential piece at new location
        PieceInterface potentialPiece = null;
        for(PieceInterface p : pieces) {
            if(p.getLocation().equals(potentialLocation)) {
                potentialPiece = p;
                break;
            }
        }

        if(potentialPiece != null) { //if there is a piece
            if(potentialPiece.getTeam().equals(piece.getTeam()) || canTake()) {
                return false;
            }
        }

        return tryMove(piece, potentialLocation, new ArrayList<>(pieces));
    }
}