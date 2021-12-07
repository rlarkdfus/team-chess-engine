package ooga.view.boardview;

import java.util.List;
import ooga.Location;
import ooga.controller.Config.PieceViewBuilder;
import ooga.controller.EditorControllerInterface;

/**
 * Purpose: EditorBoardView handles the complexity of creating a UI for the board editor
 * Assumptions: Controller will be functioning correctly to provide the board view with correct
 * values.
 * Dependencies: EditorControllerInterface, PieceViewBuilder, Location
 * @author Richard Deng, Gordon Kim
 */
public class EditorBoardView extends BoardView {

    private EditorControllerInterface controller;

    /**
     * Create a new EditorBoardView
     * @param controller Controller to handle the model view interactions
     * @param pieceViews View representations of the pieces
     * @param row Row number
     * @param col Column number
     */
    public EditorBoardView(EditorControllerInterface controller, List<PieceViewBuilder> pieceViews, int row, int col) {
        super(pieceViews, row, col);
        this.controller = controller;
    }

    /**
     * Handles the work needed to be done when a square on the editor is clicked.
     * @param clickLocation Location of the click.
     */
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
