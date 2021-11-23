package ooga.model.EndConditionHandler;

import java.util.List;
import java.util.Map;
import ooga.controller.InvalidGameConfigException;
import ooga.model.PieceInterface;

public interface EndConditionInterface {
  boolean isGameOver(List<PieceInterface> pieces);
  void setArgs(Map<String, List<String>> properties, List<PieceInterface> allpieces)
      throws InvalidGameConfigException;
  String getWinner();
}
