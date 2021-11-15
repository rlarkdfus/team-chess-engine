package ooga.model;

import java.util.List;

public class MoveVector implements MoveVectorInterface {

    private List<Vector> initialVectors;
    private List<Vector> moveVectors;
    private List<Vector> takeVectors;

    public MoveVector(List<Vector> initialVectors , List<Vector> moveVectors, List<Vector> takeVectors) {
        this.initialVectors = initialVectors;
        this.moveVectors = moveVectors;
        this.takeVectors = takeVectors;
    }

    /**
     * return the possible move vectors
     *
     * @return
     */

    @Override
    public List<Vector> getMoveVectors() {
        return moveVectors;
    }

    /**
     * reuturn the row vector of a move
     *
     * @param index
     * @return
     */
    @Override
    public int getRowVector(int index) {
        return moveVectors.get(index).getdRow();
    }

    /**
     * return the column vector of a move
     *
     * @param index
     * @return
     */
    @Override
    public int getColVector(int index) {

        return moveVectors.get(index).getdCol();
    }
}