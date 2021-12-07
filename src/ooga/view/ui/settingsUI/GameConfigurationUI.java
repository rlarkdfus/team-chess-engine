package ooga.view.ui.settingsUI;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javafx.scene.layout.GridPane;
import ooga.controller.ControllerInterface;
import ooga.controller.GameController;
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
        this.add(viewUtility.makeMenu("game_variation", variations, e -> chooseVariation(e)), 1, 0);
        this.add(viewUtility.makeButton("upload_configuration", e -> uploadConfiguration()), 1, 1);
        this.add(viewUtility.makeButton("download_game", e -> downloadGame()), 2, 1);
        this.add(viewUtility.makeButton("new_window", e -> createNewWindow()), 2, 2);
        this.add(viewUtility.makeButton("reset_window", e -> reset()), 1, 2);
    }

    private void chooseVariation(String controllerVariation) {
        controller.launchController(controllerVariation);

    }

    private void uploadConfiguration() {
        controller.uploadConfiguration(viewUtility.selectJSONFile());
    }

    private void downloadGame() {
        controller.downloadGame(viewUtility.saveJSONPath());
    }

    private void createNewWindow() {
        try {
            controller.getClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            ViewUtility.showError("InvalidGameVariation");
        }
    }

    private void reset() {
        controller.reset();
    }
}
