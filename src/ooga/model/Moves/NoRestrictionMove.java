package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * @authors gordon
 * purpose - norestrictionmove allows a piece to move anywhere, and is used in editor view
 * assumptions - it assumes that the pieces and players are all valid
 * dependencies - it depends on Location and PieceInterface
 * usage - piece can hold norestrictionmove during editing which allows it to move anywhere
 */
public class NoRestrictionMove extends TranslationMove {
  /**
   * extends the translation move
   *
   * @param dRow    delta row
   * @param dCol    delta col
   * @param take    whether move can take
   * @param limited whether move is limited
   * @param bounds  bounds of the board
   */
  public NoRestrictionMove(int dRow, int dCol, boolean take, boolean limited, Location bounds) {
    super(dRow, dCol, take, limited, bounds);
  }

  /**
   * find all the end locations on the board
   *
   * @param piece  is the piece player is attempting to move
   * @param pieces all pieces
   * @return all the end locations on the board
   */
  @Override
  public List<Location> findAllEndLocations(PieceInterface piece, List<PieceInterface> pieces) {
    List<Location> locations = new ArrayList<>();
    int row = 0;
    int col = 0;
    while (MoveUtility.inBounds(row, col, getBounds())) {
      while (MoveUtility.inBounds(row, col, getBounds())) {
        locations.add(new Location(row, col));
        col++;
      }
      col = 0;
      row++;
    }
    return locations;
  }

  /**
   * move is always legal
   *
   * @param piece             current piece
   * @param potentialLocation location to move to
   * @param pieces            all pieces
   * @return true because move is always legal
   */
  @Override
  protected boolean isLegal(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces) {
    return true;
  }
}
