package ooga.model.EndConditionHandler;

import java.util.List;
import java.util.Map;
import ooga.controller.InvalidEndGameConfigException;
import ooga.controller.InvalidGameConfigException;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

public class TimerEndCondition implements EndConditionInterface{

  List<PlayerInterface> players;

  @Override
  public String foundWinner(List<PlayerInterface> players) {
    this.players = players;

    for (PlayerInterface player : players){
      System.out.println(player.getTeam() + player.getTimeLeft());
      if (player.getTimeLeft().getValue().equals("00:00")){
        return findWinner(player.getTeam());
      }
    }
    return null;
  }

  private String findWinner(String losingTeam) {
    for (PlayerInterface player : players){
      if (player.getTeam() != losingTeam){
        return player.getTeam();
      }
    }
    return null;
  }

  @Override
  public void setArgs(Map<String, List<String>> properties, List<PieceInterface> allpieces)
      throws InvalidGameConfigException, InvalidEndGameConfigException {

  }
}
