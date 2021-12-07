package ooga.view.ui.settingsUI;

import javafx.scene.layout.GridPane;
import ooga.controller.ControllerInterface;
import ooga.view.ViewController;
import ooga.view.ui.UIInterface;

public class SettingsUI extends GridPane implements UIInterface {

  private final GameConfigurationUI gameConfigurationUI;
  private final BoardStyleUI boardStyleUI;

  /**
   * creates the SettingsUI GridPane
   *
   * @param controller     the controller responsible for actions in the SettingsUI that involve the model
   * @param viewController the controller responsible for actions in the SettingsUI that only involve the view
   */
  public SettingsUI(ControllerInterface controller, ViewController viewController) {
    gameConfigurationUI = new GameConfigurationUI(controller);
    boardStyleUI = new BoardStyleUI(viewController);
    createUI();
  }

  /**
   * adds the gameConfigurationUI and the boardStyleUI to the SettingsUI
   */
  @Override
  public void createUI() {
    this.add(gameConfigurationUI, 0, 0, 3, 2);
    this.add(boardStyleUI, 0, 5, 3, 3);
  }
}
