package ooga.model;

import java.util.List;
import java.util.Map;

public class Piece implements PieceInterface{
    private MoveVectors moveVectors;
    private MoveVectors takeMoveVectors;
    private MoveVectors initialMoveVectors;

    private String team;
    private boolean limited;
    private Map<String, Boolean> attributes;


    public Piece(String team, Map<String, List<List<Integer>>> vectors,
        Map<String, Boolean> attributes, String pieceImagePath) {
        this.team = team;
        this.limited = limited;
        this.attributes = attributes;
        moveVectors = new MoveVectors(vectors.get("moveVectors"));
        takeMoveVectors = new MoveVectors(vectors.get("takeMoveVectors"));
        initialMoveVectors = new MoveVectors(vectors.get("initialMoveVectors"));

    }

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
    public MoveVectors getMoves() {
        return moveVectors;
    }

    /**
     * returns whether a piece is able to move in a limited fashion
     * @return
     */
    @Override
    public boolean isLimited() {
        return attributes.get("limited");
    }

    @Override
    public String toString(){
        return moveVectors.toString();
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
    public class MoveVectors {
        private boolean limited;
        private List<List<Integer>> moveVectors;
        public MoveVectors(List<List<Integer>> vectors) {
            this.moveVectors = vectors;
        }

        /**
         * return the possible move vectors
         * @return
         */
        public List<List<Integer>> getMoveVectors() {
            return moveVectors;
        }

        /**
         * reuturn the row vector of a move
         * @param index
         * @return
         */
        public int getRowVector(int index) {
            return moveVectors.get(index).get(0);
        }

        /**
         * return the column vector of a move
         * @param index
         * @return
         */
        public int getColVector(int index) {
            return moveVectors.get(index).get(1);
        }

//        public boolean isLimited(){
//            return limited;
//        }

//        @Override
//        public String toString(){
//            StringBuilder str = new StringBuilder();
//
//            for(List<Integer> v)
//        }
    }

}
