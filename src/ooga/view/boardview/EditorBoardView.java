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
                clickWithoutHavingPiece(controller, clickLocation);
            } else {
                if (isLegalMove(clickLocation, controller.getLegalMoves(clickLocation))) { //user clicks new location
                    movePiece(controller, clickLocation, startLocation);
                }
            }
        }

    }
}
