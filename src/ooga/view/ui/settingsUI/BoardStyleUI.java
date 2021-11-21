package ooga.view.ui.settingsUI;

import javafx.scene.control.ColorPicker;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import ooga.view.ViewController;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

import java.util.List;

public class BoardStyleUI extends GridPane implements UIInterface {

    private final Color LICHESS_COLOR1 = Color.web("#f3dab0");
    private final Color LICHESS_COLOR2 = Color.web("#bb885b");
    private final List<String> PIECE_STYLES = List.of(
            "california", "companion", "fantasy", "fresca",
            "horsey", "pirouetti", "spatial", "staunty");

    private ViewController viewController;
    private ViewUtility viewUtility;
    private ColorPicker colorPicker1;
    private ColorPicker colorPicker2;

    public BoardStyleUI(ViewController viewController) {
        this.viewController = viewController;
        this.viewUtility = new ViewUtility();
        this.getStyleClass().add("SettingsUI");
        createUI();
    }

    @Override
    public void createUI() {
        colorPicker1 = viewUtility.makeColorPicker("board_color_1", LICHESS_COLOR1, color -> viewController.handleChangeBoardColor(color, colorPicker2.getValue()));
        colorPicker2 = viewUtility.makeColorPicker("board_color_2", LICHESS_COLOR2, color -> viewController.handleChangeBoardColor(colorPicker1.getValue(), color));

        this.add(viewUtility.makeLabel("board_color"), 0, 0);
        this.add(colorPicker1, 1, 0);
        this.add(colorPicker2, 1, 1);
        this.add(viewUtility.makeLabel("piece_style_label"), 0, 3);
        this.add(viewUtility.makeComboBox("piece_style", PIECE_STYLES, style -> viewController.handleChangePieceStyle(style)), 1, 3);
    }
}
