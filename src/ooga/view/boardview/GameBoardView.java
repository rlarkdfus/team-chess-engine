package ooga.view.boardview;

import java.util.List;
import ooga.Location;
import ooga.controller.Config.PieceViewBuilder;
import ooga.controller.ControllerInterface;
import ooga.controller.GameControllerInterface;

/**
 * Purpose: Handles the complexities of the UI needed to represent the board in a game of chess
 * Assumptions: The controller will correctly provide the class with the information needed for its
 * functionality.
 * Dependencies: PieceViewBuilder, GameControllerInterface, Location, GameControllerInterface
 * @author Gordon Kim
 */
public class GameBoardView extends BoardView {

    private GameControllerInterface controller;

    /**
     * Create a new GameBoardView
     * @param controller Controller needed to handle game board interactions
     * @param pieceViews List of view representations of the pieces
     * @param row Row number
     * @param col Column number
     */
    public GameBoardView(GameControllerInterface controller, List<PieceViewBuilder> pieceViews, int row, int col) {
        super(pieceViews, row, col);
        this.controller = controller;
    }

    /**
     * Handle the work needed when a click is registered by the board. This helps the user by showing
     * possible moves and allowing them to move their pieces.
     * @param clickLocation Location where the click is registered.
     */
    @Override
    protected void clickBoard(Location clickLocation) {
        Location startLocation = getSelectedLocation();
        //user doesn't have piece selected and clicks on new piece
        //And add logic and is the same team
        if (startLocation == null) {
            clickWithoutHavingPiece(controller, clickLocation);
        } else { //user selects new location with piece
            if (isLegalMove(clickLocation, controller.getLegalMoves(startLocation))) { //user clicks new location
                movePiece(controller, clickLocation, startLocation);
            } else if (controller.canMovePiece(clickLocation)) {
                unselectPiece();
                selectPiece(clickLocation);
                showLegalMoves(controller.getLegalMoves(clickLocation));
            } else {
                unselectPiece();
            }
        }
    }
}
