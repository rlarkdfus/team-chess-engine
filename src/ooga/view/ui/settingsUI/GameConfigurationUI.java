package ooga.view.ui.settingsUI;

import javafx.scene.layout.GridPane;
import ooga.controller.Controller;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

import java.io.File;
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
        this.add(ViewUtility.makeComboBox("game_variation", List.of("chess", "moreChess"), e -> System.out.println(e)), 1, 0);
        this.add(ViewUtility.makeButton("upload_configuration", e -> {
            System.out.println("button pressed");
            try {
                controller.loadFile(new File("data/chess/defaultChess.json"));
                controller.initializeBoard();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }), 1, 1);
    }
}
