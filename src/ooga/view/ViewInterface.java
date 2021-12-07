package ooga.view;

import java.util.List;
import javafx.scene.paint.Color;
import ooga.Location;
import ooga.controller.Config.PieceViewBuilder;
import ooga.view.util.ViewUtility;

public interface ViewInterface {
    /**
     * Updates position of pieces on the board
     */
    void updateDisplay(List<PieceViewBuilder> pieceViewList);

    void initializeDisplay(List<PieceViewBuilder> pieceViewList, Location bounds);

    void changeBoardColor(Color color1, Color color2);

    void changePieceStyle(String style);

    static void showError(String message) {
        ViewUtility.showError(message);
    }

    void changeTheme(String theme);

    void changeLanguage(String language);
}
