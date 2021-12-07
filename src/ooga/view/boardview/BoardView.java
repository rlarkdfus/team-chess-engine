package ooga.view.boardview;

import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import ooga.Location;
import ooga.controller.Config.PieceViewBuilder;
import ooga.view.PieceView;
import ooga.view.PowerupView;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: Abstract class representing a general board view implementation. This removes a great deal of the complexity
 * involved in creating a board view for each of the subclasses that extend this class. This abstraction allows child
 * classes to focus on their unique functionalities without each having to be burdened by the work handled by this class
 * in creating a board UI.
 * Assumptions:
 *
 * @author Gordon Kim, Richard Deng
 */
public abstract class BoardView extends Group implements BoardViewInterface {

  private static final Color DEFAULT_COLOR_1 = Color.web("#f3dab0");
  private static final Color DEFAULT_COLOR_2 = Color.web("#bb885b");
  private static String DEFAULT_PIECE_STYLE = "companion";
  public static final int SQUARE_WIDTH = 60;
  public static final int SQUARE_HEIGHT = 60;

  private List<BoardSquare> background;
  protected List<PieceView> pieceList;
  private List<Location> specialLocations;
  private Location selectedLocation;

  private final int row;
  private final int col;

  /**
   * Create a new BoardView object
   * @param pieceViews List of view representations of the pieces
   * @param row Row number
   * @param col Column number
   */
  public BoardView(List<PieceViewBuilder> pieceViews, int row, int col) {
    selectedLocation = null;
    pieceList = new ArrayList<>();
    specialLocations = new ArrayList<>();
    this.row = row;
    this.col = col;
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

  /**
   * Update the board view so that it reflects the state of the program in the model.
   * @param pieceViews List of view representations of the pieces.
   */
  @Override
  public void updateBoardView(List<PieceViewBuilder> pieceViews) {
    clearBoard();
    for (PieceViewBuilder piece : pieceViews) {
      PieceView newPiece = new PieceView(piece.getTeam(), piece.getName(), DEFAULT_PIECE_STYLE, piece.getLocation());
      pieceList.add(newPiece);
      this.getChildren().add(newPiece);
    }
  }

  /**
   * Change the colors of the squares on the board.
   * @param color1 Color of first type of squares
   * @param color2 Color of second type of squares
   */
  @Override
  public void changeColors(Color color1, Color color2) {
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        Color color = (i + j) % 2 == 0 ? color1 : color2;
        findBoardSquare(new Location(i, j)).setColor(color);
      }
    }
  }

  /**
   * Change the style of the piece icon styles
   * @param style Icon style
   */
  @Override
  public void changePieceStyle(String style) {
    for (PieceView pieceView : pieceList) {
      if (pieceView != null) {
        pieceView.changeStyle(style);
      }
    }
    DEFAULT_PIECE_STYLE = style;
  }

  private void annotate(MouseEvent mouse) {
    if (mouse.getButton() == MouseButton.SECONDARY) {
      Location clickLocation = findClickLocation(mouse);
      findBoardSquare(clickLocation).annotate();
    }
  }

  /**
   * Handles the work of what a click on the board should do. Method is abstract as each class
   * extending this one should have its own implementation so that a click performs the correct
   * action.
   * @param clickLocation Location
   */
  protected abstract void clickBoard(Location clickLocation);

  /**
   * Highlights squares on the board
   * @param location
   */
  protected void highlightBoardSquare(Location location) {
    findBoardSquare(location).highlight();
  }

  /**
   * Selects a piece on the board
   * @param location
   */
  protected void selectPiece(Location location) {
    selectedLocation = location;
    findBoardSquare(location).select();
  }

  /**
   * Returns true if legal move
   * @param clickLocation
   * @param legalMoves
   * @return
   */
  protected boolean isLegalMove(Location clickLocation, List<Location> legalMoves) {
    return clickLocation.inList(legalMoves);
  }

  /**
   * List containing locations of legal moves
   * @param legalMoves
   */
  protected void showLegalMoves(List<Location> legalMoves) {
    for (Location squareLoc : legalMoves) {
      highlightBoardSquare(squareLoc);
    }
  }

  /**
   * Unselect a piece
   */
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
        BoardSquare boardSquare = new BoardSquare(SQUARE_WIDTH, SQUARE_HEIGHT, location, null);
        background.add(boardSquare);
        this.getChildren().add(boardSquare);
      }
    }
    changeColors(DEFAULT_COLOR_1, DEFAULT_COLOR_2);
  }

  private void renderInitialChessPieces(List<PieceViewBuilder> pieceViews, String style){
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
    return new Location((int) mouse.getY() / SQUARE_HEIGHT, (int) mouse.getX() / SQUARE_WIDTH);
  }

  private BoardSquare findBoardSquare(Location location) {
    return background.get(location.getRow() * col + location.getCol());
  }

  /**
   * Returns desired selected location
   * @return
   */
  protected Location getSelectedLocation() {
    return selectedLocation;
  }

  /**
   * Marks locations
   * @param specialLocations
   */
  public void markInitialSpecialLocation(List<Location> specialLocations) {
    for (Location location : specialLocations) {
      findBoardSquare(location).getChildren().add(new PowerupView(location));
    }
  }
}