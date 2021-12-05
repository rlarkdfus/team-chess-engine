package ooga;

import java.util.List;

public class Location {

    private int row;
    private int col;

    public Location(int row, int col) {
        this.row = row;
        this.col = col;
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

    public boolean inList(List<Location> list) {
        for (Location location : list) {
            if (this.equals(location)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return row + " " + col;
    }
}
