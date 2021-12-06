package ooga.model.EndConditionHandler;

import java.util.ArrayList;
import java.util.List;
import ooga.model.PlayerInterface;
import ooga.model.GameState;

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
