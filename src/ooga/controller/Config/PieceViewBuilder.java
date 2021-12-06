package ooga.controller.Config;

import ooga.Location;
import ooga.model.PieceInterface;

/**
 * @Authors albert
 * This class is just a data class that is used to build pieceView objects in the view.
 * This is built in board builder and sent to View. We use this object so we can isolate all javafx
 * objects in the view package.
 */
public class PieceViewBuilder {

  private String team;
  private String name;
  private Location location;

  /**
   * This is the constructor. It takes in the piece that it's representing and copies the 3 fields, which
   * are necessary to build the corresponding pieceview object
   * @param piece - the piece that is being referenced by the to-be-made pieceview object
   */
  public PieceViewBuilder(PieceInterface piece) {
    this.team = piece.getTeam();
    this.name = piece.getName();
    this.location = piece.getLocation();
  }

  /**
   * getter method for finding the team
   * @return a string of the team (ie "w" for white)
   */
  public String getTeam() {
    return team;
  }

  /**
   * getter method for finding the piece type
   * @return a string of the piece type (ie "K" for king)
   */
  public String getName() {
    return name;
  }

  /**
   * getter method for finding the location of the object
   * @return a Location object
   */
  public Location getLocation() {
    return location;
  }
}
