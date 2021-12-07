package ooga.controller;

import ooga.Location;

import ooga.view.ViewInterface;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ooga.controller.Config.BoardBuilder;
import ooga.controller.Config.Builder;
import ooga.controller.Config.JSONWriter;
import ooga.controller.Config.JsonParser;
import ooga.controller.Config.LocationWriter;
import ooga.controller.Config.PieceViewBuilder;
import ooga.model.Engine;
import ooga.model.PieceInterface;
import ooga.view.util.ViewUtility;
import org.json.JSONObject;

/**
 * @Authors albert luis gordon sam tarun richard
 *
 * purpose - this class starts the game and connects the board and view, and holds all the game configuration objects.
 *  This is the parent class of editor and game controllers so this class holds all the common methods like
 *  creating the view and model objects, updating the view and model objects, and loading and reading
 *  configuration files. It also resets and quits the game.
 * assumptions - we assume that the defaultConfiguration file is valid. if this isn't the case, the
 *  game will crash.
 * dependencies - this class depends on the model classes, the view classes, and the boardbuilder classes.
 * To use - The user create a new controller object and a default view and model will be created. If a file
 *  is selected, the file is sent to boardbuilder and then new view and model objects are created and
 *  the game is remade.
 */
public abstract class Controller implements ControllerInterface {

  private static final String CONTROLLER_PATH = Controller.class.getPackageName() + ".";
  private static final String CONTROLLER_SUFFIX = "Controller";

  private Engine model;
  private ViewInterface view;
  private File jsonFile;

  /**
   * This constructor creates default model and view objects to that the player can either play the game,
   * or select a new configuration file and play a new game
   */
  public Controller() {
    jsonFile = getDefaultConfiguration();
    BoardBuilder boardBuilder = new BoardBuilder(jsonFile);
    model = initializeModel(boardBuilder);
    view = initializeView(boardBuilder);
  }

  /**
   * this method returns the defaultconfiguration file that is used to initiate the game and
   * reset the game
   * @return - a file object of a statically defined default file
   */
  protected abstract File getDefaultConfiguration();

  /**
   * this method initializes a model by using the objects made by the boardbuilder.
   * we assume that the boardbuilder has run .build() with a valid json file.
   * @param boardBuilder - a boardbuilder object that holds vital objects like the pieces, powerups, and endconditions
   * @return - a model object that is used to run the game
   */
  protected abstract Engine initializeModel(Builder boardBuilder);

  /**
   * this method initializes a view by using the objects made by the boardbuilder.
   * we assume that the boardbuilder has run .build() with a valid json file and that
   * the following parameters have been made.
   * @param boardBuilder - a boardbuilder object that holds vital objects like the pieces, powerups, and endconditions
   * @return - a view object that is used to display the game
   */
  protected abstract ViewInterface initializeView(Builder boardBuilder);

  protected void updateView() {
    List<PieceViewBuilder> pieces = createPieceViewList(model.getPieces());
    view.updateDisplay(pieces);
  }
  /**
   * Reset the game with the default board configuration
   */
  @Override
  public void reset() {
    BoardBuilder boardBuilder = new BoardBuilder(jsonFile);
    model = initializeModel(boardBuilder);
    view.initializeDisplay(boardBuilder.getInitialPieceViews(), boardBuilder.getPowerupLocations(), boardBuilder.getBoardSize());
  }

  /**
   * Quits the game
   */
  @Override
  public void quit() {
    System.exit(0);
  }

  /**
   * @param location is the desired destination of the move
   * @return whether the piece can be moved
   */
  @Override
  public boolean canMovePiece(Location location) {
    return model.canMovePiece(location);
  }

  /**
   * sets up a new game with the initial configuration file
   *
   * @param file the sim file with the initial board configuration
   */
  @Override
  public void uploadConfiguration(File file) {
      jsonFile = file;
      reset();
  }

  /**
   * moves a piece from the start location to the end location
   *
   * @param start is initial location of moved piece
   * @param end   is final location of moved piece
   */
  @Override
  public void movePiece(Location start, Location end) {
    model.movePiece(start, end);
    updateView();
  }

  /**
   * this method is called from view when a piece is clicked. When a piece is clicked, it's location
   * is sent via this method to the model to find all the possible moves it can legally go to based on
   * the defined rules. The returned list is used to highlight squares that the user can select as a
   * move
   * @param location - the location of the piece that is potentially getting moved
   * @return - a list of locations that the piece can go to
   */
  public List<Location> getLegalMoves(Location location) {
    return model.getLegalMoves(location);
  }

  /**
   * this method is called via a button in view. It copies down the current pieces and their locations to
   * a csv, and copies the current game json. These two files are identically named based on the given
   * filepath
   * @param filePath - the filepath of the to-be-saved game data
   */
  @Override
  public void downloadGame(String filePath) {
    try {
      JSONObject jsonObject = JsonParser.loadFile(jsonFile);
      JSONWriter.saveFile(jsonObject, filePath);
      LocationWriter locationWriter = new LocationWriter();
      locationWriter.saveCSV(filePath + ".csv", model.getPieces());
    } catch (IOException ignored) {
    }
  }

  /**
   * converts the pieces generated by the model during a turn into pieceViewbuilders.
   * These data objects are used to create  pieceview objects
   */
  protected List<PieceViewBuilder> createPieceViewList(List<PieceInterface> pieces) {
    List<PieceViewBuilder> pieceViewList = new ArrayList<>();
    pieces.forEach(piece -> pieceViewList.add(new PieceViewBuilder(piece)));
    return pieceViewList;
  }

  /**
   * launches a new controller from the selected game variation
   * @param variation the variation of the controller to use
   */
  public void launchController(String variation) {
    try {
      Class<?> clazz = Class.forName(CONTROLLER_PATH + variation + CONTROLLER_SUFFIX);
      ControllerInterface controller = (ControllerInterface) clazz.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      ViewUtility.showError("InvalidGameVariation");
    }
  }
}