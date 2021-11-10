package ooga.Model;

import ooga.Location;

public interface Engine {
    /**
     * Create default board of pieces
     */
    void initializeBoard();

    /**
     * Moves piece from start to end and updates the board
     * @param start is piece initial location
     * @param end is piece new location
     */
    void movePiece(Location start, Location end);

    /**
     * Determine whether the win condition of the game is satisfied, and declare a winner.
     */
    boolean gameFinished();
}
