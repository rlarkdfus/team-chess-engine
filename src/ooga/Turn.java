package ooga;

import java.util.ArrayList;
import java.util.List;

public class Turn {
    private List<PieceMove> moves;
    private List<Location> removedPieces;
    private Location checkedSquare;

    public Turn() {
        moves = new ArrayList<>();
        removedPieces = new ArrayList<>();
        checkedSquare = null;
    }

    /**
     * add moved piece to list
     * @param start
     * @param end
     */
    public void movePiece(Location start, Location end){
        moves.add(new PieceMove(start, end));
    }

    /**
     * add removed piece to list
     * @param location
     */
    public void removePiece(Location location) {
        removedPieces.add(location);
    }

    /**
     * add square to be highlighed when checked
     * @param location
     */
    public void addCheckedSquare(Location location) {
        checkedSquare = location;
    }

    /**
     * return the moved pieces
     * @return
     */
    public Location getCheckedSquare(){
        return checkedSquare;
    }

    /**
     * return the moved pieces
     * @return
     */
    public List<PieceMove> getMoves(){
        return moves;
    }

    /**
     * return the removed pieces
     * @return
     */
    public List<Location> getRemoved(){
        return removedPieces;
    }

    public class PieceMove {
        private Location start;
        private Location end;

        public PieceMove(Location start, Location end) {
            this.start = start;
            this.end = end;
        }

        /**
         * get the starting location
         * @return
         */
        public Location getStartLocation() {
            return start;
        }

        /**
         * get the ending location
         * @return
         */
        public Location getEndLocation() {
            return end;
        }
    }
}