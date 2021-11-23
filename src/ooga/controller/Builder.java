package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import ooga.model.EndConditionHandler.EndConditionInterface;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

public interface Builder {
  void build(File file)
      throws CsvException, FileNotFoundException, PlayerNotFoundException, InvalidPieceConfigException, InvalidGameConfigException, InvalidEndGameConfigException;
  List<PieceViewBuilder> getInitialPieceViews();

  List<PlayerInterface> getInitialPlayers();

  EndConditionInterface getEndConditionHandler();

  PieceInterface convertPiece(PieceInterface piece, String pieceType)
      throws FileNotFoundException, InvalidPieceConfigException;
}
