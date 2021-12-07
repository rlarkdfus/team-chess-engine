package ooga.view.boardview;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ooga.Location;

import java.util.ResourceBundle;

/**
 * Purpose: This class represents an abstraction of what we see as each square or checker in the
 * board. This allows encapsulates the complexity of representing which board squares are
 * acceptable moves and thus aiding the user in his game play.
 * Assumptions: We assume the shape of the BoardSquare class will be a rectangle.
 * Dependencies: Location
 *
 * @author Gordon Kim
 */
public class BoardSquare extends StackPane {

  private static final ResourceBundle STYLE = ResourceBundle.getBundle("ooga/view/boardview/BoardSquare");
  private static final Color LEGAL_MOVES_FILTER_COLOR = Color.web(STYLE.getString("legal_moves_filter"));
  private static final Color SELECT_FILTER_COLOR = Color.web(STYLE.getString("select_filter"));
  private static final Color ANNOTATE_FILTER_COLOR = Color.web(STYLE.getString("annotate_filter"));
  private static final double DEFAULT_FILTER_OPACITY = Double.parseDouble(STYLE.getString("default_filter_opacity"));

  private Rectangle square;
  private Rectangle selectFilter;
  private Rectangle legalMovesFilter;
  private Rectangle annotateFilter;

  private Color originalColor;
  private final int width;
  private final int height;

  /**
   * Create a new BoardSquare object
   *
   * @param width    Width of board square
   * @param height   Height of board square
   * @param location Location for square
   * @param color    Color of square
   */
  public BoardSquare(int width, int height, Location location, Color color) {
    this.originalColor = color;
    this.width = width;
    this.height = height;
    initializeSquare(location);
  }

  private void initializeSquare(Location location) {
    this.setLayoutX(location.getCol() * width);
    this.setLayoutY(location.getRow() * height);

    square = new Rectangle(width, height);
    square.setFill(originalColor);
    this.getChildren().add(square);

    legalMovesFilter = createHighlight(LEGAL_MOVES_FILTER_COLOR, DEFAULT_FILTER_OPACITY);
    selectFilter = createHighlight(SELECT_FILTER_COLOR, DEFAULT_FILTER_OPACITY);
    annotateFilter = createHighlight(ANNOTATE_FILTER_COLOR, DEFAULT_FILTER_OPACITY);
    setIDs(location);
  }

  private void setIDs(Location location) {
    square.setId(String.format("square_location(%d,%d)", location.getRow(), location.getCol()));
    legalMovesFilter.setId(String.format("legal_location(%d,%d)", location.getRow(), location.getCol()));
    selectFilter.setId(String.format("select_location(%d,%d)", location.getRow(), location.getCol()));
    annotateFilter.setId(String.format("annotate_location(%d,%d)", location.getRow(), location.getCol()));
  }

  /**
   * Remove highlights
   */
  public void resetBoardSquare() {
    this.getChildren().clear();
    this.getChildren().add(square);
  }

  /**
   * Set square's original color
   *
   * @param originalColor Original color of square
   */
  public void setColor(Color originalColor) {
    this.originalColor = originalColor;
    square.setFill(originalColor);
  }

  /**
   * Add legal move highlight
   */
  public void highlight() {
    this.getChildren().add(legalMovesFilter);
  }

  /**
   * Add selected square highlight (darker than legal move highlight)
   */
  public void select() {
    this.getChildren().add(selectFilter);
  }

  /**
   * Add selected square highlight (darker than legal move highlight)
   */
  public void annotate() {
    if (this.getChildren().contains(annotateFilter)) {
      this.getChildren().remove(annotateFilter);
    } else {
      this.getChildren().add(annotateFilter);
    }
  }


  private Rectangle createHighlight(Color color, double opacity) {
    Rectangle highlight = new Rectangle(width, height);
    highlight.setFill(color);
    highlight.setOpacity(opacity);
    return highlight;
  }
}