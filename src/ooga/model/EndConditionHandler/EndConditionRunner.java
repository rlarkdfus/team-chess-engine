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
    endConditions = List.of(new CheckmateEndCondition());
    System.out.println("endcondition size" + endConditions.size());
    for (EndConditionInterface endCondition : endConditions){
      GameState winner = endCondition.isSatisfied(players);
      if(winner != null) {
        return winner;
      }
    }
    return GameState.RUNNING;
  }
}
