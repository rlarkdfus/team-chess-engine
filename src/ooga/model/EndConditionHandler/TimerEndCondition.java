package ooga.model.EndConditionHandler;

import java.util.List;
import java.util.Map;
import ooga.model.GameState;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

public class TimerEndCondition implements EndConditionInterface{
  public static final String OUT_OF_TIME = "00:00";

  public TimerEndCondition(Map<String, List<String>> properties, List<PieceInterface> allpieces){

  }

  /**
   * ends the game when timer runs out
   * @param players all players
   * @return player who has time remaining when the other player runs out of time
   */
  @Override
  public GameState isSatisfied(List<PlayerInterface> players) {
    for (PlayerInterface player : players){
      if (player.getTimeLeft().getValue().equals(OUT_OF_TIME)){
        return GameState.ENDED.getLoser(player.getTeam());
      }
    }
    return null;
  }
}
