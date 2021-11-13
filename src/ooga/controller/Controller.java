package ooga.controller;

import ooga.Location;
import ooga.model.Board;
import ooga.model.Engine;
import ooga.view.View;
import ooga.view.ViewInterface;

public class Controller implements ControllerInterface{

  private Engine model;
  private ViewInterface view;

  public Controller(){
    model = new Board();
    view = new View();

    initializeGame();
  }

  private void initializeGame() {
    model.initializeBoard();
    view.updateDisplay();
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

  }
}
