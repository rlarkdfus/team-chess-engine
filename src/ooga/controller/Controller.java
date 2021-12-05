package ooga.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.beans.property.StringProperty;
import ooga.Location;
import ooga.model.Engine;
import ooga.view.LoginView;
import ooga.controller.Config.*;

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
  public abstract void movePiece(Location start, Location end);

  public List<Location> getLegalMoves(Location location) {
    return model.getLegalMoves(location);
  }

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