package ooga;

import java.util.ArrayList;
import java.util.List;

public class Turn {
  private final List<PieceMove> moves;
  private final List<Location> removedPieces;
  private Location checkedSquare;

  public Turn() {
    moves = new ArrayList<>();
    removedPieces = new ArrayList<>();
    checkedSquare = null;
  }

  /**
   * add moved piece to list
   *
   * @param start
   * @param end
   */
  public void movePiece(Location start, Location end) {
    moves.add(new PieceMove(start, end));
  }

  /**
   * add removed piece to list
   *
   * @param location
   */
  public void removePiece(Location location) {
    removedPieces.add(location);
  }

  /**
   * add square to be highlighed when checked
   *
   * @param location
   */
  public void addCheckedSquare(Location location) {
    checkedSquare = location;
  }

  /**
   * return the moved pieces
   *
   * @return
   */
  public Location getCheckedSquare() {
    return checkedSquare;
  }

  /**
   * return the moved pieces
   *
   * @return
   */
  public List<PieceMove> getMoves() {
    return moves;
  }

  /**
   * return the removed pieces
   *
   * @return
   */
  public List<Location> getRemoved() {
    return removedPieces;
  }

  /**
   * combines multiple turns into one
   *
   * @param turn
   */
  public void addTurn(Turn turn) {
    moves.addAll(turn.getMoves());
    removedPieces.addAll(turn.getRemoved());
  }

  public class PieceMove {
    private final Location start;
    private final Location end;

    public PieceMove(Location start, Location end) {
      this.start = start;
      this.end = end;
    }

    /**
     * get the starting location
     *
     * @return
     */
    public Location getStartLocation() {
      return start;
    }

    /**
     * get the ending location
     *
     * @return
     */
    public Location getEndLocation() {
      return end;
    }
  }
}