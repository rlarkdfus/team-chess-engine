package ooga.view.boardview;

import ooga.Location;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.controller.Controller;
import ooga.controller.Config.PieceViewBuilder;

import java.io.FileNotFoundException;
import java.util.List;

public class EditorBoardView extends BoardView {

    private Controller controller;

    public EditorBoardView(Controller controller, List<PieceViewBuilder> pieceViews, int row, int col) {
        super(pieceViews, row, col);
        this.controller = controller;
    }

    @Override
    protected void clickBoard(Location clickLocation) throws FileNotFoundException, InvalidPieceConfigException {
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
