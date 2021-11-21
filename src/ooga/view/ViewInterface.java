package ooga.view;

import javafx.scene.paint.Color;
import ooga.Turn;
import ooga.controller.BoardBuilder;

import java.util.List;

public interface ViewInterface {
    /**
     * Updates position of pieces on the board
     */
    void updateDisplay(Turn turn);

    void initializeDisplay(List<BoardBuilder.PieceViewBuilder> pieceViewList);

    void changeBoardColor(Color color1, Color color2);

    void changePieceStyle(String style);

    void resetBoard();

    void showError(String message);
}
