package ooga.controller;

import java.util.List;
import ooga.model.PlayerInterface;
import org.json.JSONObject;

public interface Builder {
  void build(JSONObject jsonObject) throws Exception;
  public List<PieceViewBuilder> getInitialPieceViews();

  public List<PlayerInterface> getInitialPlayers();

}
