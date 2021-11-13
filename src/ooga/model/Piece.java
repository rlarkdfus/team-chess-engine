package ooga.model;

import ooga.Location;

import java.util.ArrayList;
import java.util.List;

public class Piece implements PieceInterface{
    private MoveVector moveVector;
    private int team;
    private boolean limited;

    public Piece(int team, List<List<Integer>> vectors, boolean limited) {
        this.team = team;
        this.limited = limited;
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
    public MoveVector getMoves() {
        return moveVector;
    }

    /**
     * returns whether a piece is able to move in a limited fashion
     * @return
     */
    @Override
    public boolean isLimited() {
        return limited;
    }
//    @Override
//    public List<Location> getMoves(Location location) {
//        List<Location> moves = new ArrayList<>();
//        for(int i = 0; i < moveVector.getMoveVectors().size(); i++) {
//
//            int row = location.getX() + moveVector.getRowVector(i);
//            int col = location.getX() + moveVector.getColVector(i);
//
//            Location move = new Location(row, col);
//            moves.add(move);
//            if(limited) {
//                break;
//            }
//        }
//        return moves;
//    }

    /**
     * holds the vector of move directions for each piece
     */
    public class MoveVector {
        private boolean limited;
        private List<List<Integer>> vectors;

        public MoveVector(List<List<Integer>> vectors) {
            this.vectors = vectors;
        }

        /**
         * return the possible move vectors
         * @return
         */
        public List<List<Integer>> getMoveVectors() {
            return vectors;
        }

        /**
         * reuturn the row vector of a move
         * @param index
         * @return
         */
        public int getRowVector(int index) {
            return vectors.get(index).get(0);
        }

        /**
         * return the column vector of a move
         * @param index
         * @return
         */
        public int getColVector(int index) {
            return vectors.get(index).get(1);
        }

//        public boolean isLimited(){
//            return limited;
//        }
    }

}
