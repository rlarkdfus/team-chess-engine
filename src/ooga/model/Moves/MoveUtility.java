package ooga.model.Moves;

import ooga.Location;
import ooga.model.Board;
import ooga.model.PieceInterface;

import java.util.ArrayList;
import java.util.List;

import static ooga.controller.Config.PowerUpsBuilder.ZERO_BOUNDS;

/**
 * @authors gordon, sam
 * purpose - this class gets the piece at a location, and can get all the pieces that are attacking a piece
 * assumptions - it assumes that the pieces and players are all valid
 * dependencies - it depends on Location, Board, and PieceInterface
 * usage - other classes can call moveutility to get the piece at a location and use incheck to determine if
 * moves are valid
 */
public class MoveUtility {
  /**
   * return whether a team is in check
   *
   * @param team   team being checked
   * @param pieces all pieces
   * @return team in check or not
   */
  public static boolean inCheck(String team, List<PieceInterface> pieces) {
    PieceInterface king = findKing(team, pieces);
    List<PieceInterface> attackingPieces = getAttackingPieces(king.getTeam(), pieces);
    return underAttack(king.getLocation(), attackingPieces, pieces);
  }

  private static PieceInterface findKing(String team, List<PieceInterface> pieces) {
    for (PieceInterface piece : pieces) {
      if (piece.getTeam().equals(team) && piece.getName().equals(Board.PIECES.getString("KING"))) {
        return piece;
      }
    }
    return null;
  }

  /**
   * get the piece at a location
   *
   * @param location location to search
   * @param pieces   all pieces
   * @return piece at a location
   */
  public static PieceInterface pieceAt(Location location, List<PieceInterface> pieces) {
    for (PieceInterface piece : pieces) {
      if (piece.getLocation().equals(location)) {
        return piece;
      }
    }
    return null;
  }

  /**
   * get all the pieces that can attack a team
   *
   * @param team      team being attacked
   * @param allPieces all pieces
   * @return all the pieces that can attack the current team
   */
  protected static List<PieceInterface> getAttackingPieces(String team, List<PieceInterface> allPieces) {
    List<PieceInterface> attackingPieces = new ArrayList<>();
    for (PieceInterface piece : allPieces) {
      if (!piece.getTeam().equals(team)) {
        attackingPieces.add(piece);
      }
    }
    return attackingPieces;
  }

  /**
   * determine if a row and column are in bounds of the board
   *
   * @param newRow new row position
   * @param newCol new col position
   * @return whether a row and column are in bounds
   */
  protected static boolean inBounds(int newRow, int newCol, Location bounds) {
    return (newRow < bounds.getRow() && newCol < bounds.getCol() && newRow >= ZERO_BOUNDS && newCol >= ZERO_BOUNDS);
  }

  /**
   * return whether any pieces exist at locations
   *
   * @param locations locations to be searched
   * @param pieces    all pieces
   * @return whether any location contains a piece
   */
  static boolean isClear(List<Location> locations, List<PieceInterface> pieces) {
    for (PieceInterface piece : pieces) {
      if (piece.getLocation().inList(locations)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if the king is under attack from enemy pieces
   *
   * @param attackingPieces is the list of pieces attacking the king
   * @return true if the king is under attack from list of pieces
   */
  static boolean underAttack(Location location, List<PieceInterface> attackingPieces, List<PieceInterface> allPieces) {
    for (PieceInterface attackingPiece : attackingPieces) {
      List<Move> attackingMoves = attackingPiece.getMoves();
      for (Move attackingMove : attackingMoves) {
        if (location.inList(attackingMove.findAllEndLocations(attackingPiece, allPieces))) {
          return true;
        }
      }
    }
    return false;
  }
}
