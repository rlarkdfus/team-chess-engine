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
    private final Location bounds;

    private Turn turn;
    private List<Location> endLocations;

    /**
     * The move class defines how moves are executed and whether they are legal
     * @param dRow delta row
     * @param dCol delta col
     * @param take whether a move takes
     * @param limited whether a move is limited
     * @param bounds
     */
    public Move(int dRow, int dCol, boolean take, boolean limited, Location bounds) {
        resetMove();
        this.dRow = dRow;
        this.dCol = dCol;
        this.take = take;
        this.limited = limited;
        this.bounds = bounds;
    }

    /**
     * this method executes a move of a piece to an end location
     * @param piece current piece
     * @param pieces all pieces
     * @param end end location
     */
    public abstract void executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end);

    /**
     * This method updates all the possible move locations of a piece
     * @param piece piece location
     * @param pieces all pieces
     */
    public void updateMoveLocations(PieceInterface piece, List<PieceInterface> pieces) {
        resetMove();
        for(Location location : findAllEndLocations(piece, pieces)) {
            if(isLegal(piece, location, pieces)) {
                addEndLocation(location);
            }
        }
    }

    /**
     * this method returns whether a location is a legal move for a piece
     * @param piece current piece
     * @param potentialLocation location to move to
     * @param pieces all pieces
     * @return whether location is legal
     */
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

        while(MoveUtility.inBounds(row, col,bounds)){
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

    /**
     * this method moves a piece to an end location
     * @param piece current piece
     * @param end location to move to
     */
    protected void movePiece(PieceInterface piece, Location end) {
        turn.movePiece(piece.getLocation(), end);
        piece.moveTo(end);
    }

    /**
     * remove a piece from the list of pieces
     * @param removedPiece piece to remove
     * @param pieces all pieces
     */
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

    /**
     * return a piece at a location if it exists
     * @param location location to take piece at
     * @param pieces all pieces
     * @return piece if it exists
     */
    protected PieceInterface tryTakeMove(Location location, List<PieceInterface> pieces) {
        return MoveUtility.pieceAt(location, pieces);
    }

    /**
     * reset the location and turn of a move
     */
    protected void resetMove() {
        endLocations = new ArrayList<>();
        turn = new Turn();
    }

    /**
     * return the end locations of a move
     * @return list of end locations
     */
    public List<Location> getEndLocations() {
        return endLocations;
    }

    /**
     * add a location to the list of end locations
     * @param location end location of move
     */
    protected void addEndLocation(Location location) {
        endLocations.add(location);
    }

    /**
     * get the delta row of a move
     * @return delta row
     */
    protected int getdRow() {
        return dRow;
    }

    /**
     * get the delta column of a move
     * @return delta col
     */
    protected int getdCol() {
        return dCol;
    }

    /**
     * get the bounds of the board
     * @return a location object with the row and col bounds
     */
    protected Location getBounds() {
        return bounds;
    }
    /**
     * return the turn of a move
     * @return turn
     */
    public Turn getTurn() {
        return turn;
    }
}