package ooga.Controller;

import ooga.Model.Location;

public interface Controller {

    /**
     * View updates pieces on the board
     */
    void updateView();

    /**
     * View calls loadFile to choose a file to load a game
     */
    void loadFile();

    /**
     * View calls movePiece when user moves piece on GUI, which moves piece in model
     * movePiece calls updateView();
     * @param start is initial location of moved piece
     * @param end is final location of moved piece
     */
    void movePiece(Location start, Location end);
}
