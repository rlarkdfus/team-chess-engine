package ooga.model;

import java.util.List;
import java.util.Map;

public class Piece implements PieceInterface {

  private MoveVector moveVector;
  private String team;
  private boolean limited;
  private boolean hasMoved;
  private int score;
  private String pieceName;
  private Map<String, Boolean> attributes;

  public Piece(String team, List<Vector> vectors, boolean limited) {
    this.team = team;
    this.limited = limited;
    hasMoved = false;
    moveVector = new MoveVector(vectors, vectors, vectors); //FIXME: change to different vectors
  }

  //TODO: get rid of this constructor
  /**
   * want:
   * 1) public Piece(String team, Map<String, List<Vector>> vectors, Map<String, Boolean> attributes, String imagePath) {
   * 2) moveVector = new MoveVector(moveVectors, Takevectors, Initialvectors);
   *
   * Things that need to be refactored:
   * 1a) all piece.json need "pieceName" and "score" fields in "attributes"
   * 1b) all methods to get attributes use the map
   * 2) BoardBuilder(a lot): getMoveVectors(), and add ^ attributes in getAttributes()
   * 3) Board.initializeboard. well actually maybe not since this will be replaced by boardbuilder.
   */
  public Piece(String team, Map<String, List<Vector>> vectors,
      Map<String, Boolean> attributes, String imagePath) {
    this.team = team;
    this.attributes = attributes;
    hasMoved = false;
    moveVector = new MoveVector(vectors.get("moves"), vectors.get("takeMoves"), vectors.get("initialMoves")); //FIXME: change to different vectors
  }

  public Piece(String pieceName, String team, List<Vector> vectors, boolean limited, int score) {
    this.pieceName = pieceName;
    this.team = team;
    this.limited = limited;
    moveVector = new MoveVector(vectors, vectors, vectors);
    this.score = score;
  }

  @Override
  public String getPieceName() {
    return pieceName;
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
    return moveVector;
  }

  /**
   * returns whether a piece is able to move in a limited fashion
   *
   * @return
   */
  @Override
  public boolean isLimited() {
    return attributes.get("limited");
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
   * override toString to print out piece information
   *
   * @return
   */
  @Override
  public String toString() {
    return moveVector.toString();
  }

  /**
   * holds the vector of move directions for each piece
   */

  public class MoveVector implements MoveVectorInterface {

public class Piece implements PieceInterface{
    private MoveVector moveVector;

    private Location location;

    private String team;
    private boolean limited;
    private boolean hasMoved;
    private int score;
    private String pieceName;

    public Piece(Location location, String team, List<Vector> vectors, boolean limited) {
        this.location = location;
        this.team = team;
        this.limited = limited;
        hasMoved = false;
        moveVector = new MoveVector(vectors, vectors, vectors); //FIXME: change to different vectors
    }

//    public Piece(String pieceName, String team, List<Vector> vectors, boolean limited, int score) {
//        this.pieceName = pieceName;
//        this.team = team;
//        this.limited = limited;
//        moveVector = new MoveVector(vectors, vectors, vectors);
//        this.score = score;
//    }

    public Location getLocation(){
        return location;
    }

    public void updateLocation(Location location){
        this.location = location;
    }

    @Override
    public String getName() {return pieceName;}


    /**
     * return the possible move vectors
     *
     * @return
     */

    @Override
    public List<Vector> getMoveVectors() {
      return moveVectors;
    }

    /**
     * reuturn the row vector of a move
     *
     * @param index
     * @return
     */
    @Override
    public int getRowVector(int index) {
      return moveVectors.get(index).getdRow();
    }

    /**
     * return the column vector of a move
     *
     * @param index
     * @return
     */
    @Override
    public int getColVector(int index) {

      return moveVectors.get(index).getdCol();
    }
  }

  public static class Vector {

    int dRow;
    int dCol;

    public Vector(int dRow, int dCol) {
      this.dRow = dRow;
      this.dCol = dCol;
    }

    public int getdRow() {
      return dRow;
    }

    public int getdCol() {
      return dCol;
    }
  }

}
