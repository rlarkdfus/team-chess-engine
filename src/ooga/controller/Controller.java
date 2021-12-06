package ooga.controller;

import javafx.beans.property.StringProperty;
import ooga.Location;
import ooga.model.Engine;
import ooga.model.GameState;
import ooga.model.PieceInterface;
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
import org.json.JSONObject;

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

  public Controller() {
    initialTime = DEFAULT_INITIAL_TIME;
    increment = DEFAULT_INITIAL_INCREMENT;

    jsonFile = getDefaultConfiguration();
    BoardBuilder boardBuilder = new BoardBuilder(jsonFile);
    model = initializeModel(boardBuilder);
    view = initializeView(boardBuilder.getInitialPieceViews(),boardBuilder.getBoardSize());
  }

  protected abstract File getDefaultConfiguration();

  protected abstract Engine initializeModel(Builder boardBuilder);

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
  public void movePiece(Location start, Location end) {
    List<PieceViewBuilder> pieceViewList = new ArrayList<>();
    model.movePiece(start, end).forEach(piece -> pieceViewList.add(new PieceViewBuilder(piece)));
    view.updateDisplay(pieceViewList);
  }

  public List<Location> getLegalMoves(Location location) {
    return model.getLegalMoves(location);
  }

  @Override
  public void downloadGame(String filePath) {
    try {
      JSONObject jsonObject = JsonParser.loadFile(jsonFile);
      JSONWriter.saveFile(jsonObject, filePath);
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

  public abstract void setInitialTime(int minutes);

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
    model.getPlayers().forEach(player -> pieces.addAll(player.getPieces()));
    view.updateDisplay(createPieceViewList(pieces));
  }

  private List<PieceViewBuilder> createPieceViewList(List<PieceInterface> pieces) {
    List<PieceViewBuilder> pieceViewList = new ArrayList<>();
    pieces.forEach(piece -> pieceViewList.add(new PieceViewBuilder(piece)));
    return pieceViewList;
  }

  protected GameState getGameState() {
    return model.checkGameState();
  }

}