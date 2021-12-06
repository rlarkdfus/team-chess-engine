package ooga.model.Moves;

import java.io.FileNotFoundException;
import ooga.Location;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.controller.Config.PieceBuilder;
import ooga.model.PieceInterface;

import java.util.ArrayList;
import java.util.List;

public class PromoteMove extends Move {
    /**
     * promotions of a pawn to another piece
     * @param dRow delta row
     * @param dCol delta col
     * @param take move takes
     * @param limited move is limited
     */
    public PromoteMove(int dRow, int dCol, boolean take, boolean limited, Location bounds) {
        super(dRow, dCol, take, limited, bounds);
    }

    /**
     * @param piece current piece
     * @param pieces all pieces
     */
    @Override
    public void updateMoveLocations(PieceInterface piece, List<PieceInterface> pieces) {
        if(isLegal(piece, null, pieces)) {
            executeMove(piece, pieces, null);
        }
    }

    /**
     * @param piece current piece
     * @param pieces all pieces
     * @param end end location
     */
    @Override
    public void executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end) {
        System.out.println("Execute promote move");
        PieceInterface newPiece = null;
        try {
            newPiece = PieceBuilder.buildPiece(piece.getTeam(), "Q",end,getBounds());
        } catch (FileNotFoundException | InvalidPieceConfigException e) {
            e.printStackTrace();
        }
        piece.transform(newPiece);

        for (PieceInterface p : pieces) {
            p.updateMoves(new ArrayList<>(pieces));
        }
    }

    /**
     * @param piece current piece
     * @param potentialLocation location to move to
     * @param pieces all pieces
     * @return whether promotion is legal
     */
    @Override
    protected boolean isLegal(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces) {
        return (piece.getLocation().getRow() == getdRow() || getdRow() == -1) &&
            (piece.getLocation().getCol() == getdCol() || getdCol() == -1);
    }
}
