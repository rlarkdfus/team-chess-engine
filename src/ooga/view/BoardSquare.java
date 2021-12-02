package ooga.view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ooga.Location;

/**
 * Background of each Square on the board
 */
public class BoardSquare extends StackPane {

    private static final Color HIGHLIGHT_FILTER_COLOR = Color.web("#b8e1ff");
    private static final Color SELECT_FILTER_COLOR = Color.web("#13315C");
    private static final Color ANNOTATE_FILTER_COLOR = Color.web("EA3546");
    private static final Color CHECK_FILTER_COLOR = Color.RED;
    private static final double DEFAULT_FILTER_OPACITY = 0.5;
    private static final double CHECK_FILTER_OPACITY = 1.0;

    private Rectangle square;
    private Rectangle selectFilter;
    private Rectangle highlightFilter;
    private Rectangle annotateFilter;
    private Rectangle checkFilter;

    private Color originalColor;

    public BoardSquare(Location location, Color color) {
        this.originalColor = color;
        initializeSquare(location);
    }

    private void initializeSquare(Location location) {
        this.setLayoutX(location.getCol()*60);
        this.setLayoutY(location.getRow()*60);

        square = new Rectangle(60, 60);
        square.setFill(originalColor);
        this.getChildren().add(square);

        highlightFilter = createHighlight(HIGHLIGHT_FILTER_COLOR, DEFAULT_FILTER_OPACITY);
        selectFilter = createHighlight(SELECT_FILTER_COLOR, DEFAULT_FILTER_OPACITY);
        annotateFilter = createHighlight(ANNOTATE_FILTER_COLOR, DEFAULT_FILTER_OPACITY);
        checkFilter = createHighlight(CHECK_FILTER_COLOR, CHECK_FILTER_OPACITY);

        setIDs(location);
    }

    private void setIDs(Location location) {
        square.setId(String.format("square_location(%d,%d)", location.getRow(), location.getCol()));
        highlightFilter.setId(String.format("highlight_location(%d,%d)", location.getRow(), location.getCol()));
        selectFilter.setId(String.format("select_location(%d,%d)", location.getRow(), location.getCol()));
        annotateFilter.setId(String.format("annotate_location(%d,%d)", location.getRow(), location.getCol()));
        checkFilter.setId(String.format("check_location(%d,%d)", location.getRow(), location.getCol()));
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
     * @param originalColor
     */
    public void setColor(Color originalColor) {
        this.originalColor = originalColor;
        square.setFill(originalColor);
    }

    /**
     * Add legal move highlight
     */
    public void highlight() {
        this.getChildren().add(highlightFilter);
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

    /**
     * Add checked square highlight (solid red by default)
     */
    public void check() {
        this.getChildren().add(checkFilter);
    }

    private Rectangle createHighlight(Color color, double opacity) {
        Rectangle highlight = new Rectangle(60, 60);
        highlight.setFill(color);
        highlight.setOpacity(opacity);
        return highlight;
    }
}