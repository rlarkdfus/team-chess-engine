package ooga.model.Moves;

import ooga.Location;
import ooga.Turn;
import ooga.model.Piece;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

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

    public abstract void executeMove(PieceInterface piece, List<PieceInterface> pieces, Location end);

    public abstract void updateMoveLocations(PieceInterface piece, List<PieceInterface> pieces);

    public List<Location> getEndLocations() {
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
        for(PieceInterface attackingPiece : attackingPieces) {
            for(Location attackLocation : attackingPiece.getEndLocations()) {
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

    protected void movePiece(PieceInterface piece, Location end) {
        turn.movePiece(piece.getLocation(), end);
        piece.moveTo(end);
    }

    protected void removePiece(PieceInterface removedPiece, List<PieceInterface> pieces) {
        turn.removePiece(removedPiece.getLocation());
        pieces.remove(removedPiece);
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
        List<PieceInterface> attackingPieces = getAttackingPieces(piece, pieces);

        // if the king is in check, undo move and return false
        if(underAttack(findKing(piece, pieces).getLocation(), attackingPieces)) {
            piece.tryMove(pieceLocation);
            return false;
        }

        //otherwise undo the move and return true
        piece.tryMove(pieceLocation);
        return true;
    }

    protected List<PieceInterface> getAttackingPieces(PieceInterface attackedPiece, List<PieceInterface> allPieces) {
        List<PieceInterface> attackingPieces = new ArrayList<>();
        for(PieceInterface piece : allPieces) {
            if(!piece.getTeam().equals(attackedPiece.getTeam())) {
                attackingPieces.add(piece);
            }
        }
        return attackingPieces;
    }

    protected PieceInterface findKing(PieceInterface teamPiece, List<PieceInterface> pieces) {
        for(PieceInterface piece : pieces) {
            if(piece.toString().equals(teamPiece.getTeam() + "K")) {
                return piece;
            }
        }
        return null;
    }

    protected boolean inBounds(int newRow, int newCol) {
        return (newRow < 8 && newCol < 8 && newRow >= 0 && newCol >= 0); //FIXME: hardcoded row col
    }

    protected void resetMove() {
        endLocations = new ArrayList<>();
        turn = new Turn();
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
