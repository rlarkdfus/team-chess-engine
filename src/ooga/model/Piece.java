package ooga.model;

import ooga.Location;

import java.util.ArrayList;
import java.util.List;

public class Piece implements PieceInterface{
    private MoveVector moveVector;
    private int team;

    public Piece(int team, List<List<Integer>> vectors) {
        this.team = team;
        moveVector = new MoveVector(vectors);
    }

    /**
     * gets white or black team, used for modifying move vector
     * @return
     */
    @Override
    public int getTeam() {
        return team;
    }

    /**
     * returns a list of all the possible move locations for a piece at given location
     * @param location
     * @return
     */
    @Override
    public List<Location> getMoves(Location location) {
        List<Location> moves = new ArrayList<>();
        for(int i = 0; i < moveVector.getMoveVectors().size(); i++) {
            int row = location.getX() + moveVector.getRowVector(i);
            int col = location.getX() + moveVector.getColVector(i);

            Location move = new Location(row, col);
            moves.add(move);
        }
        return moves;
    }

    /**
     * holds the vector of move directions for each piece
     */
    public class MoveVector {
        private List<List<Integer>> vectors;

        public MoveVector(List<List<Integer>> vectors) {
            this.vectors = vectors;
        }

        public List<List<Integer>> getMoveVectors() {
            return vectors;
        }

        public int getRowVector(int index) {
            return vectors.get(index).get(0);
        }

        public int getColVector(int index) {
            return vectors.get(index).get(1);
        }
    }

}
