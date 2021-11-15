package ooga.controller;

import java.io.File;
import java.util.List;
import ooga.Location;
import ooga.model.Board;
import ooga.model.Engine;
import ooga.view.View;
import ooga.view.ViewInterface;
import org.json.JSONObject;

public class Controller implements ControllerInterface {

  private Engine model;
  private ViewInterface view;

  private JsonParser jsonParser;

  public Controller(){
    model = new Board();
    view = new View(this);

    jsonParser = new JsonParser();
  }


  @Override
  public boolean canMovePiece(Location location) {
    return model.canMovePiece(location);
  }

  @Override
  public void updateView() {

  }

  @Override
  public void loadFile(File file) {
    JSONObject jsonObject = jsonParser.loadFile(file);
  }

  @Override
  public void movePiece(Location start, Location end) {
    view.updateDisplay(model.movePiece(start, end));
    if(model.checkGameState() != Board.GameState.RUNNING) {
      System.out.println(model.checkGameState()); //FIXME
    }

  }

  public List<Location> getLegalMoves(Location location){
    return model.getLegalMoves(location);
  }
}
