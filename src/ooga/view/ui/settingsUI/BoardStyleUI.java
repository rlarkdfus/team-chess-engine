package ooga.view.ui.settingsUI;

import javafx.scene.control.ColorPicker;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import ooga.view.ViewController;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class BoardStyleUI extends GridPane implements UIInterface {

    private static final List<String> LANGUAGES = List.of("English", "Spanish", "French", "Italian", "German");
    private static final List<String> THEMES = List.of("light", "dark", "duke");
    
    public static final String COLOR_PICKER1 = "board_color_1";
    public static final String COLOR_PICKER2 = "board_color_2";
    private final Color DEFAULT_COLOR1 = Color.web("#f3dab0");
    private final Color DEFAULT_COLOR2 = Color.web("#bb885b");
    private final List<String> PIECE_STYLES = List.of(
            "california", "companion", "fantasy", "fresca",
            "horsey", "pirouetti", "spatial", "staunty"
    );
    private static final String BOARD_COLOR = "board_color";
    private static final String PIECE_STYLE_LABEL = "piece_variation_label";
    private static final String PIECE_MENU = "piece_variation";
    private static final String LANGUAGE_LABEL = "language_label";
    private static final String LANGUAGE_MENU = "language_variation";
    private static final String THEME_LABEL = "theme_label";
    private static final String THEME_MENU = "theme_variation";
    
    private final Map<String, Consumer<String>> MENU_BEHAVIOR = Map.of(
            PIECE_MENU, e -> changePieceStyle(e),
            LANGUAGE_MENU, e -> changeLanguage(e),
            THEME_MENU, e -> changeTheme(e)
    );
    
    private ViewController viewController;
    private ViewUtility viewUtility;
    private ColorPicker colorPicker1;
    private ColorPicker colorPicker2;
    private final Map<String, Consumer<Color>> COLORPICKER_BEHAVIOR = Map.of(
            COLOR_PICKER1, color -> viewController.handleChangeBoardColor(color, colorPicker2.getValue()),
            COLOR_PICKER2, color -> viewController.handleChangeBoardColor(colorPicker1.getValue(), color)
    );

    public BoardStyleUI(ViewController viewController) {
        this.viewController = viewController;
        this.viewUtility = new ViewUtility();
        this.getStyleClass().add("SettingsUI");
        makeColorPickers();
        createUI();
    }

    private void makeColorPickers() {
        colorPicker1 = viewUtility.makeColorPicker(COLOR_PICKER1, DEFAULT_COLOR1, COLORPICKER_BEHAVIOR.get(COLOR_PICKER1));
        colorPicker2 = viewUtility.makeColorPicker(COLOR_PICKER2, DEFAULT_COLOR2, COLORPICKER_BEHAVIOR.get(COLOR_PICKER2));
    }

    @Override
    public void createUI() {
        this.add(viewUtility.makeLabel(BOARD_COLOR), 0, 0);
        this.add(colorPicker1, 1, 0);
        this.add(colorPicker2, 1, 1);
        this.add(viewUtility.makeLabel(PIECE_STYLE_LABEL), 0, 2);
        this.add(viewUtility.makeMenu(PIECE_MENU, PIECE_STYLES, MENU_BEHAVIOR.get(PIECE_MENU)), 1, 2);

        this.add(viewUtility.makeLabel(LANGUAGE_LABEL), 0, 3);
        this.add(viewUtility.makeMenu(LANGUAGE_MENU, LANGUAGES, MENU_BEHAVIOR.get(LANGUAGE_MENU)), 1, 3);

        this.add(viewUtility.makeLabel(THEME_LABEL), 0, 4);
        this.add(viewUtility.makeMenu(THEME_MENU, THEMES, MENU_BEHAVIOR.get(THEME_MENU)), 1, 4);
    }

    private void changePieceStyle(String style) {
        viewController.handleChangePieceStyle(style);
    }

    private void changeLanguage(String language) {
        viewController.changeLanguage(language);
    }

    private void changeTheme(String theme) {
        viewController.changeTheme(theme);
    }
}
