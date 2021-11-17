package ooga.controller;

import java.io.File;
import java.util.List;
import ooga.Location;
import ooga.model.Board;
import ooga.model.Engine;
import ooga.model.PieceInterface;
import ooga.view.View;
import ooga.view.ViewInterface;
import org.json.JSONObject;

public class Controller implements ControllerInterface {

  private Engine model;
  private ViewInterface view;
  private BoardBuilder boardBuilder;
  private JsonParser jsonParser;

  public Controller(){
    boardBuilder = new BoardBuilder();
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
    view.initializeDisplay();
  }

  @Override
  public void loadFile(File file) throws Exception {
    JSONObject jsonObject = jsonParser.loadFile(file);
    boardBuilder.build(jsonObject);
    updateView();
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

  @Override
  public List<PieceInterface> getInitialPieces() {
    return boardBuilder.getInitialPieces();
  }

  public void initializeBoard() {
    model.initializePlayers(boardBuilder.getInitialPlayers());
  }

}
