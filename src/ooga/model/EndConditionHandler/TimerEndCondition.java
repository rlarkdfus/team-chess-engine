package ooga.model.EndConditionHandler;

import ooga.model.GameState;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

import java.util.List;
import java.util.Map;

/**
 * @authors purpose - the purpose of the timer end condition is to end the game when one player runs out of time
 * assumptions - it assumes that one player will have time left, and that the pieces and players are valid
 * dependencies - it depends on PieceInterface, PlayerInterface, and GameState
 * usage - the end condition runner loops through all the end conditions and calls checkmate to see if
 * a checkmate is met
 */
public class TimerEndCondition implements EndConditionInterface {
  public static final String OUT_OF_TIME = "00:00";

  public TimerEndCondition(Map<String, List<String>> properties, List<PieceInterface> allpieces) {

  }

  /**
   * ends the game when timer runs out
   *
   * @param players all players
   * @return player who has time remaining when the other player runs out of time
   */
  @Override
  public GameState isSatisfied(List<PlayerInterface> players) {
    for (PlayerInterface player : players) {
      if (player.getTimeLeft().getValue().equals(OUT_OF_TIME)) {
        return GameState.ENDED.getLoser(player.getTeam());
      }
    }
    return null;
  }
}
