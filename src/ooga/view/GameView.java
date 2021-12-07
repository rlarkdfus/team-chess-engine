package ooga.view;

import javafx.scene.layout.GridPane;
import ooga.Location;
import ooga.controller.Config.PieceViewBuilder;
import ooga.controller.GameControllerInterface;
import ooga.view.boardview.BoardView;
import ooga.view.boardview.GameBoardView;
import ooga.view.ui.playerStatsUI.PlayerStatsUI;
import ooga.view.ui.settingsUI.SettingsUI;
import ooga.view.ui.timeConfigurationUI.TimeConfigurationUI;

import java.util.List;

public class GameView extends View {

  private BoardView boardView;
  private SettingsUI settingsUI; // right
  private PlayerStatsUI playerStatsUI; // left
  private TimeConfigurationUI timeConfigurationUI;
  private GameControllerInterface controller;

  private final int WHITE_SCORE_INDEX = 0;
  private final int BLACK_SCORE_INDEX = 1;

  public GameView(GameControllerInterface controller) {
    super();
    this.controller = controller;
  }

  @Override
  public void initializeDisplay(List<PieceViewBuilder> pieceViewList, List<Location> specialLocations, Location bounds) {
    super.initializeDisplay(pieceViewList, specialLocations, bounds);
    updatePlayerStatsUI(controller.getUpdatedScores());
  }

  private void initializePlayerNames(List<String> names) {
    playerStatsUI.initializePlayerNames(names.get(WHITE_SCORE_INDEX), names.get(BLACK_SCORE_INDEX));
  }

  private void updatePlayerStatsUI(List<Integer> scores) {
    playerStatsUI.updateUI(scores.get(WHITE_SCORE_INDEX), scores.get(BLACK_SCORE_INDEX));
  }

  protected BoardView initializeBoardView(List<PieceViewBuilder> pieceViewList, List<Location> specialLocations, Location bounds) {
    boardView = new GameBoardView(controller, pieceViewList, bounds.getRow(), bounds.getCol());
    boardView.markInitialSpecialLocation(specialLocations);
    return boardView;
  }

  @Override
  protected void initializeUI(ViewController viewController) {
    this.timeConfigurationUI = new TimeConfigurationUI(controller);
    this.settingsUI = new SettingsUI(controller, viewController);
    this.playerStatsUI = new PlayerStatsUI(controller);
  }

  @Override
  protected GridPane addUIs() {
    GridPane root = new GridPane();
    root.add(settingsUI, 2, 1);
    root.add(boardView, 1, 1, 1, 2);
    root.add(playerStatsUI, 0, 1, 1, 2);
    root.add(timeConfigurationUI, 2, 2, 1, 1);
    return root;
  }

  @Override
  public void updateDisplay(List<PieceViewBuilder> pieceViewList) {
    super.updateDisplay(pieceViewList);
    updatePlayerStatsUI(controller.getUpdatedScores());
  }
}

