package ooga.view;

import javafx.scene.image.ImageView;
import ooga.Location;

public class PieceView extends ImageView {

    public PieceView(String side, String piece, Location location) {
        super("images/" + side + "/" + piece + ".png");
        moveTo(location);
    }

    public void moveTo(Location location) {
        this.setX(location.getY()*60);
        this.setY(location.getX()*60);
    }
}