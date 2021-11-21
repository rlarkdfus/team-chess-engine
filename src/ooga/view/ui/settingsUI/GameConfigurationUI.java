package ooga.view.ui.settingsUI;

import javafx.scene.layout.GridPane;
import ooga.controller.Controller;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class GameConfigurationUI extends GridPane implements UIInterface {

    private Controller controller;

    public GameConfigurationUI(Controller controller) {
        this.controller = controller;
        this.getStyleClass().add("SettingsUI");
        createUI();
    }

    @Override
    public void createUI() {
        this.add(ViewUtility.makeLabel("variation"), 0, 0);
        this.add(ViewUtility.makeComboBox("game_variation", List.of("chess", "moreChess"), System.out::println), 1, 0);
        this.add(ViewUtility.makeButton("upload_configuration", e -> {
            try {
                controller.uploadConfiguration(ViewUtility.selectJSONFile());
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }), 1, 1);
    }
}
