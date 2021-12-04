package ooga.view.boardview;

import ooga.Location;
import ooga.controller.Controller;
import ooga.controller.Config.PieceViewBuilder;
import java.util.List;

public class GameBoardView extends BoardView {

    private Controller controller;

    public GameBoardView(Controller controller, List<PieceViewBuilder> pieceViews, int row, int col) {
        super(pieceViews, row, col);
        this.controller = controller;
    }

    @Override
    protected void clickBoard(Location clickLocation) {
        Location startLocation = getSelectedLocation();
        //user doesn't have piece selected and clicks on new piece
        //And add logic and is the same team
        if (startLocation == null) {
            if (controller.canMovePiece(clickLocation)) {
                selectPiece(clickLocation);
                showLegalMoves(clickLocation);
            } else {
                unselectPiece();
            }
        } else { //user selects new location with piece
            if (isLegalMove(clickLocation)) { //user clicks new location
                controller.movePiece(startLocation, clickLocation);
                unselectPiece();
            } else if (controller.canMovePiece(clickLocation)) {
                unselectPiece();
                selectPiece(clickLocation);
                showLegalMoves(clickLocation);
            }
            // if user clicks an illegal move
            unselectPiece();
        }
    }

    private void showLegalMoves(Location location) {
        List<Location> legalMoves = controller.getLegalMoves(location);
        for (Location squareLoc : legalMoves) {
            highlightBoardSquare(squareLoc);
        }
    }

    private boolean isLegalMove(Location clickLocation) {
        for (Location legalSquare : controller.getLegalMoves(getSelectedLocation())) {
            if (clickLocation.equals(legalSquare)) {
                return true;
            }
        }
        return false;
    }
}
