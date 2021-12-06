package ooga.model.EndConditionHandler;

import java.util.List;
import java.util.Map;
import ooga.controller.Config.InvalidEndGameConfigException;
import ooga.controller.Config.InvalidGameConfigException;
import ooga.model.GameState;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

public class TimerEndCondition implements EndConditionInterface{

  public static final String OUT_OF_TIME = "00:00";

  @Override
  public GameState isSatisfied(List<PlayerInterface> players) {
    for (PlayerInterface player : players){
      if (player.getTimeLeft().getValue().equals(OUT_OF_TIME)){
        return GameState.ENDED.getLoser(player.getTeam());
      }
    }
    return null;
  }

  public TimerEndCondition(Map<String, List<String>> properties, List<PieceInterface> allpieces)
      throws InvalidGameConfigException, InvalidEndGameConfigException {

  }
}
