package ooga.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javafx.beans.property.StringProperty;
import ooga.Location;
import ooga.model.Board;
import ooga.model.Engine;
import ooga.model.MoveTimer;
import ooga.view.View;
import ooga.view.ViewInterface;

public class Controller implements ControllerInterface {

  public static final File DEFAULT_CHESS_CONFIGURATION = new File("data/chess/defaultChess.json");
  public static final int DEFAULT_INITIAL_TIME = 10*60;
  public static final int DEFAULT_INITIAL_INCREMENT = 5;

  private Engine model;
  private ViewInterface view;
  private LocationWriter locationWriter;
  private MoveTimer whiteMoveTimer;
  private MoveTimer blackMoveTimer;

  public Controller() {
    try {
      BoardBuilder boardBuilder = new BoardBuilder(DEFAULT_CHESS_CONFIGURATION);
      view = new View(this);
      locationWriter = new LocationWriter();
      whiteMoveTimer = new MoveTimer(DEFAULT_INITIAL_TIME, DEFAULT_INITIAL_INCREMENT);
      blackMoveTimer = new MoveTimer(DEFAULT_INITIAL_TIME, DEFAULT_INITIAL_INCREMENT);
      buildGame(boardBuilder);
    }
    catch (Exception e) {
      view.showError(e.getMessage());
    }
  }

  @Override
  public void resetGame() {
    uploadConfiguration(DEFAULT_CHESS_CONFIGURATION);
    resetTimers();
  }

  @Override
  public boolean canMovePiece(Location location) {
    return model.canMovePiece(location);
  }

  @Override
  public void uploadConfiguration(File file)  {
    if (file == null) {
      return;
    }
    try {
      BoardBuilder boardBuilder = new BoardBuilder(file);
      buildGame(boardBuilder);
    }
    catch (Exception e) {
      view.showError(e.getMessage());
    }
  }

  @Override
  public void movePiece(Location start, Location end) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    view.updateDisplay(model.movePiece(start, end));
    if (model.checkGameState() != Board.GameState.RUNNING) {
      System.out.println(model.checkGameState()); //FIXME
    }
    incrementWhiteTime();
  }

  public List<Location> getLegalMoves(Location location){
    return model.getLegalMoves(location);
  }

  private void buildGame(BoardBuilder boardBuilder) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    model = new Board(boardBuilder.getInitialPlayers());
    view.initializeDisplay(boardBuilder.getInitialPieceViews());
  }

  public void downloadGame(String filePath) {
    try {
      locationWriter.saveCSV(filePath, model.getPlayers());
    }
    catch (IOException ignored) {
    }
  }

  //TODO: make this part use reflection
  public StringProperty getWhiteTimeLeft() {
    return whiteMoveTimer.getTimeLeft();
  }

  public StringProperty getBlackTimeLeft() {
    return blackMoveTimer.getTimeLeft();
  }

  public void incrementWhiteTime() {
    whiteMoveTimer.incrementTime();
  }

  public void incrementBlackTime() {
    blackMoveTimer.incrementTime();
  }

  public void setIncrement(double increment) {
    whiteMoveTimer.setIncrement((int) increment);
    blackMoveTimer.setIncrement((int) increment);
  }

  public void setInitialTime(double initialTime) {
    whiteMoveTimer.setInitialTime((int) (initialTime * 60));
    blackMoveTimer.setInitialTime((int) (initialTime * 60));
  }

  private void resetTimers() {
    whiteMoveTimer.reset();
    blackMoveTimer.reset();
  }

  public void pauseTimer() {
    whiteMoveTimer.pause();
  }

  public void resumeTimer() {
    whiteMoveTimer.start();
  }

  public void resetTimer() {
    whiteMoveTimer.reset();
  }
}
