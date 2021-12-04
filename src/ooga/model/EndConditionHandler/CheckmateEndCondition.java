package ooga.model.EndConditionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.model.GameState;
import ooga.model.Moves.MoveUtility;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

public class CheckmateEndCondition implements EndConditionInterface {

  public CheckmateEndCondition(Map<String, List<String>> properties, List<PieceInterface> allpieces) {

  }

  @Override
  public GameState isSatisfied(List<PlayerInterface> players) {
    List<PieceInterface> pieces = getAllPieces(players);
    for(PlayerInterface player : players){
      int numlegalmoves = getTotalLegalMoves(player);
      if(numlegalmoves == 0) {
        return MoveUtility.inCheck(player.getTeam(), pieces) ? GameState.ENDED.getLoser(player.getTeam()) : GameState.DRAW;
      }
    }
    return null;
  }

  private int getTotalLegalMoves(PlayerInterface player) {
    int totalLegalMoves = 0;
    for (PieceInterface piece : player.getPieces()) {
      totalLegalMoves += piece.getEndLocations().size();
    }
    return totalLegalMoves;
  }


  private List<PieceInterface> getAllPieces(List<PlayerInterface> players) {
    List<PieceInterface> pieces = new ArrayList<>();
    for(PlayerInterface player : players){
      pieces.addAll(player.getPieces());
    }
    return pieces;
  }
}
