package ooga.model.EndConditionHandler;

import java.util.List;
import ooga.model.GameState;
import ooga.model.PlayerInterface;

public interface EndConditionInterface {
  /**
   * return a winner if any of the end conditions are met. if none of the end conditions are met
   * then return null
   * @param players all players
   * @return the winner of the game, if not null
   */
  GameState isSatisfied(List<PlayerInterface> players);
}
