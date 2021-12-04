package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javafx.beans.property.StringProperty;
import ooga.Location;
import ooga.controller.Config.BoardBuilder;
import ooga.controller.Config.Builder;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.controller.Config.JSONWriter;
import ooga.controller.Config.LocationWriter;
import ooga.model.Board;
import ooga.model.Engine;

/**
 * This class abstracts some common complexities of handling interactions between the model and the
 * view so that subclasses extenidng th
 */
public abstract class Controller implements ControllerInterface {

  public static final File DEFAULT_CHESS_CONFIGURATION = new File("data/chess/defaultChess.json");

  //TODO: change protected
  protected Engine model;
  private LocationWriter locationWriter;
  protected Builder boardBuilder;
  private File jsonFile;

  public Controller() {
    jsonFile = DEFAULT_CHESS_CONFIGURATION;
    boardBuilder = new BoardBuilder(DEFAULT_CHESS_CONFIGURATION);
    model = new Board(boardBuilder.getInitialPlayers());
    start();
  }

  protected abstract void start();

  /**
   * Reset the game with the default board configuration
   */
  @Override
  public void reset() {
    try {
      uploadConfiguration(DEFAULT_CHESS_CONFIGURATION);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Quits the game
   */
  @Override
  public void quit() {System.exit(0);}

  /**
   * @param location is the desired destination of the move
   * @return whether the piece can be moved
   */
  @Override
  public abstract boolean canMovePiece(Location location);

  /**
   * sets up a new game with the initial configuration file
   *
   * @param file the sim file with the initial board configuration
   */
  @Override
  public void uploadConfiguration(File file) {
    try {
      if (file == null) {
        return;
      }
      jsonFile = file;
      boardBuilder.build(file);
      start();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * moves a piece from the start location to the end location
   *
   * @param start is initial location of moved piece
   * @param end   is final location of moved piece
   */
  @Override
  public abstract void movePiece(Location start, Location end) throws FileNotFoundException, InvalidPieceConfigException;

  public abstract List<Location> getLegalMoves(Location location);

  @Override
  public void downloadGame(String filePath) {
    try {
      JSONWriter jsonWriter = new JSONWriter();
      jsonWriter.saveFile(jsonFile, filePath);
      locationWriter = new LocationWriter();
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
}
