package ooga.controller;

import ooga.Location;
import ooga.controller.Config.Builder;
import ooga.controller.Config.PieceViewBuilder;
import ooga.model.EditorBoard;
import ooga.model.Engine;
import ooga.view.EditorView;
import ooga.view.ViewInterface;

import java.io.File;
import java.util.List;

public class EditorController extends Controller {

  public static final File DEFAULT_CHESS_CONFIGURATION = new File("data/chess/defaultEditor.json");

  private List<PieceViewBuilder> pieces;

  @Override
  protected File getDefaultConfiguration() {
    return DEFAULT_CHESS_CONFIGURATION;
  }

  @Override
  protected Engine initializeModel(Builder boardBuilder) {
    return new EditorBoard(boardBuilder.getInitialPlayers());
  }

  @Override
  protected ViewInterface initializeView(List<PieceViewBuilder> pieces) {
    this.pieces = pieces;
    ViewInterface view = new EditorView(this);
    view.initializeDisplay(pieces);
    return view;
  }

  @Override
  public void setInitialTime(int minutes) {
  }

  @Override
  public void setIncrement(int seconds) {
  }
}