package ooga.model.Moves;

import ooga.Location;
import ooga.Turn;
import ooga.model.PieceInterface;

import java.util.ArrayList;
import java.util.List;


public abstract class Move {

    private Turn turn;
    private int dRow;
    private int dCol;
    private boolean take;
    private List<Location> endLocations;
    private boolean limited;

    public Move() {
        resetMove();
    }

    /**
     * this method creates a turn with removed pieces, moved pieces, and removes the piece from the board
     * @param piece
     * @param pieces
     * @param end
     */
    public abstract void executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end);

    /**
     * this method adds all the potential move locations for a piece to its list of end locations
     * @param piece
     * @param pieces
     */
    public abstract void updateMoveLocations(PieceInterface piece, List<PieceInterface> pieces);

    /**
     * this method returns all the potential end locations of a move
     * @return
     */
    public List<Location> getEndLocations() {
        return endLocations;
    }

    /**
     * this method returns whether a move is legal, and takes into account teams, checks, and other factors
     * @param piece
     * @param potentialLocation
     * @param pieces
     * @return
     */
    protected abstract boolean isLegal(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces);

    /**
     * this method sets the parameters for a move action
     * @param dRow
     * @param dCol
     * @param take
     * @param limited
     */
    public void setMove(int dRow, int dCol, boolean take, boolean limited){ //TODO: boolean take
        this.dRow = dRow;
        this.dCol = dCol;
        this.take = take;
        this.limited = limited;
    }
    // [[(1,0), (2,0), ...],[(1,1),(2,1),...],...[...]]

    /**
     * Checks if the king is under attack from enemy pieces
     * @param attackingPieces is the list of pieces attacking the king
     * @return true if the king is under attack from list of pieces
     */
    public boolean underAttack(Location location, List<PieceInterface> attackingPieces) {
        for(PieceInterface attackingPiece : attackingPieces) {
            if(location.inList(attackingPiece.getEndLocations())) {
                return true;
            }
        }
        return false;
    }

    /**
     * this method returns whether any pieces listed are at any of the specified locations
     * @param locations
     * @param pieces
     * @return
     */
    protected boolean isClear(List<Location> locations, List<PieceInterface> pieces) {
        for(PieceInterface piece : pieces) {
            if(piece.getLocation().inList(locations)) {
                return false;
            }
        }

        return true;
    }

    /**
     * this method updates a turn to move a piece from its current location to the end location
     * @param piece
     * @param end
     */
    protected void movePiece(PieceInterface piece, Location end) {
        turn.movePiece(piece.getLocation(), end);
        piece.moveTo(end);
    }

    /**
     * this method updates a turn to remove a piece from the list of pieces
     * @param removedPiece
     * @param pieces
     */
    protected void removePiece(PieceInterface removedPiece, List<PieceInterface> pieces) {
        turn.removePiece(removedPiece.getLocation());
        pieces.remove(removedPiece);
    }

    /**
     * this method returns the piece, if found, at the given location
     * @param location
     * @param pieces
     * @return
     */
    public PieceInterface pieceAt(Location location, List<PieceInterface> pieces) {
        for(PieceInterface piece : pieces) {
            if(piece.getLocation().equals(location)) {
                return piece;
            }
        }
        return null;
    }

    /**
     * Helper function to see if potential move is legal
     * @param piece is the piece player is attempting to move
     * @param potentialLocation is the location the player is attempting to move the piece to
     * @return if the move is legal or not
     */
    public boolean tryMove(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces) {
        Location originalLocation = new Location(piece.getLocation().getRow(), piece.getLocation().getCol());
        PieceInterface takenPiece = null;

        // theoretically move piece to location
        if(pieceAt(potentialLocation, pieces) != null) { //take piece if exists
            takenPiece = pieceAt(potentialLocation, pieces);
            pieces.remove(takenPiece);
        }
        //move piece to new location
        piece.tryMove(potentialLocation);

        // look for checks
        List<PieceInterface> attackingPieces = getAttackingPieces(piece, pieces);

        // if the king is in check, undo move and return false
        if(underAttack(findKing(piece, pieces).getLocation(), attackingPieces)) {
            piece.tryMove(originalLocation);
            return false;
        }

        //otherwise undo the move and return true
        piece.tryMove(originalLocation);
        return true;
    }

    /**
     * this method returns the list of pieces that can potentially attack another piece
     * @param attackedPiece
     * @param allPieces
     * @return
     */
    protected List<PieceInterface> getAttackingPieces(PieceInterface attackedPiece, List<PieceInterface> allPieces) {
        List<PieceInterface> attackingPieces = new ArrayList<>();
        for(PieceInterface piece : allPieces) {
            if(!piece.getTeam().equals(attackedPiece.getTeam())) {
                attackingPieces.add(piece);
            }
        }
        return attackingPieces;
    }

    /**
     * this method returns the king, if found
     * @param teamPiece
     * @param pieces
     * @return
     */
    protected PieceInterface findKing(PieceInterface teamPiece, List<PieceInterface> pieces) {
        for(PieceInterface piece : pieces) {
            if(piece.getTeam().equals(teamPiece.getTeam()) && piece.getName().equals("K")) {
                return piece;
            }
        }
        return null;
    }

    /**
     * this method returns whether a row and column is in bounds on the chess grid
     * @param newRow
     * @param newCol
     * @return
     */
    protected boolean inBounds(int newRow, int newCol) {
        return (newRow < 8 && newCol < 8 && newRow >= 0 && newCol >= 0); //FIXME: hardcoded row col
    }

    /**
     * this mehtod resets the value and turn in a move
     */
    protected void resetMove() {
        endLocations = new ArrayList<>();
        turn = new Turn();
    }

    /**
     * this method adds a location to the list of end locations
     * @param location
     */
    protected void addEndLocation(Location location) {
        endLocations.add(location);
    }

    /**
     * this method returns the delta row movement
     * @return
     */
    protected int getdRow() {
        return dRow;
    }

    /**
     * this method returns the delta column movement
     * @return
     */
    protected int getdCol() {
        return dCol;
    }

    /**
     * this method returns whether a move is eligible to take pieces
     * @return
     */
    protected boolean canTake() {
        return take;
    }

    /**
     * this method returns whether a pieces movement is limited to a single grid square or is repeatable
     * @return
     */
    protected boolean isLimited(){
        return limited;
    }

    /**
     * this method gets the turn of a move
     * @return
     */
    public Turn getTurn() {
        return turn;
    }
}
