package ooga.view.ui.settingsUI;

import javafx.scene.layout.GridPane;
import ooga.controller.Controller;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;
import java.util.List;

public class GameConfigurationUI extends GridPane implements UIInterface {

    private Controller controller;
    private ViewUtility viewUtility;

    public GameConfigurationUI(Controller controller) {
        this.controller = controller;
        this.viewUtility = new ViewUtility();
        this.getStyleClass().add("SettingsUI");
        createUI();
    }

    @Override
    public void createUI() {
        this.add(viewUtility.makeLabel("variation"), 0, 0);
        this.add(viewUtility.makeComboBox("game_variation", List.of("chess", "moreChess"), System.out::println), 1, 0);
        this.add(viewUtility.makeButton("upload_configuration", e -> {controller.uploadConfiguration(viewUtility.selectJSONFile());}), 1, 1);
    }
}
