package ooga.model.Moves;

import java.io.FileNotFoundException;
import ooga.Location;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.controller.Config.PieceBuilder;
import ooga.model.PieceInterface;

import java.util.List;

public class PromoteMove extends Move {

    public PromoteMove(int dRow, int dCol, boolean take, boolean limited) {
        super(dRow, dCol, take, limited);
    }

    @Override
    public void updateMoveLocations(PieceInterface piece, List<PieceInterface> pieces) {
        if(isLegal(piece, null, pieces)) {
            executeMove(piece, pieces, null);
        }
    }

    @Override
    public void executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end) {
        System.out.println("Execute promote move");
        PieceInterface newPiece = null;
        try {
            newPiece = PieceBuilder.buildPiece(piece.getTeam(), "Q",end);
        } catch (FileNotFoundException | InvalidPieceConfigException e) {
            e.printStackTrace();
        }
        piece.transform(newPiece);
    }

    @Override
    protected boolean isLegal(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces) {
        return (piece.getLocation().getRow() == getdRow() || getdRow() == -1) &&
            (piece.getLocation().getCol() == getdCol() || getdCol() == -1) &&
            piece.canTransform();
    }
}
