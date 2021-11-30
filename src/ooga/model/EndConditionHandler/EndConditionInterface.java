package ooga.model.EndConditionHandler;

import java.util.List;
import java.util.Map;
import ooga.controller.InvalidEndGameConfigException;
import ooga.controller.InvalidGameConfigException;
import ooga.model.Board.GameState;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

public interface EndConditionInterface {
  GameState isGameOver(List<PlayerInterface> players);
  void setArgs(Map<String, List<String>> properties, List<PieceInterface> allpieces)
      throws InvalidGameConfigException, InvalidEndGameConfigException;
  String getWinner();
}
