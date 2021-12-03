package ooga.model;

import java.util.ArrayList;
import java.util.List;
import ooga.Location;
import ooga.model.Moves.Move;

public class Check {
  List<PlayerInterface> players;
  String winner;

  public String getWinner() {
    return winner;
  }

  public boolean isTrue(List<PlayerInterface> players){
    this.players = players;
    List<PieceInterface> allPieces = getAllPieces();
    for (PlayerInterface player : players) {
      PieceInterface king = findKing(player.getTeam(), allPieces);
      List<PieceInterface> attackingPieces = getAttackingPieces(player.getTeam(), allPieces);
      if (underAttack(king.getLocation(), attackingPieces, allPieces)) {
        winner = attackingPieces.get(0).getTeam();
        return true;
      }
    }
    return false;
  }

  private List<PieceInterface> getAllPieces() {
    List<PieceInterface> allPieces = new ArrayList<>();
    for (PlayerInterface player : players) {
      allPieces.addAll(player.getPieces());
    }
    return allPieces;
  }

  /**
   * Checks if the king is under attack from enemy pieces
   *
   * @param attackingTeam is the list of pieces attacking the king
   * @return true if the king is under attack from list of pieces
   */
  private boolean underAttack(Location kingLocation, List<PieceInterface> attackingTeam,
      List<PieceInterface> allPieces) {
    for (PieceInterface attackingPiece : attackingTeam) {
      List<Move> attackingMoves = attackingPiece.getMoves();
      for (Move attackingMove : attackingMoves) {
        if (kingLocation.inList(attackingMove.findAllEndLocations(attackingPiece, allPieces))) {
          return true;
        }
      }

    }
    return false;
  }

  private List<PieceInterface> getAttackingPieces(String attackedTeam,
      List<PieceInterface> allPieces) {
    List<PieceInterface> attackingPieces = new ArrayList<>();
    for (PieceInterface piece : allPieces) {
      if (!piece.getTeam().equals(attackedTeam)) {
        attackingPieces.add(piece);
      }
    }
    return attackingPieces;
  }

  private PieceInterface findKing(String team, List<PieceInterface> pieces) {
    for (PieceInterface piece : pieces) {
      if (team.equals(piece.getTeam()) && piece.getName().equals("K")) {
        return piece;
      }
    }
    return null;
  }
}
