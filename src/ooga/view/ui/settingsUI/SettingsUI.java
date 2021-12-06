package ooga.view.ui.settingsUI;

import javafx.scene.layout.GridPane;
import ooga.controller.ControllerInterface;
import ooga.view.ViewController;
import ooga.view.ui.UIInterface;

public class SettingsUI extends GridPane implements UIInterface {

    private GameConfigurationUI gameConfigurationUI;
    private BoardStyleUI boardStyleUI;

    public SettingsUI(ControllerInterface controller, ViewController viewController) {
        gameConfigurationUI = new GameConfigurationUI(controller);
        boardStyleUI = new BoardStyleUI(viewController);
        createUI();
    }

    @Override
    public void createUI() {
        this.add(gameConfigurationUI, 0, 0, 3, 2);
        this.add(boardStyleUI, 0, 5, 3, 3);
    }
}
