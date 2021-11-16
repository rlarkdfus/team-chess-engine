package ooga.view.ui.settingsUI;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ooga.controller.Controller;
import ooga.view.ViewController;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

import java.sql.Time;
import java.util.List;

public class SettingsUI extends GridPane implements UIInterface {

    private Controller controller;
    private ViewController viewController;
    private GameConfigurationUI gameConfigurationUI;
    private TimeConfigurationUI timeConfigurationUI;
    private BoardStyleUI boardStyleUI;

    public SettingsUI(Controller controller, ViewController viewController) {
        this.controller = controller;
        this.viewController = viewController;
        //this.getStyleClass().add("SettingsUI");
        gameConfigurationUI = new GameConfigurationUI(controller);
        timeConfigurationUI = new TimeConfigurationUI(viewController);
        boardStyleUI = new BoardStyleUI(viewController);
        createUI();
    }

    @Override
    public void createUI() {
        this.add(gameConfigurationUI, 0, 0, 3, 2);
        this.add(timeConfigurationUI, 0, 2, 3, 3);
        this.add(boardStyleUI, 0, 5, 3, 3);
        this.add(ViewUtility.makeButton("new_game", e -> viewController.handleNewGame()), 0, 8);
    }
}
