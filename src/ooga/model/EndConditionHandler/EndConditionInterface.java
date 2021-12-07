package ooga.model.EndConditionHandler;

import ooga.model.GameState;
import ooga.model.PlayerInterface;

import java.util.List;

/**
 * @authors purpose - the purpose of the endconditioninterface is to define the api for checking whether any
 * of the game end conditions are satisfied.
 * assumptions - it assumes that there are end conditions defined
 * dependencies - it depends on the PlayerInterface and GameState
 * usage - the end condition runner loops through all the end conditions and calls checkmate to see if
 * a checkmate is met
 */
public interface EndConditionInterface {
  /**
   * return a winner if any of the end conditions are met. if none of the end conditions are met
   * then return null
   *
   * @param players all players
   * @return the winner of the game, if not null
   */
  GameState isSatisfied(List<PlayerInterface> players);
}
