package ooga.model;

import ooga.Location;

import java.util.Map;

public class Piece implements PieceInterface {

    private Location location;
  private MoveVector moveVectors;
  private String team;
  private boolean limited;
  private boolean hasMoved;
  private int score;
  private String name;
  private Map<String, Boolean> attributes;

  public Piece(String team, String name, Location location, MoveVector moveVectors, boolean limited) {
    this.team = team;
    this.name = name;
    this.location = location;
    this.moveVectors = moveVectors;
    this.limited = limited;
    hasMoved = false;
  }

//  //TODO: get rid of this constructor
//  /**
//   * want:
//   * 1) public Piece(String team, Map<String, List<Vector>> vectors, Map<String, Boolean> attributes, String imagePath) {
//   * 2) moveVector = new MoveVector(moveVectors, Takevectors, Initialvectors);
//   *
//   * Things that need to be refactored:
//   * 1a) all piece.json need "pieceName" and "score" fields in "attributes"
//   * 1b) all methods to get attributes use the map
//   * 2) BoardBuilder(a lot): getMoveVectors(), and add ^ attributes in getAttributes()
//   * 3) Board.initializeboard. well actually maybe not since this will be replaced by boardbuilder.
//   */
//  public Piece(String team, Map<String, List<Vector>> vectors,
//      Map<String, Boolean> attributes, String imagePath) {
//    this.team = team;
//    this.attributes = attributes;
//    hasMoved = false;
//    moveVector = new MoveVector(vectors.get("moves"), vectors.get("takeMoves"), vectors.get("initialMoves")); //FIXME: change to different vectors
//  }
//
//  public Piece(String pieceName, String team, List<Vector> vectors, boolean limited, int score) {
//    this.pieceName = pieceName;
//    this.team = team;
//    this.limited = limited;
//    moveVector = new MoveVector(vectors, vectors, vectors);
//    this.score = score;
//  }

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
    return limited;
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
