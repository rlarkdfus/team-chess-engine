package ooga.View;

import javafx.scene.paint.Paint;

public interface BoardViewInterface {
    /**
     * Update pieces based on model
     */
    void updateBoardView();

    /**
     * Initializes the Board View
      */
    void initializeBoardView();

    /**
     * Checkered pattern board colors
     * @param color1
     * @param color2
     */
    void changeColors(Paint color1, Paint color2);

    /**
     * Highlights squares on the board that are legal
     */
    void showLegalMoves();
}
