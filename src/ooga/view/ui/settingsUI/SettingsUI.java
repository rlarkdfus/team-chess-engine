package ooga.view.ui.settingsUI;

import javafx.scene.layout.GridPane;
import ooga.controller.Controller;
import ooga.view.ViewController;
import ooga.view.ui.UIInterface;
import ooga.view.ui.timeConfigurationUI.TimeConfigurationUI;
import ooga.view.util.ViewUtility;

public class SettingsUI extends GridPane implements UIInterface {

    private Controller controller;
    private ViewController viewController;
    private GameConfigurationUI gameConfigurationUI;
    private BoardStyleUI boardStyleUI;
    private ViewUtility viewUtility;

    public SettingsUI(Controller controller, ViewController viewController) {
        this.controller = controller;
        this.viewController = viewController;
        gameConfigurationUI = new GameConfigurationUI(controller);
        boardStyleUI = new BoardStyleUI(viewController);
        this.viewUtility = new ViewUtility();
        createUI();
    }

    @Override
    public void createUI() {
        this.add(gameConfigurationUI, 0, 0, 3, 2);
        this.add(boardStyleUI, 0, 5, 3, 3);
    }
}
