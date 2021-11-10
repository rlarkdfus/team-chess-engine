package ooga.View;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Holds the square and the piece
 */
public class PieceView extends StackPane implements PieceViewInterface {

    private boolean holdingPiece;
    private final Square square;
    private String side;
    private String piece;

    public PieceView() {
        square = new Square(60, 60);
        this.getChildren().add(square);
    }

    @Override
    public void setPiece(String side, String piece) {
        removePiece();
        this.side = side;
        this.piece = piece;
        holdingPiece = true;
        this.getChildren().add(findPieceImage(side, piece));
    }

    public void movePiece(PieceView movedPiece) {
        movedPiece.removePiece();
        setPiece(movedPiece.side, movedPiece.piece);
    }

    private void removePiece() {
        if(holdingPiece) {
            this.getChildren().remove(1);
            holdingPiece = false;
        }
    }

    private ImageView findPieceImage(String side, String piece) {
        return new ImageView("images/" + side + "/" + piece + ".png");
    }

    public void setFill(Paint color) {
        square.setFill(color);
    }

    public boolean isHoldingPiece() {
        return holdingPiece;
    }


    /**
     * Background of each Square on the board
     */
    public class Square extends Rectangle {
        public Square(double length, double height) {
            super(length, height);
        }
    }
}