package ooga.model.EndConditionHandler;

import java.util.ArrayList;
import java.util.List;
import ooga.model.PlayerInterface;
import ooga.model.GameState;

/**
 * @authors
 * purpose - the purpose of the endconditionrunner is to keep track of all the end conditions, loop through
 * them, and return the state of the game if an end condition has determined a winner
 * assumptions - it assumes that there are defined end conditions as well as that all the pieces and
 * players are valid
 * dependencies - it depends on the PlayerInterface and Gamestate
 * usage - the satisfiedendcondition method is called after each move to see if an end condition has
 * been met, and to check the game state
 */
public class EndConditionRunner {
  private List<EndConditionInterface> endConditions;

  /**
   * This keeps track of all the end conditions for a game
   */
  public EndConditionRunner(){
    endConditions = new ArrayList<>();
  }

  /**
   * this adds an end condition to the game
   * @param endCondition type of end condition to be added to the game
   */
  public void add(EndConditionInterface endCondition){
    endConditions.add(endCondition);
  }

  /**
   * determines the current state of the game by returning a winner if found
   * @param players all the players
   * @return winning player, or running gamestate
   */
  public GameState satisfiedEndCondition(List<PlayerInterface> players){
    for (EndConditionInterface endCondition : endConditions){
      GameState winner = endCondition.isSatisfied(players);
      if(winner != null) {
        System.out.println("win triggered by "+endCondition.getClass());
        return winner;
      }
    }
    return GameState.RUNNING;
  }
}
