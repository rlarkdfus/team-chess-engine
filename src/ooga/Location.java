package ooga;

public class Location {

    private int row;
    private int col;

    public Location(int x, int y) {
        this.row = x;
        this.col = y;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean equals(Location other) {
        return this.row == other.getRow() && this.col == other.getCol();
    }
}
