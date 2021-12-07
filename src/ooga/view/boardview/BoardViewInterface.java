package ooga.view.boardview;

import java.util.List;
import javafx.scene.paint.Color;
import ooga.controller.Config.PieceViewBuilder;

/**
 * Purpose: This interface represents an API of what the BoardView classes should ensure in their
 * interactions with other classes. The behaviors outlined below will be implemented in all
 * classes inheriting this interface and will accomplish their described function.
 */
public interface BoardViewInterface {
    /**
     * Update pieces based on the state of the program in the model
     */
    void updateBoardView(List<PieceViewBuilder> pieceViews);

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
}
