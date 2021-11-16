package ooga.model;

public class Vector implements VectorInterface {

    int dRow;
    int dCol;

    public Vector(int dRow, int dCol) {
        this.dRow = dRow;
        this.dCol = dCol;
    }

    @Override
    public int getdRow() {
        return dRow;
    }

    @Override
    public int getdCol() {
        return dCol;
    }
}