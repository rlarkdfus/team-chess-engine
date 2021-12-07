package ooga.view.ui.timeConfigurationUI;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import ooga.controller.GameController;
import ooga.controller.GameControllerInterface;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

public class TimeConfigurationUI extends GridPane implements UIInterface {

    private static final String TIME_CONTROL_LABEL = "time_control";
    private static final String MINUTES_LABEL = "minutes_label";
    private static final String MINUTES_SLIDER = "minutes_slider";
    private static final String MINUTES_SLIDER_VALUE = "minutes_slider_value";

    private static final String INCREMENT_LABEL = "increment_label";
    private static final String INCREMENT_SLIDER = "increment_slider";
    private static final String INCREMENT_SLIDER_VALUE = "increment_slider_value";


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
        this.add(viewUtility.makeLabel(TIME_CONTROL_LABEL), 0, 1);
        this.add(viewUtility.makeLabel(MINUTES_LABEL), 0, 2);
        this.add(viewUtility.makeSlider(MINUTES_SLIDER, 1, 9, 5, this::handleMinutesSliderAction), 1, 2);
        this.add(viewUtility.makeLabel(MINUTES_SLIDER_VALUE), 2, 2);
        this.add(viewUtility.makeLabel(INCREMENT_LABEL), 0, 3);
        this.add(viewUtility.makeSlider(INCREMENT_SLIDER, 0, 20, 10, this::handleIncrementSliderAction), 1, 3);
        this.add(viewUtility.makeLabel(INCREMENT_SLIDER_VALUE), 2, 3);
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
