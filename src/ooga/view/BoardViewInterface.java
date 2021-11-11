package ooga.view;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ooga.Location;

public interface BoardViewInterface {
    /**
     * Update pieces based on model
     */
    void updateBoardView();

    /**
     * Initializes the Board View
      */
    void initializeBoardView(int row, int col);

    /**
     * Checkered pattern board colors
     * @param color1
     * @param color2
     */
    void changeColors(Color color1, Color color2);

    /**
     * Highlights squares on the board that are legal
     */
    void showLegalMoves(Location location);
}
