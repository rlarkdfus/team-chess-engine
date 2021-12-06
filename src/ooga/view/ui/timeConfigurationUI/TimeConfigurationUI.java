package ooga.view.ui.timeConfigurationUI;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import ooga.controller.GameControllerInterface;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

public class TimeConfigurationUI extends GridPane implements UIInterface {

    private GameControllerInterface controller;
    private ViewUtility viewUtility;

    public TimeConfigurationUI(GameControllerInterface controller) {
        this.controller = controller;
        viewUtility = new ViewUtility();
        this.getStyleClass().add("SettingsUI");
        createUI();
    }

    @Override
    public void createUI() {
        this.add(viewUtility.makeLabel("time_control"), 0, 1);
        this.add(viewUtility.makeLabel("minutes_label"), 0, 2);
        this.add(viewUtility.makeSlider("minutes_slider", 1, 9, 5, this::handleMinutesSliderAction), 1, 2);
        this.add(viewUtility.makeLabel("minutes_slider_value"), 2, 2);
        this.add(viewUtility.makeLabel("increment_label"), 0, 3);
        this.add(viewUtility.makeSlider("increment_slider", 0, 20, 10, this::handleIncrementSliderAction), 1, 3);
        this.add(viewUtility.makeLabel("increment_slider_value"), 2, 3);
        this.add(viewUtility.makeButton("new_game", e -> controller.reset()), 0, 4);
    }

    private void handleMinutesSliderAction(Double minutes) {
        int initialTime = (int)(Math.round(minutes));
        controller.setInitialTime(initialTime);
        ((Label) lookup("#minutes_slider_value")).setText(String.valueOf(initialTime));
    }

    private void handleIncrementSliderAction(Double seconds) {
        int increment = (int)(Math.round(seconds));
        controller.setIncrement(increment);
        ((Label) lookup("#increment_slider_value")).setText(String.valueOf(increment));
    }
 }
