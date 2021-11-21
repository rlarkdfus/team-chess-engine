package ooga.controller;

import java.io.File;
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
  Builder boardBuilder;
  public Controller(){
    boardBuilder = new BoardBuilder(DEFAULT_CHESS_CONFIGURATION);
    this.view = new View(this);
    buildGame(boardBuilder);
  }


  @Override
  public boolean canMovePiece(Location location) {
    return model.canMovePiece(location);
  }

//  @Override
//  public void updateView() {
//    view.initializeDisplay();
//  }

  @Override
  public void uploadConfiguration(File file) throws Exception {
    boardBuilder.build(file);
    buildGame(boardBuilder);
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

//  @Override
//  public List<PieceInterface> getInitialPieces() {
//    return boardBuilder.getInitialPieces();
//  }

  private void buildGame(Builder boardBuilder) {
    this.model = new Board(boardBuilder.getInitialPlayers());
    this.view.initializeDisplay(boardBuilder.getInitialPieceViews());
  }
}
