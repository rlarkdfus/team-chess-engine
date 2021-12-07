package ooga.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.Location;
import ooga.view.boardview.BoardView;

public class PieceView extends ImageView {

  private final String team;
  private final String name;
  private String style;
  private Location location;

  /**
   * creates PieceView with the specified team, name, style, and location
   *
   * @param team     the side the PieceView is part of
   * @param name     the type of the PieceView
   * @param style    the style of the PieceView
   * @param location the Location of the PieceView in the board
   */
  public PieceView(String team, String name, String style, Location location) {
    super(String.format("images/%s/%s%s.png", style, team, name));
    this.team = team;
    this.name = name;
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

  public Location getLocation() {
    return location;
  }


  /**
   * moves the pieceView to a specified location
   *
   * @param location the destination location
   */
  public void moveTo(Location location) {
    this.location = location;
    this.setX(location.getCol() * BoardView.SQUARE_WIDTH);
    this.setY(location.getRow() * BoardView.SQUARE_HEIGHT);
    setId();
  }

  /**
   * changes the style of the pieceView
   *
   * @param style the new style
   */
  public void changeStyle(String style) {
    this.setImage(new Image(String.format("images/%s/%s%s.png", style, team, name)));
    this.style = style;
    setId();
  }

  /**
   * sets the ID of the pieceView
   */
  private void setId() {
    this.setId(String.format("pieceView_side(%s)_piece(%s)_location(%d,%d)_style(%s)", team, name, location.getRow(), location.getCol(), style));
  }
}