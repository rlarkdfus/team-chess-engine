package ooga.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import ooga.Location;
import ooga.model.Moves.Move;

public class Piece implements PieceInterface {
  private String team;
  private String name;
  private int score;
  private Location location;
  private boolean moved;
  private boolean firstMove = false;
  private boolean firstMoveUpdate = false;
  private List<Move> moves;

  /**
   * create a piece object with the following parameters
   * @param team team name
   * @param name piece name
   * @param location location of piece
   * @param moves list of all moves available to piece
   * @param score value of a piece
   */
  public Piece(String team, String name, Location location, List<Move> moves, int score) {
    this.location = location;
    this.team = team;
    this.moved = false;
    this.moves = moves;
    this.score = score;
    this.name = name;
  }

  /**
   * gets white or black team, used for modifying move vector
   *
   * @return the team name
   */
  @Override
  public String getTeam() {
    return team;
  }

  /**
   * returns the value of a piece
   *
   * @return the score of a piece
   */
  @Override
  public int getScore() {
    return score;
  }

  /**
   * returns the name of a piece
   * @return the name of a piece
   */
    @Override
    public String getName() {
        return name;
    }

  /**
   * returns the location of a piece
   * @return location of a piece
   */
  @Override
    public Location getLocation() {
        return location;
    }

  /**
   * moves piece and updates first move and moved flags
   * @param location location to move a piece to
   */
  @Override
    public void moveTo(Location location) {
      firstMove = !firstMove && !moved;
      if(firstMove) {
        System.out.println(this);
      }
      moved = true;
      tryMove(location);
    }

  /**
   * set a piece location
   * @param location move a piece to location
   */
  @Override
    public void tryMove(Location location) {
      this.location = location;
    }

  /**
   * override toString to print out piece information
   *
   * @return string representation of team and piece
   */
  @Override
  public String toString() {
    return team + name;
  }

  /**
   * override equals to check if pieces have same score, location, name, and team
   * @param o other piece
   * @return whether pieces have same score, location, team, and name
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Piece piece = (Piece) o;
    return score == piece.score && Objects.equals(location, piece.location) && Objects.equals(team, piece.team) && Objects.equals(name, piece.name);
  }

  /**
   * get all the locations a piece can move to
   * @return list of all end locations of a piece's moves
   */
  @Override
  public List<Location> getEndLocations() {
    List<Location> locations = new ArrayList<>();
    for(Move move : moves) {
      locations.addAll(move.getEndLocations());
    }
    return locations;
  }

  /**
   * return whether a piece has moved
   * @return whether a piece has moved
   */
  @Override
  public boolean hasMoved() {
    return moved;
  }

  /**
   * return whether a piece has just done its first move
   * @return whether a piece has just done its first move
   */
  @Override
  public boolean isFirstMove() {
    return (firstMove || firstMoveUpdate);
  }

  /**
   * Transform a piece from one type to another
   */
  @Override
  public void transform(PieceInterface newPiece) {
    this.name = newPiece.getName();
    this.moves = newPiece.getMoves();
    this.score = newPiece.getScore();
  }

  /**
   * this method returns the move that results in a piece being at the end location
   * @param end location of a move
   * @return the move that goes to end location
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
   * @param pieces all pieces
   */
  @Override
  public void updateMoves(List<PieceInterface> pieces) {
    firstMoveUpdate = firstMove && moved;
    firstMove = false;
    for(Move move : moves) {
      move.updateMoveLocations(this, pieces);
    }
  }

  /**
   * return the moves of a piece
   * @return list of all moves of a piece
   */
  @Override
  public List<Move> getMoves(){
    return moves;
  }

  /**
   * return whether this piece and another piece are on the same team
   * @param piece other piece
   * @return whether piece and other piece are on the same team
   */
  @Override
  public boolean isSameTeam(PieceInterface piece){
    return team.equals(piece.getTeam());
  }
}
