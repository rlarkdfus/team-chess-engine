package ooga.view.ui.settingsUI;

import javafx.scene.layout.GridPane;
import ooga.view.ViewController;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

public class TimeConfigurationUI extends GridPane implements UIInterface {

    private ViewController viewController;

    public TimeConfigurationUI(ViewController viewController) {
        this.viewController = viewController;
        this.getStyleClass().add("SettingsUI");
        createUI();
    }

    public void createUI() {
        //TimeConfigurationUI
        this.add(ViewUtility.makeLabel("time_control"), 0, 1);
        this.add(ViewUtility.makeLabel("minutes_label"), 0, 2);
        this.add(ViewUtility.makeSlider("minutes", 0, 15, 3, e -> viewController.handleChangeTimeIncrement(e)), 1, 2);
        this.add(ViewUtility.makeLabel("increment_label"), 0, 3);
        this.add(ViewUtility.makeSlider("increment", 0, 10, 2, e -> System.out.println(e)), 1, 3);
    }
}
