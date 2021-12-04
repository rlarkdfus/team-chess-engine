package ooga.model.EndConditionHandler;

import java.util.ArrayList;
import java.util.List;
import ooga.model.PlayerInterface;
import ooga.model.GameState;

public class EndConditionRunner {
  private List<EndConditionInterface> endConditions;

  public EndConditionRunner(){
    endConditions = new ArrayList<>();
  }

  public void add(EndConditionInterface endCondition){
    endConditions.add(endCondition);
  }

  public GameState satisfiedEndCondition(List<PlayerInterface> players){
    for (EndConditionInterface endCondition : endConditions){
      GameState winner = endCondition.isSatisfied(players);
      if(winner != null) {
        System.out.println("win triggered by "+endCondition.getClass());
        System.out.println("runner print: "+ winner);
        return winner;
      }
    }
    return GameState.RUNNING;
  }
}
