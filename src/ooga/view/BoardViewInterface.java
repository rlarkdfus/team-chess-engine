package ooga.view;

import java.util.List;
import javafx.scene.paint.Color;
import ooga.Location;
import ooga.Turn;
import ooga.controller.PieceViewBuilder;

public interface BoardViewInterface {
    /**
     * Update pieces based on model
     */
    void updateBoardView(List<PieceViewBuilder> pieceViews);

//    /**
//     * Initializes the Board View
//      */
//    void initializeBoardView(int row, int col);

    void initializeBoardView(List<PieceViewBuilder> pieceViews, int row, int col);

    /**
     * Checkered pattern board colors
     * @param color1
     * @param color2
     */
    void changeColors(Color color1, Color color2);

    /**
     * Checkered pattern board colors
     * @param style
     */
    void changePieceStyle(String style);

    void resetBoard();

    /**
     * Highlights squares on the board that are legal
     */
    void showLegalMoves(Location location);
}
