package ooga.view;

import java.util.Map;
import javafx.scene.layout.GridPane;
import ooga.Location;
import ooga.controller.Config.PieceViewBuilder;
import ooga.controller.GameControllerInterface;
import ooga.model.GameState;
import ooga.view.boardview.BoardView;
import ooga.view.boardview.GameBoardView;
import ooga.view.ui.cheatUI.CheatUI;
import ooga.view.ui.playerStatsUI.PlayerStatsUI;
import ooga.view.ui.settingsUI.SettingsUI;
import ooga.view.ui.timeConfigurationUI.TimeConfigurationUI;

import java.util.List;

public class GameView extends View {

  private BoardView boardView;
  private SettingsUI settingsUI; // right
  private PlayerStatsUI playerStatsUI; // left
  private TimeConfigurationUI timeConfigurationUI;
  private CheatUI cheatUI;
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


  private void initializePlayers(Map<Enum, String> names, Map<Enum, Integer> wins) {
    playerStatsUI.initializePlayerNames(names.get(GameState.WHITE), names.get(GameState.BLACK));
    playerStatsUI.initializePlayerWins(wins.get(GameState.WHITE), wins.get(GameState.BLACK));
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
    this.cheatUI = new CheatUI(controller);
  }

  @Override
  protected GridPane addUIs() {
    GridPane root = new GridPane();
    root.add(settingsUI, 2, 1);
    root.add(boardView, 1, 1, 1, 2);
    root.add(playerStatsUI, 0, 1, 1, 2);
    root.add(timeConfigurationUI, 2, 2, 1, 1);
    root.add(cheatUI, 2, 3);
    cheatUI.setVisible(false);
    return root;
  }

  @Override
  public void updateDisplay(List<PieceViewBuilder> pieceViewList) {
    super.updateDisplay(pieceViewList);
    updatePlayerStatsUI(controller.getUpdatedScores());
    initializePlayers(controller.getUsernames(), controller.getWins());
  }

  public void toggleCheatsVisible(){
    cheatUI.setVisible(!cheatUI.isVisible());
  }
}

