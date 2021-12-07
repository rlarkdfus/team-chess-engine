package ooga.view.ui.settingsUI;

import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import ooga.controller.ControllerInterface;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

public class GameConfigurationUI extends GridPane implements UIInterface {

    public static final String VARIATION_LABEL = "variation";
    public static final String GAME_VARIATION = "game_variation";
    public static final String UPLOAD_CONFIGURATION = "upload_configuration";
    public static final String DOWNLOAD_GAME = "download_game";
    public static final String NEW_WINDOW = "new_window";
    public static final String RESET_WINDOW = "reset_window";
    private final Map<String, EventHandler<ActionEvent>> buttonBehavior = Map.of(
            UPLOAD_CONFIGURATION, e -> uploadConfiguration(),
            DOWNLOAD_GAME, e -> downloadGame(),
            NEW_WINDOW, e -> createNewWindow(),
            RESET_WINDOW, e -> reset()
    );

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
        this.add(viewUtility.makeLabel(VARIATION_LABEL), 0, 0);
        this.add(viewUtility.makeMenu(GAME_VARIATION, variations, this::chooseVariation), 1, 0);
        this.add(viewUtility.makeButton(UPLOAD_CONFIGURATION, buttonBehavior.get(UPLOAD_CONFIGURATION)), 1, 1);
        this.add(viewUtility.makeButton(DOWNLOAD_GAME, buttonBehavior.get(DOWNLOAD_GAME)), 2, 1);
        this.add(viewUtility.makeButton(NEW_WINDOW, buttonBehavior.get(NEW_WINDOW)), 2, 2);
        this.add(viewUtility.makeButton(RESET_WINDOW, buttonBehavior.get(RESET_WINDOW)), 1, 2);
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
