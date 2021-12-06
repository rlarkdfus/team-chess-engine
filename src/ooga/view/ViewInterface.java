package ooga.view;

import java.util.List;
import javafx.scene.paint.Color;
import ooga.Location;
import ooga.controller.Config.PieceViewBuilder;

public interface ViewInterface {
    /**
     * Updates position of pieces on the board
     */
    void updateDisplay(List<PieceViewBuilder> pieceViewList);

    void initializeDisplay(List<PieceViewBuilder> pieceViewList, Location bounds);

    void resetDisplay(List<PieceViewBuilder> pieceViewList, Location bounds);

    void changeBoardColor(Color color1, Color color2);

    void changePieceStyle(String style);

    void showError(String message);

    void showCheck(Location location);
}
