package ooga.view.ui.settingsUI;

import javafx.scene.layout.GridPane;
import ooga.controller.Controller;
import ooga.view.ViewController;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

public class TimeConfigurationUI extends GridPane implements UIInterface {

    private Controller controller;
    private ViewController viewController;
    private ViewUtility viewUtility;

    public TimeConfigurationUI(Controller controller, ViewController viewController) {
        this.controller = controller;
        this.viewController = viewController;
        viewUtility = new ViewUtility();
        this.getStyleClass().add("SettingsUI");
        createUI();
    }

    public void createUI() {
        this.add(viewUtility.makeLabel("time_control"), 0, 1);
        this.add(viewUtility.makeLabel("minutes_label"), 0, 2);
        this.add(viewUtility.makeSlider("minutes", 0, 20, 3, e -> controller.setInitialTime(e)), 1, 2);
        this.add(viewUtility.makeLabel("increment_label"), 0, 3);
        this.add(viewUtility.makeSlider("increment", 0, 20, 2, e -> controller.setIncrement(e)), 1, 3);
    }
}
