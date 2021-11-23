package ooga.model;

import java.util.List;
import java.util.Map;
import ooga.controller.InvalidGameConfigException;

public interface EndConditionInterface {
  boolean isGameOver(List<PieceInterface> pieces);
  void setArgs(Map<String, List<String>> properties, List<PieceInterface> allpieces)
      throws InvalidGameConfigException;
}
