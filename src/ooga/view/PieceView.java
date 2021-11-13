package ooga.view;

import javafx.scene.image.ImageView;
import ooga.Location;

public class PieceView extends ImageView {

    public static final int PIECE_WIDTH = 60;
    public static final int PIECE_HEIGHT = 60;

    public PieceView(String side, String piece, String style, Location location) {
        super(String.format("images/%s/%s%s.png", style, side, piece));
        this.setId(String.format("pieceView_location(%d,%d)_style(%s)", location.getRow(), location.getCol(), style));
        this.setFitWidth(PIECE_WIDTH);
        this.setFitHeight(PIECE_HEIGHT);
        moveTo(location);
    }

    public void moveTo(Location location) {
        this.setX(location.getCol()*60);
        this.setY(location.getRow()*60);
    }
}