package ooga.model.EndConditionHandler;

import java.util.List;
import ooga.model.GameState;
import ooga.model.PlayerInterface;

public interface EndConditionInterface {
  GameState isSatisfied(List<PlayerInterface> players);
}
