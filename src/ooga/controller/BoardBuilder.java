package ooga.controller;

import static java.lang.Integer.parseInt;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.model.Builder;
import ooga.model.Engine;
import ooga.model.Piece;
import ooga.model.PieceInterface;
import org.json.JSONArray;
import org.json.JSONObject;

public class BoardBuilder implements Builder {

  String gameType;
  String boardShape;
  List<Integer> boardSize;
  List<String> boardColors;
  List<String> players;
  String rules;
  String csv;
  List<List<String>> csvData;
  PieceInterface[][] pieceGrid;

  //  LocationParser locationParser;
  JsonParser jsonParser;
  public BoardBuilder() {
//    locationParser = new LocationParser();
    jsonParser = new JsonParser();
  }

  @Override
  public Engine buildEngine() {
    return null;
  }

  public void build(JSONObject jsonObject) {
    extractJSONObj(jsonObject);
    pieceGrid = new PieceInterface[boardSize.get(0)][boardSize.get(1)];
//    csvData = locationParser.getInitialLocations(csv);
    iterateCSVData();
  }

  private void iterateCSVData() {
    for (int r = 0; r < boardSize.get(0); r++) {
      for (int c = 0; c < boardSize.get(1); c++) {
        String[] square = csvData.get(r).get(c).split("_");
        String pieceColor = square[0];
        String pieceType = square[1];
        String pieceImagePath = "data/" + gameType + "/images/" + pieceColor + "_" + pieceType + ".png";
        JSONObject pieceJSON = jsonParser.loadFile(new File("data/"+gameType+"/pieces/"+pieceType+".json"));
        JSONObject attributes = pieceJSON.getJSONObject("attributes");
        JSONObject moveVectors = pieceJSON.getJSONObject("moveVectors");

        Piece piece = new Piece(pieceColor,getMoveVectors(moveVectors), getAttributes(attributes), pieceImagePath);
        pieceGrid[r][c] = piece;
      }

    }
  }

  private Map<String, Boolean> getAttributes(JSONObject attributes) {
    Map<String, Boolean> map = new HashMap<>();
    for (String attribute : attributes.keySet()){
      map.put(attribute, attributes.getBoolean(attribute));
    }

    return map;
  }

  /**
   * @param moveVectors - JSONObject which contains a bunch Lists<lists> that represent movement vectors
   * @return a map of the movement vector type (ie takeMoveVectors) to a list<list> that represents movement vectors
   */
  private Map<String,List<List<Integer>>> getMoveVectors(JSONObject moveVectors) {
    Map<String,List<List<Integer>>> map = new HashMap<>();
    for (String vectorType : moveVectors.keySet()){
      JSONArray jsonArray = moveVectors.getJSONArray(vectorType);
      List<List<Integer>> vectors = extractMoveVectors(jsonArray);
      map.put(vectorType,vectors);
    }
    return map;
  }

  /**
   * @param jsonArray Takes a jsonArray which has nested jsonArrays that represent movement vectors.
   * @return ret - List<List<Integer>> version of the jsonArrays
   */
  private List<List<Integer>> extractMoveVectors(JSONArray jsonArray) {
    List<List<Integer>> ret = new ArrayList<>();

    for (int i = 0; i<jsonArray.length();i++){
      JSONArray nestedArray = jsonArray.getJSONArray(i);
      List<Integer> newList = new ArrayList<>();
      for (int j = 0; j<nestedArray.length();j++){
        newList.add(nestedArray.getInt(j));
      }
      ret.add(newList);
    }
    return ret;
  }

  /**
   * sets the instance variables to the values given by the inputted json object
   * @param jsonObject - jsonobject representation of the .json file
   */
  private void extractJSONObj(JSONObject jsonObject) {
    gameType = jsonObject.getString("type");
    boardShape = jsonObject.getString("board");
    boardSize = new ArrayList<>();
    List<String> a = Arrays.asList(jsonObject.getString("boardSize").split(","));
    a.forEach((num) -> boardSize.add(parseInt(num)));
    boardColors = extractColors(jsonObject.getJSONArray("boardColors"));
    players = extractColors(jsonObject.getJSONArray("players"));
    rules = jsonObject.getString("rules");
    csv = jsonObject.getString("csv");
  }

  /**
   * @param jsonArray - jsonarray of strings that represent colors of the board/players;
   * @return - a List of strings version of the jsonarray
   */
  private List<String> extractColors(JSONArray jsonArray) {
    List<String> ret = new ArrayList<>();
    for (int i = 0; i < jsonArray.length(); i++) {
      ret.add(jsonArray.getString(i));
    }
    return ret;
  }


}
