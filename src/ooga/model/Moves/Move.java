package ooga.model.Moves;

import ooga.Location;
import ooga.Turn;
import ooga.model.PieceInterface;

import java.util.ArrayList;
import java.util.List;

public abstract class Move {

    private final int dRow;
    private final int dCol;
    private final boolean take;
    private final boolean limited;

    private Turn turn;
    private List<Location> endLocations;

    public Move(int dRow, int dCol, boolean take, boolean limited) {
        resetMove();
        this.dRow = dRow;
        this.dCol = dCol;
        this.take = take;
        this.limited = limited;
    }

    public abstract void executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end);

    public void updateMoveLocations(PieceInterface king, List<PieceInterface> pieces) {
        resetMove();
        for(Location location : findAllEndLocations(king, pieces)) {
            if(isLegal(king, location, pieces)) {
                addEndLocation(location);
            }
        }
    }

    protected abstract boolean isLegal(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces);

    /**
     * Returns list of all possible locations of a given piece based on other pieces disregarding checks
     * @param piece is the piece player is attempting to move
     * @return list of all potential locations regardless of game rules
     */
    public List<Location> findAllEndLocations(PieceInterface piece, List<PieceInterface> pieces) {
        List<Location> endLocations = new ArrayList<>();
        int row = piece.getLocation().getRow() + dRow;
        int col = piece.getLocation().getCol() + dCol;

        Location potentialLocation = new Location(row, col);

        while(MoveUtility.inBounds(row, col)){
            PieceInterface targetPiece = MoveUtility.pieceAt(potentialLocation, pieces);
            if(targetPiece != null) {
                if(!targetPiece.isSameTeam(piece) && take) {
                    endLocations.add(potentialLocation);
                }
                break;
            }

            endLocations.add(potentialLocation);

            if(limited) {
                break;
            }

            row += dRow;
            col += dCol;
            potentialLocation = new Location(row, col);
        }
        return endLocations;
    }

    protected void movePiece(PieceInterface piece, Location end) {
        turn.movePiece(piece.getLocation(), end);
        piece.moveTo(end);
    }

    protected void removePiece(PieceInterface removedPiece, List<PieceInterface> pieces) {
        turn.removePiece(removedPiece.getLocation());
        pieces.remove(removedPiece);
    }

    /**
     * Helper function to see if potential move is legal
     * @param piece is the piece player is attempting to move
     * @param potentialLocation is the location the player is attempting to move the piece to
     * @return if the move is legal or not
     */
    public boolean tryMove(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces) {
        Location originalLocation = new Location(piece.getLocation().getRow(), piece.getLocation().getCol());
        PieceInterface takenPiece = tryTakeMove(potentialLocation, pieces);

        // theoretically move piece to location
        if(takenPiece != null) { //take piece if exists
            pieces.remove(takenPiece);
        }
        //move piece to new location
        piece.tryMove(potentialLocation);
        boolean inCheck = MoveUtility.inCheck(piece.getTeam(), pieces);
        piece.tryMove(originalLocation);
        return !inCheck;
    }

    protected PieceInterface tryTakeMove(Location location, List<PieceInterface> pieces) {
        return MoveUtility.pieceAt(location, pieces);
    }

    protected void resetMove() {
        endLocations = new ArrayList<>();
        turn = new Turn();
    }

    public List<Location> getEndLocations() {
        return endLocations;
    }

    protected void addEndLocation(Location location) {
        endLocations.add(location);
    }

    protected int getdRow() {
        return dRow;
    }

    protected int getdCol() {
        return dCol;
    }

    protected boolean canTake() {
        return take;
    }

    protected boolean isLimited(){
        return limited;
    }

    public Turn getTurn() {
        return turn;
    }
}