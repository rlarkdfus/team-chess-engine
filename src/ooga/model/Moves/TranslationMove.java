package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

import java.util.ArrayList;
import java.util.List;

public class TranslationMove extends Move {

    public TranslationMove() {
    }

    @Override
    public List<PieceInterface> executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end) {
        List<PieceInterface> takenPieces = new ArrayList<>();
        for(PieceInterface occupied : pieces) {
            if(occupied.getLocation().equals(end)) {
                takenPieces.add(occupied);
                getTurn().removePiece(end);
            }
        }
        pieces.removeAll(takenPieces);

        getTurn().movePiece(piece.getLocation(), end);
        piece.moveTo(end);
        return pieces;
    }

    @Override
    public void updateMoveLocations(PieceInterface piece, List<PieceInterface> pieces) {
        resetEndLocations();
        int row = piece.getLocation().getRow() + getdRow();
        int col = piece.getLocation().getCol() + getdCol();

        Location potentialLocation = new Location(row, col);

        while(isLegal(piece, potentialLocation, pieces)){
            System.out.println(piece.getTeam() + piece.getName() + " " + isLegal(piece, potentialLocation, pieces));
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

        if(potentialPiece != null && potentialPiece.getTeam().equals(piece.getTeam())) {
            return false;
        } else if (potentialPiece != null && !canTake()) { // piece is not the same team but cannot take
            return false;
        }

        return tryMove(piece, potentialLocation, pieces);
    }
}