package ooga.controller;


import java.io.File;
import java.util.List;
import ooga.Location;
import ooga.controller.Config.Builder;
import ooga.controller.Config.PieceViewBuilder;
import ooga.model.EditorBoard;
import ooga.model.EditorEngine;
import ooga.model.Engine;
import ooga.view.EditorView;
import ooga.view.ViewInterface;

public class EditorController extends Controller implements EditorControllerInterface {

  public static final File DEFAULT_CHESS_CONFIGURATION = new File("data/chess/defaultEditor.json");
  private String selectedTeam;
  private String selectedName;

  private EditorEngine model;

  @Override
  protected File getDefaultConfiguration() {
    return DEFAULT_CHESS_CONFIGURATION;
  }

  @Override
  protected Engine initializeModel(Builder boardBuilder) {
    model = new EditorBoard(boardBuilder.getInitialPlayers(),boardBuilder.getBoardSize());
    return model;
  }

  @Override
  protected ViewInterface initializeView(Builder boardBuilder) {
    ViewInterface view = new EditorView(this);
    view.initializeDisplay(boardBuilder.getInitialPieceViews(), boardBuilder.getPowerupLocations(), boardBuilder.getBoardSize());
    return view;
  }

  @Override
  public boolean selectMenuPiece(String team, String name) {
    boolean selectNewPiece = !hasMenuPiece() || !selectedTeam.equals(team) || !selectedName.equals(name);
    if (selectNewPiece) {
      selectedTeam = team;
      selectedName = name;
    } else {
      selectedTeam = null;
      selectedName = null;
    }
    return selectNewPiece;
  }

  @Override
  public boolean hasMenuPiece() {
    return selectedTeam != null && selectedName != null;
  }

  @Override
  public void addPiece(Location location) {
    model.addPiece(selectedTeam, selectedName, location);
    updateView();
  }
}