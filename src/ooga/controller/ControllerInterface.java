package ooga.controller;

import java.io.File;
import ooga.Location;

public interface ControllerInterface {

    /**
     * View updates pieces on the board
     */
    void updateView();

    /**
     * View calls loadFile to choose a file to load a game
     */
    void loadFile(File file);

    /**
     * View calls movePiece when user moves piece on GUI, which moves piece in model
     * movePiece calls updateView();
     * @param start is initial location of moved piece
     * @param end is final location of moved piece
     */
    void movePiece(Location start, Location end);
}
