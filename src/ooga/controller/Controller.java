package ooga.controller;

import java.io.File;
import ooga.Location;
import ooga.model.Board;
import ooga.model.Engine;
import ooga.view.View;
import ooga.view.ViewController;
import ooga.view.ViewInterface;

import java.util.List;
import org.json.JSONObject;

public class Controller implements ControllerInterface {

  private Engine model;
  private ViewInterface view;
  private Parser parser;

  public Controller(){
    model = new Board();
    view = new View(this);
    parser = new Parser();
    initializeGame();
  }

  private void initializeGame() {
    model.initializeBoard();
    view.initializeDisplay();
  }

  @Override
  public void updateView() {

  }

  @Override
  public void loadFile(File file) {
    JSONObject jsonObject = parser.loadFile(file);
  }

  @Override
  public void movePiece(Location start, Location end) {
    view.updateDisplay(model.movePiece(start, end));
  }

  public List<Location> getLegalMoves(Location location){
    return model.getLegalMoves(location);
  }
}
