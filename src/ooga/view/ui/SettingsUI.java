package ooga.view.ui;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ooga.view.util.ViewUtility;

import java.util.List;

public class SettingsUI extends GridPane implements UIInterface {

    private final Color LICHESS_COLOR1 = Color.web("#f3dab0");
    private final Color LICHESS_COLOR2 = Color.web("#bb885b");

    public SettingsUI() {
        this.getStyleClass().add("SettingsUI");
        createUI();
    }

    @Override
    public void createUI() {
        this.add(ViewUtility.makeLabel("variation"), 0, 0);
        this.add(ViewUtility.makeComboBox("game_variation", List.of("chess", "moreChess"), e -> System.out.println(e)), 1, 0);

        this.add(ViewUtility.makeButton("upload_configuration", e -> System.out.println("upload configuration")), 1, 1);

        //TimeConfigurationUI
        this.add(ViewUtility.makeLabel("time_control"), 0, 1);
        this.add(ViewUtility.makeLabel("minutes_label"), 0, 2);
        this.add(ViewUtility.makeSlider("minutes", 0, 15, 3, e -> System.out.println(e)), 1, 2);
        this.add(ViewUtility.makeLabel("increment_label"), 0, 3);
        this.add(ViewUtility.makeSlider("increment", 0, 10, 2, e -> System.out.println(e)), 1, 3);

        //BoardStyleUI
        this.add(ViewUtility.makeLabel("board_color"), 0, 4);
        this.add(ViewUtility.makeColorPicker("board_color_1", LICHESS_COLOR1, e -> changeColor1(e)), 1, 4);
        this.add(ViewUtility.makeColorPicker("board_color_2", LICHESS_COLOR2, e -> changeColor2(e)), 1, 5);
        this.add(ViewUtility.makeLabel("piece_style_label"), 0, 6);
        this.add(ViewUtility.makeComboBox("piece_style", List.of("lichess"), e -> System.out.println(e)), 1, 6);

        this.add(ViewUtility.makeButton("new_game", e -> System.out.println("new game")), 0, 10);
    }

    private void changeColor1(Color newColor) {
        System.out.println("changed 1 to " + newColor);
    }

    private void changeColor2(Color newColor) {
        System.out.println("changed 1 to " + newColor);
    }
}
