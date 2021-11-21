package ooga.view.ui.gameSettingsUI;

import javafx.scene.layout.GridPane;
import ooga.controller.Controller;
import ooga.view.ViewController;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

public class GameSettingsUI extends GridPane implements UIInterface {
    Controller controller;
    ViewController viewController;
    ViewUtility viewUtility;

    public GameSettingsUI(Controller controller, ViewController viewController) {
        this.controller = controller;
        this.viewController = viewController;
        this.viewUtility = new ViewUtility();
        this.getStyleClass().add("GameSettingsUI");
        createUI();
    }

    @Override
    public void createUI() {
        this.add(viewUtility.makeLabel("black_time_label"), 0, 0);
        this.add(viewUtility.makeText("black_timer_display"), 0, 1);
        this.add(viewUtility.makeLabel("move_history_label"), 0, 2);
        this.add(viewUtility.makeGridPane("move_history_gridpane"), 0, 3);
        this.add(viewUtility.makeButton("download_game", e -> controller.downloadGame(viewUtility.saveCSVPath())), 0, 4);
        this.add(viewUtility.makeLabel("white_time_label"), 0, 5);
        this.add(viewUtility.makeText("white_timer_display"), 0, 6);
    }
}
