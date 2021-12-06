package ooga.view.boardview;

import java.util.List;
import ooga.Location;
import ooga.controller.Config.PieceViewBuilder;
import ooga.controller.EditorControllerInterface;

public class EditorBoardView extends BoardView {

    private EditorControllerInterface controller;

    public EditorBoardView(EditorControllerInterface controller, List<PieceViewBuilder> pieceViews, int row, int col) {
        super(pieceViews, row, col);
        this.controller = controller;
    }

    @Override
    protected void clickBoard(Location clickLocation) {
        Location startLocation = getSelectedLocation();
        if (controller.hasMenuPiece()) {
            controller.addPiece(clickLocation);
        } else {
            if (startLocation == null) {
                if (controller.canMovePiece(clickLocation)) {
                    selectPiece(clickLocation);
                    showLegalMoves(controller.getLegalMoves(clickLocation));
                } else {
                    unselectPiece();
                }
            } else {
                if (isLegalMove(clickLocation, controller.getLegalMoves(clickLocation))) { //user clicks new location
                    controller.movePiece(startLocation, clickLocation);
                    unselectPiece();
                }
            }
        }

    }
}
