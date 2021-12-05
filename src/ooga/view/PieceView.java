package ooga.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.Location;

public class PieceView extends ImageView {

    public static final int PIECE_WIDTH = 60;
    public static final int PIECE_HEIGHT = 60;

    private String side;
    private String piece;
    private String style;
    private Location location;

    public PieceView(String side, String piece, String style, Location location) {
        super(String.format("images/%s/%s%s.png", style, side, piece));
        this.side = side;
        this.piece = piece;
        this.style = style;
        this.location = location;
        this.setFitWidth(PIECE_WIDTH);
        this.setFitHeight(PIECE_HEIGHT);
        setId();
        moveTo(location);
    }

    public String getTeam() {
        return side;
    }

    public String getName() {
        return piece;
    }

    public void moveTo(Location location) {
        this.location = location;
        this.setX(location.getCol()*60);
        this.setY(location.getRow()*60);
        setId();
    }

    public void changeStyle(String style) {
        this.setImage(new Image(String.format("images/%s/%s%s.png", style, side, piece)));
        this.style = style;
        setId();
    }

    private void setId() {
        this.setId(String.format("pieceView_side(%s)_piece(%s)_location(%d,%d)_style(%s)", side, piece, location.getRow(), location.getCol(), style));
    }
    
    public Location getLocation() {
        return location;
    }
}