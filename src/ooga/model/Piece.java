package ooga.model;

import ooga.Location;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Piece implements PieceInterface {

  private Location location;
  private MoveVector moveVectors;
  private String team;
  private boolean hasMoved;
  private int score;
  private String name;
  private Map<String, Boolean> attributes;
  private boolean isEliminated;
  private boolean endConditionSatisified;
  private int uniqueID;
  //Used to create a unique hash/id for each piece;
  private Location initialLocation;
  String endStateString = "eliminated";



  public Piece(String team, String name, Location location, MoveVector moveVectors, Map<String, Boolean> attributes, int score) {
    this.location = location;
    this.moveVectors = moveVectors;
    this.team = team;
    this.hasMoved = hasMoved;
    this.score = score;
    this.name = name;
    this.attributes = attributes;
  }


  public Piece(String team, String name, Location location, MoveVector moveVectors, Map<String, Boolean> attributes, int score, String endStateString) {
    this.location = location;
    this.moveVectors = moveVectors;
    this.team = team;
    this.hasMoved = hasMoved;
    this.score = score;
    this.name = name;
    this.attributes = attributes;
    this.endStateString = endStateString;
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
    if(!hasMoved) {
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
      hasMoved = true;
    }
    
    @Override
    public void tryMove(Location location) {
      this.location = location;
    }

  @Override
  public int getUniqueId() {
    return this.uniqueID;
  }

  @Override
  public void setEliminated(boolean state) {
    isEliminated = state;
  }

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
    return moveVectors.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Piece piece = (Piece) o;
    return score == piece.score && Objects.equals(location, piece.location) && Objects.equals(team, piece.team) && Objects.equals(name, piece.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(initialLocation, team, score, name);
  }




  /**
   * holds the vector of move directions for each piece
   */
}
