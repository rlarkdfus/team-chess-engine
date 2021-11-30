package ooga.model;

import ooga.Location;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import ooga.model.Moves.Move;

public class Piece implements PieceInterface {

  private String team;
  private String name;
  private int score;

  private Location location;
  private boolean moved;
  private boolean firstMove = false;

  private Map<String, Boolean> attributes;
  private boolean isEliminated;
  private boolean endConditionSatisified;
  private int uniqueID;
  //Used to create a unique hash/id for each piece;
  private Location initialLocation;
  String endStateString = "eliminated";

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

  /**
   * returns the name of a piece
   * @return
   */
    @Override
    public String getName() {
        return name;
    }

  /**
   * returns the location of a piece
   * @return
   */
  @Override
    public Location getLocation() {
        return location;
    }

  /**
   * moves piece and updates first move and moved flags
   * @param location
   */
  @Override
    public void moveTo(Location location) {
      firstMove = !firstMove && !moved;
      moved = true;
      tryMove(location);
    }

  /**
   * set a piece location
   * @param location
   */
  @Override
    public void tryMove(Location location) {
      this.location = location;
    }

  /**
   * return the unique id of a piece
   * @return
   */
  @Override
  public int getUniqueId() {
    return this.uniqueID;
  }

  /**
   * set a piece to be eliminated
   * @param state
   */
  @Override
  public void setEliminated(boolean state) {
    isEliminated = state;
  }

  /**
   * return whether a piece is eliminated
   * @return
   */
  @Override
  public boolean getEliminatedState() {
    return isEliminated;
  }


  //Fixme: Fix the end state
  @Override
  public boolean getEndState() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method method= this.getClass().getDeclaredMethod(endStateString);
    //endState string can be standard(no end state, or eliminated, meaning it is killed)
    Object value = method.invoke(this);
    return (boolean) value;
  }

  //Used for reflection.
  private boolean standard(){
    return true;
  }
  private boolean eliminated(){
    return this.isEliminated;
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
   * override equals to check if pieces have same score, location, name, and team
   * @param o
   * @return
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Piece piece = (Piece) o;
    return score == piece.score && Objects.equals(location, piece.location) && Objects.equals(team, piece.team) && Objects.equals(name, piece.name);
  }

  /**
   * gets a hash code of piece
   * @return
   */
  @Override
  public int hashCode() {
    return Objects.hash(initialLocation, team, score, name);
  }




  /**
   * get all the locations a piece can move to
   * @return
   */
  @Override
  public List<Location> getEndLocations() {
    List<Location> locations = new ArrayList<>();
    for(Move move : moves) {
//      System.out.println(move.getClass());
      locations.addAll(move.getEndLocations());
    }
    return locations;
  }

  /**
   * return whether a piece has moved
   * @return
   */
  @Override
  public boolean hasMoved() {
    return moved;
  }

  /**
   * return whether a piece has just done its first move
   * @return
   */
  @Override
  public boolean isFirstMove() {
    return firstMove;
  }

  /**
   * return a new copy of the same piece
   * @return
   */
  @Override
  public Piece copy() {
    return new Piece(this.team, this.name, this.location, this.moves, this.attributes, this.score);
  }

  /**
   * return all the end locations of a piece
   * @return
   */
  @Override
  public List<Location> getAllEndLocations() {
    List<Location> endLocations = new ArrayList<>();
    for(Move move : moves) {
      endLocations.addAll(move.getEndLocations());
    }
    return endLocations;
  }

  /**
   * this method returns the move that results in a piece being at the end location
   * @param end
   * @return
   */
  @Override
  public Move getMove(Location end) {
    for(Move move : moves) {
      if(end.inList(move.getEndLocations())) {
        return move;
      }
    }
    return null;
  }

  /**
   * this method updates the move locations of all pieces
   * @param pieces
   */
  @Override
  public void updateMoves(List<PieceInterface> pieces) {
    firstMove = false;
    for(Move move : moves) {
      move.updateMoveLocations(this, pieces);
    }
  }

}
