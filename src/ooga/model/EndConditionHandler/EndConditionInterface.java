package ooga.model.EndConditionHandler;

import java.util.List;
import java.util.Map;
import ooga.controller.InvalidEndGameConfigException;
import ooga.controller.InvalidGameConfigException;
import ooga.model.GameState;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

public interface EndConditionInterface {
  GameState isSatisfied(List<PlayerInterface> players);
}
