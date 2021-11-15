package ooga.controller;

import java.util.List;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;
import org.json.JSONObject;

public interface Builder {
  void build(JSONObject jsonObject) throws Exception;
  public List<PieceInterface> getInitialPieces();

  public List<PlayerInterface> getInitialPlayers();

}
