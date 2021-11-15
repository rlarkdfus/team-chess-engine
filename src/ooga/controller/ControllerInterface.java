package ooga.controller;

import java.io.File;
import java.util.List;

import ooga.Location;
import ooga.view.PieceView;

public interface ControllerInterface {

    /**
     * View updates pieces on the board
     */
    void updateView();

    /**
     * View calls loadFile to choose a file to load a game
     */
    void loadFile(File file) throws Exception;

    /**
     * View calls movePiece when user moves piece on GUI, which moves piece in model
     * movePiece calls updateView();
     * @param start is initial location of moved piece
     * @param end is final location of moved piece
     */
    void movePiece(Location start, Location end);

    /**
     * Returns true if the piece at the location of user click can be moved
     * @param location is the location of piece
     */
    boolean canMovePiece(Location location);

    List<Location> getLegalMoves(Location location);

    PieceView[][] sendInitialBoardView(String style);
}
