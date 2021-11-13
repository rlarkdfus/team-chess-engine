package ooga.view.ui.settingsUI;

import javafx.scene.layout.GridPane;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

import java.util.List;

public class GameConfigurationUI extends GridPane implements UIInterface {

    public GameConfigurationUI() {
        this.getStyleClass().add("SettingsUI");
        createUI();
    }

    @Override
    public void createUI() {
        this.add(ViewUtility.makeLabel("variation"), 0, 0);
        this.add(ViewUtility.makeComboBox("game_variation", List.of("chess", "moreChess"), e -> System.out.println(e)), 1, 0);
        this.add(ViewUtility.makeButton("upload_configuration", e -> System.out.println("upload configuration")), 1, 1);
    }
}
