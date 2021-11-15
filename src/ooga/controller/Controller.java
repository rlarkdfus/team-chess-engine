package ooga.controller;

import java.io.File;
import java.util.List;
import ooga.Location;
import ooga.model.Board;
import ooga.model.Engine;
import ooga.model.PieceInterface;
import ooga.view.PieceView;
import ooga.view.View;
import ooga.view.ViewInterface;
import org.json.JSONObject;

public class Controller implements ControllerInterface {

  private Engine model;
  private ViewInterface view;
  private BoardBuilder boardBuilder;
  private JsonParser jsonParser;

  public Controller(){
    model = new Board();
    view = new View(this);

    boardBuilder = new BoardBuilder();
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
  public PieceInterface[][] loadFile(File file) throws Exception {
    JSONObject jsonObject = jsonParser.loadFile(file);
    return boardBuilder.build(jsonObject);
  }

  @Override
  public void movePiece(Location start, Location end) {
    view.updateDisplay(model.movePiece(start, end));
  }

  public List<Location> getLegalMoves(Location location){
    return model.getLegalMoves(location);
  }

  @Override
  public PieceView[][] sendInitialBoardView(String style) {
    return boardBuilder.getInitialBoardView(style);
  }

}
