package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

public class ShortCastleMove extends CastleMove {

  /**
   * king side castling
   *
   * @param dRow    delta row
   * @param dCol    delta col
   * @param take    move takes
   * @param limited move is limited
   */
  public ShortCastleMove(int dRow, int dCol, boolean take, boolean limited, Location bounds) {
    super(dRow, dCol, take, limited, bounds);
  }

  /**
   * @param king location of king
   * @return location of rook
   */
  @Override
  protected Location findRookLocation(PieceInterface king) {
    return new Location(king.getLocation().getRow(), 7);
  }
}