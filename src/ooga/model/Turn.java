package ooga.model;

import ooga.Location;

import java.util.ArrayList;
import java.util.List;

public class Turn {
    private List<PieceMove> moves;
    private List<Location> removedPieces;

    public Turn() {
        moves = new ArrayList<>();
        removedPieces = new ArrayList<>();
    }

    public void movePiece(Location start, Location end){
        moves.add(new PieceMove(start, end));
    }

    public void removePiece(Location location) {
        removedPieces.add(location);
    }

    public class PieceMove {
        private Location start;
        private Location end;

        public PieceMove(Location start, Location end) {
            this.start = start;
            this.end = end;
        }

        public Location getStartLocation() {
            return start;
        }

        public Location getEndLocation() {
            return end;
        }
    }
}