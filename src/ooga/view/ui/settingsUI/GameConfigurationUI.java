package ooga.view.ui.settingsUI;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import ooga.controller.ControllerInterface;
import ooga.view.ui.UIHelper;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

public class GameConfigurationUI extends GridPane implements UIInterface {
    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("ooga/view/ui/settingsUI/GameConfigurationUI");

    public final List<String> variations = ResourceBundle.getBundle("ooga/controller/resources/Variations").keySet().stream().toList();

    public static final String VARIATION_LABEL = "variation";
    public static final String GAME_VARIATION = "game_variation";
    public static final String UPLOAD_CONFIGURATION = "upload_configuration";
    public static final String DOWNLOAD_GAME = "download_game";
    public static final String NEW_WINDOW = "new_window";
    public static final String RESET_WINDOW = "reset_window";
    public final Map<String, Consumer<String>> MENU_BEHAVIOR = Map.of(
            GAME_VARIATION, e -> chooseVariation(e)
    );
    public final Map<String, EventHandler<ActionEvent>> BUTTON_BEHAVIOR = Map.of(
            UPLOAD_CONFIGURATION, e -> uploadConfiguration(),
            DOWNLOAD_GAME, e -> downloadGame(),
            NEW_WINDOW, e -> createNewWindow(),
            RESET_WINDOW, e -> reset()
    );

    private ControllerInterface controller;
    private ViewUtility viewUtility;

    public GameConfigurationUI(ControllerInterface controller) {
        this.controller = controller;
        this.viewUtility = new ViewUtility();
        this.getStyleClass().add("SettingsUI");
        createUI();
    }

    @Override
    public void createUI() {
        UIHelper.setResourceBundle(RESOURCE_BUNDLE);
        this.add(viewUtility.makeLabel(VARIATION_LABEL), UIHelper.getCol(VARIATION_LABEL), UIHelper.getRow(VARIATION_LABEL));
        this.add(viewUtility.makeMenu(GAME_VARIATION, variations, MENU_BEHAVIOR.get(GAME_VARIATION)), UIHelper.getCol(GAME_VARIATION), UIHelper.getRow(GAME_VARIATION));
        this.add(viewUtility.makeButton(UPLOAD_CONFIGURATION, BUTTON_BEHAVIOR.get(UPLOAD_CONFIGURATION)), UIHelper.getCol(UPLOAD_CONFIGURATION), UIHelper.getRow(UPLOAD_CONFIGURATION));
        this.add(viewUtility.makeButton(DOWNLOAD_GAME, BUTTON_BEHAVIOR.get(DOWNLOAD_GAME)), UIHelper.getCol(DOWNLOAD_GAME), UIHelper.getRow(DOWNLOAD_GAME));
        this.add(viewUtility.makeButton(NEW_WINDOW, BUTTON_BEHAVIOR.get(NEW_WINDOW)), UIHelper.getCol(NEW_WINDOW), UIHelper.getRow(NEW_WINDOW));
        this.add(viewUtility.makeButton(RESET_WINDOW, BUTTON_BEHAVIOR.get(RESET_WINDOW)), UIHelper.getCol(RESET_WINDOW), UIHelper.getRow(RESET_WINDOW));
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
