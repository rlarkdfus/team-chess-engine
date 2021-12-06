package ooga.view.ui.settingsUI;

import java.util.List;
import javafx.scene.layout.GridPane;
import ooga.controller.ControllerInterface;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

public class GameConfigurationUI extends GridPane implements UIInterface {

    private ControllerInterface controller;
    private ViewUtility viewUtility;
    private final List<String> variations = List.of("Game", "Editor");

    public GameConfigurationUI(ControllerInterface controller) {
        this.controller = controller;
        this.viewUtility = new ViewUtility();
        this.getStyleClass().add("SettingsUI");
        createUI();
    }

    @Override
    public void createUI() {
        this.add(viewUtility.makeLabel("variation"), 0, 0);
        this.add(viewUtility.makeMenu("game_variation", variations, System.out::println), 1, 0);
        this.add(viewUtility.makeButton("upload_configuration", e -> {controller.uploadConfiguration(viewUtility.selectJSONFile());}), 1, 1);
    }
}
