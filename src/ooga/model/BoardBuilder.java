package ooga.model;

import static java.lang.Integer.parseInt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class BoardBuilder implements Builder{

  String gameType;
  String boardShape;
  List<Integer> boardSize;
  List<String> boardColors;
  String rules;
  String csv;
  List<List<String>> csvData;
  PieceInterface[][] pieceGrid;

//  LocationParser locationParser;
  public BoardBuilder() {
//    locationParser = new LocationParser();

  }

  @Override
  public Engine buildEngine() {
    return null;
  }

  public void build(JSONObject jsonObject) {
    extractJSONObj(jsonObject);
    pieceGrid = new PieceInterface[boardSize.get(0)][boardSize.get(1)];
//    locationParser.getInitialLocations(csv);
  }

  private void extractJSONObj(JSONObject jsonObject) {
    gameType = jsonObject.getString("type");
    boardShape = jsonObject.getString("board");
    boardSize = new ArrayList<>();
    List<String> a = Arrays.asList(jsonObject.getString("boardSize").split(","));
    a.forEach((num) -> boardSize.add(parseInt(num)));
    boardColors = JSONtoList(jsonObject.getJSONArray("boardColors"));
    rules = jsonObject.getString("rules");
    csv = jsonObject.getString("csv");
  }

  private List<String> JSONtoList(JSONArray jsonArray) {
    List<String> ret = new ArrayList<>();
    for (int i = 0; i < jsonArray.length(); i++) {
      ret.add(jsonArray.getString(i));
    }
    return ret;
  }


}
