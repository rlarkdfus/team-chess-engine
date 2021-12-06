package ooga.view.boardview;

import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import ooga.Location;
import ooga.controller.Config.PieceViewBuilder;
import ooga.view.PieceView;
import java.util.ArrayList;
import java.util.List;

public abstract class BoardView extends Group implements BoardViewInterface {

  private final Color DEFAULT_COLOR_1 = Color.web("#f3dab0");
  private final Color DEFAULT_COLOR_2 = Color.web("#bb885b");
  private final String DEFAULT_PIECE_STYLE = "companion";

  private List<BoardSquare> background;
  protected List<PieceView> pieceList;
  private Location selectedLocation;

  private int row;
  private int col;

  public BoardView(List<PieceViewBuilder> pieceViews, int row, int col) {
    selectedLocation = null;
    pieceList = new ArrayList<>();
    this.row = row;
    this.col = row;
    initializeBoardView(pieceViews);
  }

  private void initializeBoardView(List<PieceViewBuilder> pieceViews) {
    renderBackground();
    renderInitialChessPieces(pieceViews, DEFAULT_PIECE_STYLE);
    this.setOnMouseClicked(e -> {
      clickBoard(findClickLocation(e));
      annotate(e);
    });
  }

  @Override
  public void updateBoardView(List<PieceViewBuilder> pieceViews) {
    clearBoard();
    for (PieceViewBuilder piece : pieceViews) {
      PieceView newPiece = new PieceView(piece.getTeam(), piece.getName(), DEFAULT_PIECE_STYLE, piece.getLocation());
      pieceList.add(newPiece);
      this.getChildren().add(newPiece);
    }
  }

  @Override
  public void changeColors(Color color1, Color color2) {
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        Color color = (i + j) % 2 == 0 ? color1 : color2;
        findBoardSquare(new Location(i, j)).setColor(color);
      }
    }
  }

  @Override
  public void changePieceStyle(String style) {
    for (PieceView pieceView : pieceList) {
      if (pieceView != null) {
        pieceView.changeStyle(style);
      }
    }
  }

  private void annotate(MouseEvent mouse) {
    if (mouse.getButton() == MouseButton.SECONDARY) {
      Location clickLocation = findClickLocation(mouse);
      findBoardSquare(clickLocation).annotate();
    }
  }

  protected abstract void clickBoard(Location clickLocation);

  protected void highlightBoardSquare(Location location) {
    findBoardSquare(location).highlight();
  }

  protected void selectPiece(Location location) {
    selectedLocation = location;
    findBoardSquare(location).select();
  }

  protected boolean isLegalMove(Location clickLocation, List<Location> legalMoves) {
    return clickLocation.inList(legalMoves);
  }

  protected void showLegalMoves(List<Location> legalMoves) {
    for (Location squareLoc : legalMoves) {
      highlightBoardSquare(squareLoc);
    }
  }

  protected void unselectPiece() {
    selectedLocation = null;
    for (BoardSquare boardSquare : background) {
      boardSquare.resetBoardSquare();
    }
  }

  private void renderBackground() {
    background = new ArrayList<>();
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        Location location = new Location(i, j);
        BoardSquare boardSquare = new BoardSquare(location, null);
        background.add(boardSquare);
        this.getChildren().add(boardSquare);
      }
    }
    changeColors(DEFAULT_COLOR_1, DEFAULT_COLOR_2);
  }

  private void renderInitialChessPieces(List<PieceViewBuilder> pieceViews, String style) {
    for (PieceViewBuilder piece : pieceViews) {
      PieceView newPiece = new PieceView(piece.getTeam(), piece.getName(), style, piece.getLocation());
      pieceList.add(newPiece);
      this.getChildren().add(newPiece);
    }
  }

  private void clearBoard() {
    this.getChildren().removeAll(pieceList);
    pieceList.clear();
  }

  private Location findClickLocation(MouseEvent mouse) {
    return new Location((int) mouse.getY() / 60, (int) mouse.getX() / 60);
  }

  private BoardSquare findBoardSquare(Location location) {
    return background.get(location.getRow() * col + location.getCol());
  }

  protected Location getSelectedLocation() {
    return selectedLocation;
  }

  public void showCheck(Location location) {
    findBoardSquare(location).check();
  }
}