package ooga.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import ooga.Location;
import ooga.model.Board;
import ooga.model.Engine;
import ooga.view.View;
import ooga.view.ViewInterface;

public class Controller implements ControllerInterface {

  private final File DEFAULT_CHESS_CONFIGURATION = new File("data/chess/defaultChess.json");

  private Engine model;
  private ViewInterface view;
  private LocationWriter locationWriter;

  public Controller() {
    try {
      BoardBuilder boardBuilder = new BoardBuilder(DEFAULT_CHESS_CONFIGURATION);
      view = new View(this);
      this.locationWriter = new LocationWriter();
      buildGame(boardBuilder);
    }
    catch (Exception e) {
      view.showError(e.getMessage());
    }
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
}
