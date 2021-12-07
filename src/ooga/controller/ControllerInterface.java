package ooga.controller;

import java.io.File;
import java.util.List;

import javafx.scene.input.KeyCode;
import ooga.Location;

/**
 * @Authors gordon albert luis richard tarun sam
 * <p>
 * purpose - this class defines the shared methods that are common to all Controller type objects.
 * assumptions - that all the methods below are defined dependencies - this class depends on the
 * model classes, the view classes, and the boardbuilder classes. To use - The user create a new
 * controller object and a default view and model will be created. If a file is selected, the file
 * is sent to boardbuilder and then new view and model objects are created and the game is remade.
 */
public interface ControllerInterface {

  /**
   * Resets a game to its initial/default state
   */
  void reset();

  /**
   * This method sends the file to boardbuilder which builds objects based on the file's data we
   * assume that this file is a .json file
   *
   * @param file - the json file that holds the configuration data
   */
  void uploadConfiguration(File file);

  /**
   * this method is called via a button in view. It copies down the current pieces and their
   * locations to a csv, and copies the current game json. These two files are identically named
   * based on the given filepath
   *
   * @param filePath - the filepath of the to-be-saved game data
   */
  void downloadGame(String filePath);

  /**
   * this method quits the game and closes all windows by shutting down all application objects.
   */
  void quit();

  /**
   * View calls movePiece when user moves piece on GUI, which moves piece in model movePiece calls
   * updateView();
   *
   * @param start is initial location of moved piece
   * @param end   is final location of moved piece
   */
  void movePiece(Location start, Location end);

  /**
   * Returns true if the piece at the location of user click can be moved. This is determined in
   * model and prevents the user from making an illegal move.
   *
   * @param location is the location of piece
   */
  boolean canMovePiece(Location location);

  /**
   * this method is called from view when a piece is clicked. When a piece is clicked, it's location
   * is sent via this method to the model to find all the possible moves it can legally go to based
   * on the defined rules. The returned list is used to highlight squares that the user can select
   * as a move
   *
   * @param location - the location of the piece that is potentially getting moved
   * @return - a list of locations that the piece can go to
   */
  List<Location> getLegalMoves(Location location);

  /**
   * launches a new controller from the selected game variation
   * @param controllerVariation the variation to launch a new controller for
   * @throws Throwable if class not found or no such method
   */
  void launchController(String controllerVariation) throws Throwable;

  void handleKeyPress(KeyCode code);
}
