package ooga.model.EndConditionHandler;

import java.util.List;
import java.util.Map;
import ooga.controller.InvalidEndGameConfigException;
import ooga.controller.InvalidGameConfigException;
import ooga.model.GameState;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

public class TimerEndCondition implements EndConditionInterface{

  @Override
  public GameState isSatisfied(List<PlayerInterface> players) {
    for (PlayerInterface player : players){
      if (player.getTimeLeft().getValue().equals("00:00")){
        return GameState.ENDED.getLoser(player.getTeam());
      }
    }
    return null;
  }

  public TimerEndCondition(Map<String, List<String>> properties, List<PieceInterface> allpieces)
      throws InvalidGameConfigException, InvalidEndGameConfigException {

  }
}
