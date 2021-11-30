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

    public void setMove(int dRow, int dCol, boolean take, boolean limited){
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

        while(inBounds(row, col)){
            if(pieceAt(potentialLocation, pieces) != null) {
                if(pieceAt(potentialLocation, pieces).isSameTeam(piece) || !take) {
                    break;
                }
                endLocations.add(potentialLocation);
                break;
            }

            endLocations.add(potentialLocation);

            if(limited) {
                break;
            }

            row += getdRow();
            col += getdCol();
            potentialLocation = new Location(row, col);
        }
        return endLocations;
    }

    /**
     * Checks if the king is under attack from enemy pieces
     * @param attackingPieces is the list of pieces attacking the king
     * @return true if the king is under attack from list of pieces
     */
    public boolean underAttack(Location location, List<PieceInterface> attackingPieces, List<PieceInterface> allPieces) {
        for(PieceInterface attackingPiece : attackingPieces) {
            List<Move> attackingMoves = attackingPiece.getMoves();
            for(Move attackingMove : attackingMoves) {
                if(location.inList(attackingMove.findAllEndLocations(attackingPiece, allPieces))) {
                    return true;
                }
            }

        }
        return false;
    }

    protected boolean isClear(List<Location> locations, List<PieceInterface> pieces) {
        for(PieceInterface piece : pieces) {
            if(piece.getLocation().inList(locations)) {
                return false;
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
        Location originalLocation = new Location(piece.getLocation().getRow(), piece.getLocation().getCol());
        PieceInterface takenPiece = tryTakeMove(potentialLocation, pieces);

        // theoretically move piece to location
        if(takenPiece != null) { //take piece if exists
            pieces.remove(takenPiece);
        }
        //move piece to new location
        piece.tryMove(potentialLocation);

        return !inCheck(piece, pieces, originalLocation);
    }

    private boolean inCheck(PieceInterface piece, List<PieceInterface> pieces, Location originalLocation) {
        // look for checks
        List<PieceInterface> attackingPieces = getAttackingPieces(piece, pieces);

        // if the king is in check, undo move and return false
        if(findKing(piece, pieces) == null) {
            return true;
        }
        if(underAttack(findKing(piece, pieces).getLocation(), attackingPieces, pieces)) {
            piece.tryMove(originalLocation);
            return true;
        }

        //otherwise undo the move and return true
        piece.tryMove(originalLocation);
        return false;
    }

    protected PieceInterface tryTakeMove(Location location, List<PieceInterface> pieces) {
        return pieceAt(location, pieces);
    }

    protected List<PieceInterface> getAttackingPieces(PieceInterface attackedPiece, List<PieceInterface> allPieces) {
        List<PieceInterface> attackingPieces = new ArrayList<>();
        for(PieceInterface piece : allPieces) {
            if(!piece.isSameTeam(attackedPiece)) {
                attackingPieces.add(piece);
            }
        }
        return attackingPieces;
    }

    protected PieceInterface findKing(PieceInterface teamPiece, List<PieceInterface> pieces) {
        for(PieceInterface piece : pieces) {
            if(teamPiece.isSameTeam(piece) && piece.getName().equals("K")) {
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
