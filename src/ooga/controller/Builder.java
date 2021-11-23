package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import ooga.model.EndConditionHandler.EndConditionInterface;
import ooga.model.PlayerInterface;

public interface Builder {
  void build(File file)
      throws CsvException, FileNotFoundException, PlayerNotFoundException, InvalidPieceConfigException, InvalidGameConfigException, InvalidEndGameConfigException;
  List<PieceViewBuilder> getInitialPieceViews();

  List<PlayerInterface> getInitialPlayers();

  EndConditionInterface getEndConditionHandler();
}
