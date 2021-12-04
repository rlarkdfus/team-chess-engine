package ooga.view.boardview;

import ooga.Location;
import ooga.controller.Controller;
import ooga.controller.Config.PieceViewBuilder;

import java.util.List;

public class EditorBoardView extends BoardView {

    private Controller controller;

    public EditorBoardView(Controller controller, List<PieceViewBuilder> pieceViews, int row, int col) {
        super(pieceViews, row, col);
        this.controller = controller;
    }

    @Override
    protected void clickBoard(Location clickLocation) {
        Location startLocation = getSelectedLocation();

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


//        if (getSelectedLocation() == null) {
//            selectPiece(clickLocation);
//        }
//        else {
//            controller.movePiece(getSelectedLocation(), clickLocation);
//            unselectPiece();
//        }
    }
}
