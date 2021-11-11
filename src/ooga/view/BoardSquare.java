package ooga.view;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ooga.Location;

/**
 * Background of each Square on the board
 */
public class BoardSquare extends StackPane {

    private final Color LEGAL_MOVE_HIGHLIGHT = Color.web("#b8e1ff");
    private final Color SELECTED_HIGHLIGHT = Color.web("#13315C");

    private Rectangle square;
    private Rectangle highlightSelected;
    private Rectangle highlightLegalMove;
    private Color originalColor;

    public BoardSquare(Location location, Color color) {
        this.originalColor = color;
        initializeSquare(location);
    }

    private void initializeSquare(Location location) {
        this.setLayoutX(location.getY()*60);
        this.setLayoutY(location.getX()*60);

        square = new Rectangle(60, 60);
        square.setFill(originalColor);
        this.getChildren().add(square);

        highlightLegalMove = createHighlight(LEGAL_MOVE_HIGHLIGHT);
        highlightSelected = createHighlight(SELECTED_HIGHLIGHT);
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
    public void highlightLegalMove() {
        this.getChildren().add(highlightLegalMove);
    }

    /**
     * Add selected square highlight (darker than legal move highlight)
     */
    public void highlightSelected() {
        this.getChildren().add(highlightSelected);
    }

    private Rectangle createHighlight(Color color) {
        Rectangle highlight = new Rectangle(60, 60);
        highlight.setFill(color);
        highlight.setOpacity(0.5);
        return highlight;
    }
}