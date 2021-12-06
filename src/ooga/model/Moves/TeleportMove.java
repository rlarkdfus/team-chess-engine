package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

import java.util.ArrayList;
import java.util.List;

public class TeleportMove extends TranslationMove {
    /**
     * teleport allows a piece to jump to a specific square directly
     * @param dRow    delta row
     * @param dCol    delta col
     * @param take    move takes
     * @param limited move is limited
     * @param bounds
     */
    public TeleportMove(int dRow, int dCol, boolean take, boolean limited, Location bounds) {
        super(dRow, dCol, take, limited, bounds);
    }

    /**
     * Returns list of all possible locations of a given piece based on other pieces disregarding checks
     * @param piece is the piece player is attempting to move
     * @return list of all potential locations regardless of game rules
     */
    @Override
    public List<Location> findAllEndLocations(PieceInterface piece, List<PieceInterface> pieces) {
        return List.of(new Location(getdRow(), getdCol()));
    }

    /**
     * teleport to the location if the location does not have any other pieces on it
     * @param piece current piece
     * @param potentialLocation location to move to
     * @param pieces all pieces
     * @return whether promotion is legal
     */
    @Override
    protected boolean isLegal(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces) {
        if(MoveUtility.pieceAt(potentialLocation, pieces) != null) {
            return false;
        }
        return tryMove(piece, new Location(getdRow(), getdCol()), new ArrayList<>(pieces));
    }

}
