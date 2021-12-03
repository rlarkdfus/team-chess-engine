package ooga.view.boardview;

import ooga.Location;
import ooga.controller.Controller;
import ooga.controller.PieceViewBuilder;

import java.awt.event.MouseEvent;
import java.util.List;

public class EditorBoardView extends BoardView {

    Controller controller;

    public EditorBoardView(Controller controller, List<PieceViewBuilder> pieceViews, int row, int col) {
        super(pieceViews, row, col);
        this.controller = controller;
    }

    @Override
    protected void clickBoard(Location clickLocation) {
        if (getSelectedLocation() == null) {
            selectPiece(clickLocation);
        }
        else {
            unselectPiece();
        }
    }
}
