package ooga.model.Moves;

import ooga.Location;
import ooga.model.Board;
import ooga.model.PieceInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * @authors gordon, sam
 * purpose - defines en passant, which allows a piece to take a piece indirectly when it meets
 * the conditions on firstmove
 * assumptions - it assumes that the pieces and players are valid
 * dependencies - it depends on Location, Board, and PieceInterface
 * usage - if a piece has this move the move class will check if it is legal and return it
 * as part of its end locations
 */
public class EnPassantMove extends Move {
  /**
   * en passant move for pawns to indirectly take other pawns
   *
   * @param dRow    delta row
   * @param dCol    delta col
   * @param take    move takes
   * @param limited move is limited
   */
  public EnPassantMove(int dRow, int dCol, boolean take, boolean limited, Location bounds) {
    super(dRow, dCol, take, limited, bounds);
  }

  /**
   * @param pawn   piece to move
   * @param pieces all pieces
   * @param end    end location
   */
  @Override
  public void executeMove(PieceInterface pawn, List<PieceInterface> pieces, Location end) {
    Location enemyPawnLocation = new Location(end.getRow() - getdRow(), end.getCol());

    Location removeFrom = MoveUtility.pieceAt(enemyPawnLocation, pieces) == null ? end : enemyPawnLocation;
    System.out.println("EnPassantMove.executeMove removed " + MoveUtility.pieceAt(removeFrom, pieces));
    removePiece(MoveUtility.pieceAt(removeFrom, pieces), pieces);
    movePiece(pawn, end);
  }

  /**
   * @param pawn              piece to move
   * @param potentialLocation location to move to
   * @param pieces            all pieces
   * @return whether a location is a legal move for a pawn
   */
  @Override
  protected boolean isLegal(PieceInterface pawn, Location potentialLocation, List<PieceInterface> pieces) {
    Location otherPawnLocation = new Location(pawn.getLocation().getRow(), potentialLocation.getCol());

    if (MoveUtility.pieceAt(potentialLocation, pieces) != null || MoveUtility.pieceAt(otherPawnLocation, pieces) == null) {
      return false;
    }

    PieceInterface otherPawn = MoveUtility.pieceAt(otherPawnLocation, pieces);
//        if(otherPawn != null) {
//            System.out.println(pawn.toString() + " " + pawn.getLocation() + ", " + otherPawn.toString() + " " + otherPawnLocation);
//        }

    if (otherPawn == null || !otherPawn.getName().equals(Board.PIECES.getString("PAWN")) || !otherPawn.isFirstMove()) {
      return false;
    }
    return tryMove(pawn, potentialLocation, new ArrayList<>(pieces));
  }

  /**
   * @param potentialLocation location of pawn diagonal
   * @param pieces            all pieces
   * @return piece if it exists
   */
  @Override
  protected PieceInterface tryTakeMove(Location potentialLocation, List<PieceInterface> pieces) {
    Location otherPawnLocation = new Location(potentialLocation.getRow() - getdRow(), potentialLocation.getCol());
    return MoveUtility.pieceAt(otherPawnLocation, pieces);
  }
}
