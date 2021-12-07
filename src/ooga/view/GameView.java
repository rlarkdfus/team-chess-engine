package ooga.view;

import java.util.Map;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.GridPane;
import ooga.Location;
import ooga.controller.Config.PieceViewBuilder;
import ooga.controller.GameControllerInterface;
import ooga.model.GameState;
import ooga.view.boardview.GameBoardView;
import ooga.view.ui.playerStatsUI.PlayerStatsUI;
import ooga.view.ui.settingsUI.SettingsUI;
import ooga.view.ui.timeConfigurationUI.TimeConfigurationUI;

import java.util.List;

public class GameView extends View {

  private SettingsUI settingsUI; // right
  private PlayerStatsUI playerStatsUI; // left
  private TimeConfigurationUI timeConfigurationUI;
  GameControllerInterface controller;

  private final int WHITE_SCORE_INDEX = 0;
  private final int BLACK_SCORE_INDEX = 1;
  private final int DEFAULT_INITIAL_BLACK_SCORE = 39;
  private final int DEFAULT_INITIAL_WHITE_SCORE = 39;

  public GameView(GameControllerInterface controller) {
    super();
    this.controller = controller;
  }

  @Override
  public void initializeDisplay(List<PieceViewBuilder> pieceViewList, Location bounds) {
    super.initializeDisplay(pieceViewList, bounds);
    updatePlayerStatsUI(List.of(DEFAULT_INITIAL_WHITE_SCORE, DEFAULT_INITIAL_BLACK_SCORE));
//    initializePlayers(controller.getUsernames(), controller.getWins());

  }


  private void initializePlayers(Map<Enum, String> names, Map<Enum, Integer> wins) {
    playerStatsUI.initializePlayerNames(names.get(GameState.WHITE), names.get(GameState.BLACK));
    playerStatsUI.initializePlayerWins(wins.get(GameState.WHITE), wins.get(GameState.BLACK));
  }

  private void updatePlayerStatsUI(List<Integer> scores) {
    playerStatsUI.updateUI(scores.get(WHITE_SCORE_INDEX), scores.get(BLACK_SCORE_INDEX));
  }

  @Override
  protected void createStaticUIs() {
    this.timeConfigurationUI = new TimeConfigurationUI(controller);
  }

  @Override
  protected void createResettableUIs() {
    this.settingsUI = new SettingsUI(controller, viewController);
    this.playerStatsUI = new PlayerStatsUI(controller);
  }

  @Override
  protected void addUIs(GridPane root) {
    root.add(settingsUI, 2, 1);
    root.add(boardView, 1, 1, 1, 2);
    root.add(playerStatsUI, 0, 1, 1, 2);
    root.add(timeConfigurationUI, 2, 2, 1, 1);
  }

  @Override
  public void updateDisplay(List<PieceViewBuilder> pieceViewList) {
    super.updateDisplay(pieceViewList);
    updatePlayerStatsUI(controller.getUpdatedScores());
    initializePlayers(controller.getUsernames(), controller.getWins());
  }

  @Override
  public void resetDisplay(List<PieceViewBuilder> pieceViewList, Location bounds) {
    this.boardView = new GameBoardView(controller, pieceViewList, bounds.getRow(), bounds.getCol());
    super.resetDisplay(pieceViewList, bounds);
  }
}

