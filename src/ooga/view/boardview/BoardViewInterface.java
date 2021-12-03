package ooga.view.boardview;

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
