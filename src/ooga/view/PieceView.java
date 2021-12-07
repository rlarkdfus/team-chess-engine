package ooga.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.Location;
import ooga.view.boardview.BoardView;

public class PieceView extends ImageView {

    private String team;
    private String name;
    private String style;
    private Location location;

<<<<<<< HEAD
    public PieceView(String side, String piece, String style, Location location) {
        super(String.format("images/%s/%s%s.png", style, side, piece));
        this.side = side;
        this.piece = piece;
=======
    public PieceView(String team, String name, String style, Location location){
        super(String.format("images/%s/%s%s.png", style, team, name));
        this.team = team;
        this.name = name;
>>>>>>> 1dc0d1373ac97e551eb9625607d4fa144b85c534
        this.style = style;
        this.location = location;
        this.setFitWidth(BoardView.SQUARE_WIDTH);
        this.setFitHeight(BoardView.SQUARE_HEIGHT);
        setId();
        moveTo(location);
    }

    public String getTeam() {
        return team;
    }

    public String getName() {
        return name;
    }

    public void moveTo(Location location) {
        this.location = location;
        this.setX(location.getCol()*BoardView.SQUARE_WIDTH);
        this.setY(location.getRow()*BoardView.SQUARE_HEIGHT);
        setId();
    }

    public void changeStyle(String style) {
        this.setImage(new Image(String.format("images/%s/%s%s.png", style, team, name)));
        this.style = style;
        setId();
    }

    private void setId() {
        this.setId(String.format("pieceView_side(%s)_piece(%s)_location(%d,%d)_style(%s)", team, name, location.getRow(), location.getCol(), style));
    }
    
    public Location getLocation() {
        return location;
    }
}