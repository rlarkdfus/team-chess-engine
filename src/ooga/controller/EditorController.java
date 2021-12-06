package ooga.controller;

import java.io.File;
import java.util.List;
import ooga.controller.Config.Builder;
import ooga.controller.Config.PieceViewBuilder;
import ooga.model.EditorBoard;
import ooga.model.Engine;
import ooga.view.EditorView;
import ooga.view.ViewInterface;

public class EditorController extends Controller {

  public static final File DEFAULT_CHESS_CONFIGURATION = new File("data/chess/defaultEditor.json");

  @Override
  protected File getDefaultConfiguration() {
    return DEFAULT_CHESS_CONFIGURATION;
  }

  @Override
  protected Engine initializeModel(Builder boardBuilder) {
    return new EditorBoard(boardBuilder.getInitialPlayers(),boardBuilder.getBoardSize());
  }

  @Override
  protected ViewInterface initializeView(List<PieceViewBuilder> pieces) {
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