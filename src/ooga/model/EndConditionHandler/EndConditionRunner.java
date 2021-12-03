package ooga.model.EndConditionHandler;

import java.util.ArrayList;
import java.util.List;
import ooga.model.Board.GameState;
import ooga.model.PlayerInterface;

public class EndConditionRunner {
  private List<EndConditionInterface> endConditions;
  private String winner;

  public EndConditionRunner(){
    endConditions = new ArrayList<>();
  }

  public void add(EndConditionInterface endCondition){
    endConditions.add(endCondition);
  }
  public GameState satisfiedEndCondition(List<PlayerInterface> players){
    for (EndConditionInterface endCondition : endConditions){
      winner = endCondition.foundWinner(players);
      if (winner != null){
        return GameState.CHECKMATE;
      }
    }
    return GameState.RUNNING;
  }

  public String getWinner() {
    return winner;
  }
}
