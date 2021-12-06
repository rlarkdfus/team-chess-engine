package ooga.controller;


import java.io.File;
import java.util.List;
import ooga.Location;
import ooga.controller.Config.Builder;
import ooga.controller.Config.PieceViewBuilder;
import ooga.model.EditorBoard;
import ooga.model.Engine;
import ooga.view.EditorView;
import ooga.view.ViewInterface;

public class EditorController extends Controller implements EditorControllerInterface {

  public static final File DEFAULT_CHESS_CONFIGURATION = new File("data/chess/defaultEditor.json");
  private String selectedTeam;
  private String selectedName;

  @Override
  protected File getDefaultConfiguration() {
    return DEFAULT_CHESS_CONFIGURATION;
  }

  @Override
  protected Engine initializeModel(Builder boardBuilder) {
    return new EditorBoard(boardBuilder.getInitialPlayers(),boardBuilder.getBoardSize());
  }

  @Override
  protected ViewInterface initializeView(List<PieceViewBuilder> pieces, Location bounds) {
    ViewInterface view = new EditorView(this);
    view.initializeDisplay(pieces, bounds);
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
    getModel().addPiece(selectedTeam, selectedName, location);
    updateView();
  }
}