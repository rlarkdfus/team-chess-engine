package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

/**
 * @authors gordon, sam
 * purpose - longcastle implements queenside castling where the king moves the long way
 * assumptions - it assumes that the pieces and players are all valid
 * dependencies - it depends on Location and PieceInterface
 * usage - if a piece has this move the move class will check if it is legal and return it
 * as part of its end locations
 */
public class LongCastleMove extends CastleMove {
  /**
   * queen side castling
   *
   * @param dRow    delta row
   * @param dCol    delta col
   * @param take    move takes
   * @param limited move is limited
   */
  public LongCastleMove(int dRow, int dCol, boolean take, boolean limited, Location bounds) {
    super(dRow, dCol, take, limited, bounds);
  }

  /**
   * @param king location of king
   * @return location of rook
   */
  @Override
  protected Location findRookLocation(PieceInterface king) {
    return new Location(king.getLocation().getRow(), 0);
  }
}
