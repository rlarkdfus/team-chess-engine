package ooga.model.EndConditionHandler;

import java.util.List;
import java.util.Map;
import ooga.model.Check;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

public class CheckmateEndCondition implements EndConditionInterface {

  String winner;
  List<PlayerInterface> players;
  Check check;

  @Override
  public void setArgs(Map<String, List<String>> propertiesMap, List<PieceInterface> allpieces) {
    check = new Check();
  }

  @Override
  public String foundWinner(List<PlayerInterface> players) {
    this.players = players;
    if (check.isTrue(players)) {
      winner = check.getWinner();
      if (getTotalLegalMoves() == 0) {
        return winner;
      }
    }
    return null;
  }

  private int getTotalLegalMoves() {
    int totalLegalMoves = 0;
    for (PieceInterface piece : checkedPlayer().getPieces()) {
      totalLegalMoves += numLegalMoves(piece);
    }
    return totalLegalMoves;
  }

  private PlayerInterface checkedPlayer() {
    for (PlayerInterface player : players) {
      if (!player.getTeam().equals(winner)) {
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
}
