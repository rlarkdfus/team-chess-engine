package ooga.view.boardview;

import java.util.List;
import ooga.Location;
import ooga.controller.Config.PieceViewBuilder;
import ooga.controller.EditorControllerInterface;
import ooga.view.PieceView;

public class PieceMenuView extends BoardView {

  private EditorControllerInterface controller;

  public PieceMenuView(EditorControllerInterface controller, List<PieceViewBuilder> pieceViews, int row, int col) {
    super(pieceViews, row, col);
    this.controller = controller;
  }

  @Override
  protected void clickBoard(Location clickLocation) {
    PieceView clickedPiece = null;
    for (PieceView pieceView : pieceList) {
      if (pieceView.getLocation().equals(clickLocation)) {
        clickedPiece = pieceView;
        break;
      }
    }
    if (controller.selectMenuPiece(clickedPiece.getTeam(), clickedPiece.getName())) {
      unselectPiece();
      selectPiece(clickLocation);
    } else {
      unselectPiece();
    }
  }
}
