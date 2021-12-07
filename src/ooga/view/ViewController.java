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

    public ViewController(ViewInterface view) {
        this.view = view;
    }

    public void handleChangeBoardColor(Color color1, Color color2) {
        view.changeBoardColor(color1, color2);
    }

    public void handleChangePieceStyle(String style) {
        view.changePieceStyle(style);
    }

    public void changeLanguage(String language) {
        view.changeLanguage(language);
    }

    public void changeTheme(String theme) {
        view.changeTheme(theme);
        List<Color> colors = themeMap.get(theme);
        handleChangeBoardColor(colors.get(0), colors.get(1));
    }

    public void handleKeyPress(KeyCode k){
        if (k==KeyCode.C){
            //todo unhide cheat menu
//            revealCheatMenu();
        }
    }
}
