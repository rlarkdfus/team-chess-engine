package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javafx.beans.property.StringProperty;
import ooga.Location;
import ooga.Turn;
import ooga.model.Board;
import ooga.model.Board.GameState;
import ooga.model.Engine;
import ooga.view.LoginView;
import ooga.view.GameOverScreen;
import ooga.view.View;
import ooga.view.ViewInterface;

public class Controller implements ControllerInterface {

  public static final File DEFAULT_CHESS_CONFIGURATION = new File("data/chess/defaultChess.json");
  public static final int DEFAULT_INITIAL_TIME = 10;
  public static final int DEFAULT_INITIAL_INCREMENT = 5;

  private Engine model;
  private ViewInterface view;
  private LocationWriter locationWriter;
  private Builder boardBuilder;
  private TimeController timeController;
  private LoginController loginController;
  private File jsonFile;
  private LoginView loginView;
  private GameOverScreen gameOverScreen;

  public Controller() {
    initializeLogin();
  }

  public void handleLoginAttempt(String username, String password) throws Exception {
    if (loginController.isValidLogin(username, password)) {
      startGame();
    }
    else {
      // handle incorrect password label;
    }
  }

  /**
   * Reset the game with the default board configuration
   */
  @Override
  public void resetGame() {uploadConfiguration(DEFAULT_CHESS_CONFIGURATION);}

  /**
   * Quits the game
   */
  @Override
  public void quit() {System.exit(0);}

  /**
   * @param location is the desired destination of the move
   * @return whether the piece can be moved to the location
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
    if (file == null) {
      return;
    }
    try {
      jsonFile = file;
      boardBuilder.build(file);
      buildGame(boardBuilder);
      view.resetDisplay(boardBuilder.getInitialPieceViews());
      startTimersForNewGame();
    } catch (Exception E) {
      view.showError(E.toString());
    }
  }

  /**
   * reset timers for a new game and start the first player's timer
   */
  private void startTimersForNewGame() {
    timeController.resetTimers(model.getPlayers());
    timeController.startPlayer1Timer(model.getPlayers());
  }

  /**
   * moves a piece from the start location to the end location
   *
   * @param start is initial location of moved piece
   * @param end   is final location of moved piece
   * @throws InvocationTargetException
   * @throws NoSuchMethodException
   * @throws IllegalAccessException
   */
  @Override
  public void movePiece(Location start, Location end) throws
      InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException, InvalidPieceConfigException {
    Turn turn = model.movePiece(start, end);
    view.updateDisplay(turn);
    GameState gameState = model.checkGameState();
    if (gameState != Board.GameState.RUNNING) {
      String winner = model.getWinner();
      gameOverScreen = new GameOverScreen(this, winner);
    }
  }

  public List<Location> getLegalMoves(Location location) {
    return model.getLegalMoves(location);
  }


  public void downloadGame(String filePath) {
    try {
      JSONWriter jsonWriter = new JSONWriter();
      jsonWriter.saveFile(jsonFile, filePath);

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
   * tells the timeController to change the initial time for the next game
   *
   * @param minutes the new initial time (min)
   */
  public void setInitialTime(int minutes) {
    timeController.setInitialTime(minutes);
  }

  /**
   * tells the timeController to change the increment time for the next game
   *
   * @param seconds the new increment (s)
   */
  public void setIncrement(int seconds) {
    timeController.setIncrement(seconds);
  }

  private void initializeLogin() {
    loginController = new LoginController();
    loginView = new LoginView(this);
    loginView.initializeDisplay();
  }

  private void buildGame(Builder boardBuilder) throws
      InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    model = new Board(boardBuilder.getInitialPlayers());
    timeController.configTimers(model.getPlayers());
    model.setEndCondition(boardBuilder.getEndConditionHandler());
    view.initializeDisplay(boardBuilder.getInitialPieceViews());
  }
  private void startGame() {
    loginView.hideDisplay();
    view = new View(this);
    try {
      jsonFile = DEFAULT_CHESS_CONFIGURATION;
      boardBuilder = new BoardBuilder(DEFAULT_CHESS_CONFIGURATION);
      timeController = new TimeController(DEFAULT_INITIAL_TIME, DEFAULT_INITIAL_INCREMENT);
      locationWriter = new LocationWriter();
      buildGame(boardBuilder);
      timeController.configTimers(model.getPlayers());
      startTimersForNewGame();
      view.initializeDisplay(boardBuilder.getInitialPieceViews());
    }
    catch (Exception e){
      view.showError(e.getMessage());
    }
  }

}
