package ooga.model;

import ooga.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.model.Moves.Move;

public class Piece implements PieceInterface {

  private Location location;
  private MoveVector moveVectors;
  private String team;
  private boolean moved;
  private boolean firstMove = false;
  private int score;
  private String name;
  private Map<String, Boolean> attributes;
  private List<Move> moves;

  public Piece(String team, String name, Location location, MoveVector moveVectors, Map<String, Boolean> attributes, int score) {
    this.location = location;
    this.moveVectors = moveVectors;
    this.team = team;
    this.moved = false;
    this.score = score;
    this.name = name;
    this.attributes = attributes;
  }
  //Todo: make this the default constructor
  public Piece(String team, String name, Location location, List<Move> moves, Map<String, Boolean> attributes, int score) {
    this.location = location;
    this.team = team;
    this.moved = false;
    this.moves = moves;
    this.score = score;
    this.name = name;
    this.attributes = attributes;
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
  public List<Vector> getMoveVectors() {
    List<Vector> moves = new ArrayList<>(moveVectors.getMoveVectors());
    if(!moved) {
      moves.addAll(moveVectors.getInitialVectors());
    }
    return moves;
  }

  @Override
  public List<Vector> getTakeVectors() {
    return moveVectors.getTakeVectors();
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
      tryMove(location);
      firstMove = !firstMove && !moved;
      moved = true;
    }
    
    @Override
    public void tryMove(Location location) {
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
   * get all the locations a piece can move to
   * @return
   */
  @Override
  public List<Location> getEndLocations() {
    List<Location> locations = new ArrayList<>();
    for(Move move : moves) {
      locations.addAll(move.getEndLocations());
    }
    return locations;
  }

  @Override
  public boolean hasMoved() {
    return moved;
  }

  @Override
  public boolean isFirstMove() {
    return firstMove;
  }

  @Override
  public Piece copy() {
    return new Piece(this.team, this.name, this.location, this.moves, this.attributes, this.score);
  }
}
