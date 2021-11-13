package ooga.model;

import ooga.Location;

import java.util.ArrayList;
import java.util.List;

public class Piece implements PieceInterface{
    private MoveVector moveVector;

    private Location location;

    private String team;
    private boolean limited;
    private boolean hasMoved;
    private int score;
    private String pieceName;

    public Piece(Location location, String team, List<Vector> vectors, boolean limited) {
        this.location = location;
        this.team = team;
        this.limited = limited;
        hasMoved = false;
        moveVector = new MoveVector(vectors, vectors, vectors); //FIXME: change to different vectors
    }

//    public Piece(String pieceName, String team, List<Vector> vectors, boolean limited, int score) {
//        this.pieceName = pieceName;
//        this.team = team;
//        this.limited = limited;
//        moveVector = new MoveVector(vectors, vectors, vectors);
//        this.score = score;
//    }

    public Location getLocation(){
        return location;
    }

    public void updateLocation(Location location){
        this.location = location;
    }

    @Override
    public String getName() {return pieceName;}


    /**
     * gets white or black team, used for modifying move vector
     * @return
     */
    @Override
    public String getTeam() {
        return team;
    }

    /**
     * returns a list of all the possible move locations for a piece at given location
     * @param
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

    /**
     * returns the value of a piece
     * @return
     */
    @Override
    public int getScore() {
        return score;
    }

    /**
     * override toString to print out piece information
     * @return
     */
    @Override
    public String toString(){
        return moveVector.toString();
    }

    /**
     * holds the vector of move directions for each piece
     */
    public class MoveVector implements MoveVectorInterface {
        private boolean limited;
        private List<Vector> moveVectors;
        private List<Vector> takeVectors;
        private List<Vector> initialVectors;

        public MoveVector(List<Vector> moveVectors, List<Vector> takeVectors, List<Vector> initialVectors) {
            this.moveVectors = moveVectors;
            this.takeVectors = takeVectors;
            this.initialVectors = initialVectors;
        }

        /**
         * return the possible move vectors
         * @return
         */
        @Override
        public List<Vector> getMoveVectors() {
            return moveVectors;
        }

        /**
         * reuturn the row vector of a move
         * @param index
         * @return
         */
        @Override
        public int getRowVector(int index) {
            return moveVectors.get(index).getdRow();
        }

        /**
         * return the column vector of a move
         * @param index
         * @return
         */
        @Override
        public int getColVector(int index) {
            return moveVectors.get(index).getdCol();
        }
    }

    public static class Vector {
        int dRow;
        int dCol;

        public Vector(int dRow, int dCol) {
            this.dRow = dRow;
            this.dCol = dCol;
        }

        public int getdRow() {
            return dRow;
        }

        public int getdCol() {
            return dCol;
        }
    }

}
