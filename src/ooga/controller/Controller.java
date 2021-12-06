package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.StringProperty;
import ooga.Location;
import ooga.controller.Config.BoardBuilder;
import ooga.controller.Config.Builder;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.controller.Config.JSONWriter;
import ooga.controller.Config.JsonParser;
import ooga.controller.Config.LocationWriter;
import ooga.controller.Config.PieceViewBuilder;
import ooga.model.Engine;
import ooga.model.GameState;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;
import ooga.view.ViewInterface;
import org.json.JSONObject;

/**
 * @Authors albert luis gordon sam tarun richard
 *
 * purpose - this class starts the game and connects the board and view, and holds all the game configuration objects.
 *  This is the parent class of editor and game controllers. The purpose of this class is to communicate
 *  any information that needs to be send to and from the view and model. It also initiates the game
 *  by creating view and model objects by reading files and creating objects from them.
 * assumptions - we assume that the defaultConfiguration file is valid. if this isn't the case, the
 *  game will crash.
 * dependencies - this class depends on the our model classes, our view classes, and our boardbuilder classes.
 * To use - The user create a new controller object and a default view and model will be created. If a file
 *  is selected, the file is sent to boardbuilder and then new view and model objects are created and
 *  the game is remade.
 */
public abstract class Controller implements ControllerInterface {

  //TODO: change protected
  private Engine model;
  private ViewInterface view;
  private File jsonFile;
  private String selectedTeam;
  private String selectedName;

  protected int initialTime;
  protected int increment;

  public static final int DEFAULT_INITIAL_TIME = 5;
  public static final int DEFAULT_INITIAL_INCREMENT = 5;

  /**
   * This constructor creates default model and view objects to that the player can either play the game,
   * or select a new configuration file and play a new game
   */
  public Controller() {
    initialTime = DEFAULT_INITIAL_TIME;
    increment = DEFAULT_INITIAL_INCREMENT;

    jsonFile = getDefaultConfiguration();
    BoardBuilder boardBuilder = new BoardBuilder(jsonFile);
    model = initializeModel(boardBuilder);
    view = initializeView(boardBuilder.getInitialPieceViews(),boardBuilder.getBoardSize());
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
   * @param pieces -  a list of data objects that are used to produce javafx objects in view
   * @param bounds -  a location object that is used to define the bounds of the display board
   * @return - a view object that is used to display the game
   */
  protected abstract ViewInterface initializeView(List<PieceViewBuilder> pieces, Location bounds);

  /**
   * Reset the game with the default board configuration
   */
  @Override
  public void reset() {
    uploadConfiguration(getDefaultConfiguration());
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
    BoardBuilder boardBuilder = new BoardBuilder(file);
    jsonFile = file;
    model = initializeModel(boardBuilder);
    view = initializeView(boardBuilder.getInitialPieceViews(), boardBuilder.getBoardSize());
  }

  /**
   * moves a piece from the start location to the end location
   *
   * @param start is initial location of moved piece
   * @param end   is final location of moved piece
   */
  @Override
  public void movePiece(Location start, Location end) throws FileNotFoundException, InvalidPieceConfigException {
    List<PieceViewBuilder> pieceViewList = new ArrayList<>();
    for (PieceInterface piece : model.movePiece(start, end)) {
      pieceViewList.add(new PieceViewBuilder(piece));
    }
    view.updateDisplay(pieceViewList);
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
      JSONWriter jsonWriter = new JSONWriter();
      JSONObject jsonObject = JsonParser.loadFile(jsonFile);
      jsonWriter.saveFile(jsonObject, filePath);
      LocationWriter locationWriter = new LocationWriter();
      locationWriter.saveCSV(filePath + ".csv", model.getPlayers());
    } catch (IOException ignored) {
    }
  }

  /**
   * gets the amount of time left on a player's timer
   *
   * @param side the side of the player
   * @return a StringProperty ("mm:ss") representing the amount of time left
   */
  public StringProperty getTimeLeft(int side) {
    return model.getPlayers().get(side).getTimeLeft();
  }

  /**
   * this is called by the slider in view that allows the user to change the amount of time per game
   * upon resetting the game, the new game will its timer start at this time.
   * @param minutes - the new time per game
   */
  public abstract void setInitialTime(int minutes);

  /**
   * this is called by the slider in view that allows the user to change the amount of time gained per
   * move upon resetting the game, the new game will increment the timer by this amount.
   * @param seconds - the new time bonus per played move
   */
  public abstract void setIncrement(int seconds);


  public boolean selectMenuPiece(String team, String name) {
    if (selectedTeam == null) {
      selectedTeam = team;
      selectedName = name;
      return true;
    }
    if (selectedTeam.equals(team) && selectedName.equals(name)) {
      selectedTeam = null;
      selectedName = null;
      return false;
    }
    return false;
  }

  public boolean hasMenuPiece() {
    return selectedTeam != null || selectedName != null;
  }

  public void addPiece(Location location) {
    model.addPiece(selectedTeam, selectedName, location);
    List<PieceInterface> pieces = new ArrayList<>();
    for (PlayerInterface player : model.getPlayers()) {
      pieces.addAll(player.getPieces());
    }
    view.updateDisplay(createPieceViewList(pieces));
  }

  private List<PieceViewBuilder> createPieceViewList(List<PieceInterface> pieces) {
    List<PieceViewBuilder> pieceViewList = new ArrayList<>();
    for (PieceInterface piece : pieces) {
      pieceViewList.add(new PieceViewBuilder(piece));
    }
    return pieceViewList;
  }

  protected GameState getGameState() {
    return model.checkGameState();
  }

}