package ooga.view;

import javafx.scene.paint.Color;
import ooga.Turn;

public interface ViewInterface {
    /**
     * Updates position of pieces on the board
     */
    void updateDisplay(Turn turn);

    void initializeDisplay();

    void changeBoardColor(Color color1, Color color2);

    void changePieceStyle(String style);
}
