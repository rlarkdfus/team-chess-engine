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
        this.turn = new Turn();
        this.endLocations = new ArrayList<>();
    }

    public abstract List<PieceInterface> executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end);

    public abstract void updateMoveLocations(PieceInterface piece, List<PieceInterface> pieces);

    public List<Location> getEndLocations() {
        for(Location location : endLocations) {
        }
        return endLocations;
    }


    abstract boolean isLegal(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces);


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
        for(PieceInterface atackingPiece : attackingPieces) {
            for(Location attackLocation : atackingPiece.getEndLocations()) {
                if(location.equals(attackLocation)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean isClear(List<Location> locations, List<PieceInterface> pieces) {
        for(Location location : locations) {
            for(PieceInterface piece : pieces) {
                if(piece.getLocation().equals(location)) {
                    return false;
                }
            }
        }
        return true;
    }

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
        Location pieceLocation = new Location(piece.getLocation().getRow(), piece.getLocation().getCol());
        PieceInterface takenPiece = null;

        // theoretically move piece to location
        if(pieceAt(potentialLocation, pieces) != null) { //take piece if exists
            takenPiece = pieceAt(potentialLocation, pieces);
            pieces.remove(takenPiece);
        }
        //move piece to new location
        piece.tryMove(potentialLocation);

        // look for checks
        List<PieceInterface> attackingPieces = new ArrayList<>();
        for(PieceInterface attackingPiece : pieces) {
            if(!piece.getTeam().equals(attackingPiece.getTeam())) {
                attackingPieces.add(attackingPiece);
            }
        }

        // if the king is in check, undo move and return false
        if(underAttack(findKing(pieces).getLocation(), attackingPieces)) {
            undoTryMove(piece, pieceLocation, takenPiece, pieces);
            return false;
        }

        //otherwise undo the move and return true
        undoTryMove(piece, pieceLocation, takenPiece, pieces);
        return true;
    }

    protected PieceInterface findKing(List<PieceInterface> pieces) {
        for(PieceInterface piece : pieces) {
            if(piece.getName().equals("K")) {
                return piece;
            }
        }
        return null;
    }


    /**
     * Undo the tried move after trying the move
     * @param piece is the piece player moved
     * @param pieceLocation is the original location the player is attempting to move the piece to
     * @param takenPiece is the piece that was taken during the turn, if a piece was taken
     */
    protected void undoTryMove(PieceInterface piece, Location pieceLocation, PieceInterface takenPiece, List<PieceInterface> pieces) {
        piece.tryMove(pieceLocation);
        if (takenPiece != null) {
            pieces.add(takenPiece);
        }
    }

    protected boolean inBounds(int newRow, int newCol) {
        return (newRow < 8 && newCol < 8 && newRow >= 0 && newCol >= 0); //FIXME: hardcoded row col
    }

    protected void resetEndLocations() {
        endLocations = new ArrayList<>();
        turn = new Turn();
    }

    protected void addEndLocation(Location location) {
        System.out.println(location);
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
