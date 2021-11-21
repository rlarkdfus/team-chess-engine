package ooga.model;

import ooga.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.model.Moves.Move;

public class Piece implements PieceInterface {

  private String team;
  private String name;
  private int score;

  private Location location;
  private boolean moved;
  private boolean firstMove = false;

  private Map<String, Boolean> attributes;
  private List<Move> moves;

  public Piece(String team, String name, Location location, MoveVector moveVectors, Map<String, Boolean> attributes, int score) {
    this.location = location;
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
    return team + name;
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

  @Override
  public List<Location> getAllMoves() {
    List<Location> endLocations = new ArrayList<>();
    for(Move move : moves) {
      endLocations.addAll(move.getEndLocations());
    }
    return endLocations;
  }

  @Override
  public Move getMove(Location end) {
    for(Move move : moves) {
      if(end.inList(move.getEndLocations())) {
        return move;
      }
    }
    return null;
  }
}
