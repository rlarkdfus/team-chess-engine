package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import ooga.model.PlayerInterface;

public interface Builder {
  void build(File file)
      throws CsvException, FileNotFoundException, PlayerNotFoundException, InvalidPieceConfigException, InvalidGameConfigException;
  List<PieceViewBuilder> getInitialPieceViews();

  List<PlayerInterface> getInitialPlayers();

  void getEndConditionHandler();
}
