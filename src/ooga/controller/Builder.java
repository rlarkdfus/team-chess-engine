package ooga.controller;

import java.util.List;
import ooga.model.PlayerInterface;
import ooga.view.PieceView;
import org.json.JSONObject;

public interface Builder {
  void build(JSONObject jsonObject) throws Exception;
  public PieceView[][] getInitialBoardView(String style);

  public List<PlayerInterface> getInitialPlayers();

}
