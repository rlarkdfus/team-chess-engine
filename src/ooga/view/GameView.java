package ooga.view;

import java.util.List;
import javafx.scene.layout.GridPane;
import ooga.Location;
import ooga.controller.Config.PieceViewBuilder;
import ooga.controller.GameControllerInterface;
import ooga.view.boardview.GameBoardView;
import ooga.view.ui.gameInfoUI.GameInfoUI;
import ooga.view.ui.gameSettingsUI.GameSettingsUI;
import ooga.view.ui.settingsUI.SettingsUI;
import ooga.view.ui.timeConfigurationUI.TimeConfigurationUI;

public class GameView extends View {

  private SettingsUI settingsUI; // right
  private GameInfoUI gameInfoUI; // left
  private GameSettingsUI gameSettingsInfoUI; // top
  private TimeConfigurationUI timeConfigurationUI;
  GameControllerInterface controller;

  public GameView(GameControllerInterface controller) {
    this.controller = controller;
  }

  @Override
  protected void createStaticUIs() {
    this.timeConfigurationUI = new TimeConfigurationUI(controller);
  }

  @Override
  protected void createResettableUIs() {
    this.settingsUI = new SettingsUI(controller, viewController);
    this.gameSettingsInfoUI = new GameSettingsUI(controller, viewController);
    this.gameInfoUI = new GameInfoUI();
  }

  @Override
  protected void addUIs(GridPane root) {
    root.add(settingsUI, 2, 1);
    root.add(gameInfoUI, 0, 0, 3, 1);
    root.add(boardView, 1, 1, 1, 2);
    root.add(gameSettingsInfoUI, 0, 1, 1, 2);
    root.add(timeConfigurationUI, 2, 2, 1, 1);
  }

  @Override
  public void resetDisplay(List<PieceViewBuilder> pieceViewList, Location bounds) {
    this.boardView = new GameBoardView(controller, pieceViewList, bounds.getRow(), bounds.getCol());
    super.resetDisplay(pieceViewList, bounds);
  }
}

