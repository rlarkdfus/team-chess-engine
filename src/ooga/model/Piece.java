package ooga.model;

import ooga.Location;

import java.util.Map;

public class Piece implements PieceInterface {

    private Location location;
  private MoveVector moveVectors;
  private String team;
  private boolean hasMoved;
  private int score;
  private String name;
  private Map<String, Boolean> attributes;

  public Piece(String team, String name, Location location, MoveVector moveVectors, Map<String, Boolean> attributes) {
    this.team = team;
    this.name = name;
    this.location = location;
    this.moveVectors = moveVectors;
    this.attributes = attributes;
    hasMoved = false;
  }

  /**
   * gets white or black team, used for modifying move vector
   *
   * @return
   */
  @Override
  public String getTeam() {
    return team;
  }

  /**
   * returns a list of all the possible move locations for a piece at given location
   *
   * @param
   * @return
   */
  @Override
  public MoveVector getMoves() {
    return moveVectors;
  }

  /**
   * returns whether a piece is able to move in a limited fashion
   *
   * @return
   */
  @Override
  public boolean isLimited() {
    return attributes.getOrDefault("limited", true);
  }

  /**
   * returns the value of a piece
   *
   * @return
   */
  @Override
  public int getScore() {
    return score;
  }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void moveTo(Location location) {
        this.location = location;
    }

    /**
   * override toString to print out piece information
   *
   * @return
   */
  @Override
  public String toString() {
    return moveVectors.toString();
  }

  /**
   * holds the vector of move directions for each piece
   */
}
