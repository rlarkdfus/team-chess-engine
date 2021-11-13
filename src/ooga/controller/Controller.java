package ooga.controller;

import ooga.Location;
import ooga.model.Board;
import ooga.model.Engine;
import ooga.view.View;
import ooga.view.ViewController;
import ooga.view.ViewInterface;

import java.util.List;

public class Controller implements ControllerInterface {

  private Engine model;
  private ViewInterface view;
  private ViewController viewController;

  public Controller() {
    model = new Board();
    //viewController = new ViewController();
    view = new View(this);
    initializeGame();
  }

  public ViewController getViewController() {
    return viewController;
  }

  private void initializeGame() {
    model.initializeBoard();
    view.initializeDisplay();
  }

  @Override
  public void updateView() {

  }

  @Override
  public void loadFile() {
//    model = builder.buildModel();
  }

  @Override
  public void movePiece(Location start, Location end) {
    view.updateDisplay(model.movePiece(start, end));
  }

  public List<Location> getLegalMoves(Location location){
    return model.getLegalMoves(location);
  }
}
