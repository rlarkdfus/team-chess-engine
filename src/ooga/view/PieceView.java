package ooga.view;

import javafx.scene.image.ImageView;
import ooga.Location;

public class PieceView extends ImageView {

    public static final int PIECE_WIDTH = 60;
    public static final int PIECE_HEIGHT = 60;

    public PieceView(String side, String piece, String style, Location location) {
        super("images/" + "companion" + "/" + side + piece + ".png");
        this.setFitWidth(PIECE_WIDTH);
        this.setFitHeight(PIECE_HEIGHT);
        moveTo(location);
    }

    public void moveTo(Location location) {
        this.setX(location.getCol()*60);
        this.setY(location.getRow()*60);
    }
}