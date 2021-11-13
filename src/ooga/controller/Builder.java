package ooga.controller;

import ooga.model.PieceInterface;
import org.json.JSONObject;

public interface Builder {
  PieceInterface[][] build(JSONObject jsonObject);
}
