package ooga.view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ooga.Location;

/**
 * Background of each Square on the board
 */
public class BoardSquare extends StackPane {

    private final Color HIGHLIGHT_FILTER_COLOR = Color.web("#b8e1ff");
    private final Color SELECT_FILTER_COLOR = Color.web("#13315C");
    private final Color ANNOTATE_FILTER_COLOR = Color.web("EA3546");

    private Rectangle square;
    private Rectangle selectFilter;
    private Rectangle highlightFilter;
    private Rectangle annotateFilter;
    private Color originalColor;

    public BoardSquare(Location location, Color color) {
        this.originalColor = color;
        initializeSquare(location);
    }

    private void initializeSquare(Location location) {
        this.setLayoutX(location.getCol()*60);
        this.setLayoutY(location.getRow()*60);

        square = new Rectangle(60, 60);
        square.setId(String.format("square_location(%d,%d)", location.getRow(), location.getCol()));
        square.setFill(originalColor);
        this.getChildren().add(square);

        highlightFilter = createHighlight(HIGHLIGHT_FILTER_COLOR);
        selectFilter = createHighlight(SELECT_FILTER_COLOR);
        annotateFilter = createHighlight(ANNOTATE_FILTER_COLOR);
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
        if(this.getChildren().contains(annotateFilter)) {
            this.getChildren().remove(annotateFilter);
        } else {
            this.getChildren().add(annotateFilter);
        }
    }

    private Rectangle createHighlight(Color color) {
        Rectangle highlight = new Rectangle(60, 60);
        highlight.setFill(color);
        highlight.setOpacity(0.5);
        return highlight;
    }
}