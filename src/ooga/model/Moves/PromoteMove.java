package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

import java.util.List;

public class PromoteMove extends Move {

    @Override
    public void updateMoveLocations(PieceInterface piece, List<PieceInterface> pieces) {
        if(isLegal(piece, null, pieces)) {
            executeMove(piece, pieces, null);
        }
    }

    @Override
    public void executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end) {
        System.out.println("Execute promote move");
        for(PieceInterface newPiece : pieces) {
            if(newPiece.getName().equals("Q") && newPiece.isSameTeam(piece)) { // TODO Q or whatever other otp
                piece.transform(newPiece);
            }
        }
    }

    @Override
    protected boolean isLegal(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces) {
        return (piece.getLocation().getRow() == getdRow() || getdRow() == -1) &&
                (piece.getLocation().getCol() == getdCol() || getdCol() == -1) &&
                piece.canTransform();
    }
}
