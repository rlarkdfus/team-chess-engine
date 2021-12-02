package ooga.model.EndConditionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.Location;
import ooga.model.Board.GameState;
import ooga.model.Moves.Move;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

public class CheckmateEndCondition implements EndConditionInterface {
  String winner;
  List<PlayerInterface> players;

  @Override
  public void setArgs(Map<String, List<String>> propertiesMap, List<PieceInterface> allpieces) {
  }

  @Override
  public GameState isGameOver(List<PlayerInterface> players) {
    this.players = players;
    if (check()) {
      if (getTotalLegalMoves() == 0) {
        return GameState.CHECKMATE;
      }else{
        return GameState.CHECK;
      }
    }
    return GameState.RUNNING;
  }

  private int getTotalLegalMoves() {
    int totalLegalMoves = 0;
    for (PieceInterface piece : checkedPlayer().getPieces()) {
      totalLegalMoves += numLegalMoves(piece);
    }
    return totalLegalMoves;
  }

  private PlayerInterface checkedPlayer() {
    for (PlayerInterface player : players){
      if (!player.getTeam().equals(winner)){
        return player;
      }
    }
    return null;
  }

  private int numLegalMoves(PieceInterface currpiece) {
    for (PlayerInterface player : players) {
      for (PieceInterface piece : player.getPieces()) {
        if (piece.getLocation().equals(currpiece.getLocation())) {
          return piece.getEndLocations().size();
        }
      }
    }
    return 0;
  }

  private boolean check(){
    List<PieceInterface> allPieces = getAllPieces();
    for (PlayerInterface player : players){
      PieceInterface king = findKing(player.getTeam(), allPieces);
//      System.out.println(player.getTeam() + " " + king.getLocation());
      List<PieceInterface> attackingPieces = getAttackingPieces(player.getTeam(), allPieces);
      if (underAttack(king.getLocation(), attackingPieces, allPieces)){

        winner = attackingPieces.get(0).getTeam();
        return true;
      }
    }
    return false;
  }

  private List<PieceInterface> getAllPieces() {
    List<PieceInterface> allPieces = new ArrayList<>();
    for (PlayerInterface player : players){
      allPieces.addAll(player.getPieces());
    }
    return allPieces;
  }

  /**
   * Checks if the king is under attack from enemy pieces
   * @param attackingTeam is the list of pieces attacking the king
   * @return true if the king is under attack from list of pieces
   */
  private boolean underAttack(Location kingLocation, List<PieceInterface> attackingTeam, List<PieceInterface> allPieces) {
    for(PieceInterface attackingPiece : attackingTeam) {
      List<Move> attackingMoves = attackingPiece.getMoves();
      for(Move attackingMove : attackingMoves) {
        if(kingLocation.inList(attackingMove.findAllEndLocations(attackingPiece, allPieces))) {
          return true;
        }
      }

    }
    return false;
  }

  private List<PieceInterface> getAttackingPieces(String attackedTeam, List<PieceInterface> allPieces) {
    List<PieceInterface> attackingPieces = new ArrayList<>();
    for(PieceInterface piece : allPieces) {
      if(!piece.getTeam().equals(attackedTeam)) {
        attackingPieces.add(piece);
      }
    }
    return attackingPieces;
  }

  private PieceInterface findKing(String team, List<PieceInterface> pieces) {
    for(PieceInterface piece : pieces) {
      if(team.equals(piece.getTeam()) && piece.getName().equals("K")) {
        return piece;
      }
    }
    return null;
  }

  @Override
  public String getWinner(){
    return winner;
  }
}
