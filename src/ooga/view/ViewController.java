package ooga.view;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Map;

/**
 * Handles changes in the view that do not involve the model
 */
public class ViewController {

    public static final Map<String, List<Color>> themeMap = Map.of(
            "light", List.of(Color.web("#F2F4F6"), Color.web("#CFD6DE")),
            "dark", List.of(Color.web("#E1E1E1"), Color.web("#7E7E7E")),
            "duke", List.of(Color.web("#0577B1"), Color.web("#005587"))
    );

    private ViewInterface view;

    /**
     * creates a ViewController object
     *
     * @param view the ViewInterface representing the UI from which actions are handled
     */
    public ViewController(ViewInterface view) {
        this.view = view;
    }

    /**
     * calls on the view to change the board color
     *
     * @param color1 the new first board square color
     * @param color2 the new second board square color
     */
    public void handleChangeBoardColor(Color color1, Color color2) {
        view.changeBoardColor(color1, color2);
    }

    /**
     * calls on the view to change the piece style
     *
     * @param style the new piece style
     */
    public void handleChangePieceStyle(String style) {
        view.changePieceStyle(style);
    }

    /**
     * calls on the view to change the language
     *
     * @param language the new language
     */
    public void changeLanguage(String language) {
        view.changeLanguage(language);
    }

    /**
     * calls on the view to change the theme
     *
     * @param theme the new theme
     */
    public void changeTheme(String theme) {
        view.changeTheme(theme);
        List<Color> colors = themeMap.get(theme);
        handleChangeBoardColor(colors.get(0), colors.get(1));
    }

    public void handleKeyPress(KeyCode k){
        if (k==KeyCode.C){
            ((GameView)view).toggleCheatsVisible();
        }
    }
}
