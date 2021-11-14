package ooga.controller;

import static java.lang.Integer.parseInt;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.model.Vector;
import ooga.model.PieceInterface;
import org.json.JSONArray;
import org.json.JSONObject;

public class BoardBuilder implements Builder {
  public static final String DEFAULT_STYLE = "companion";

  private String gameType;
  private String boardShape;
  private List<Integer> boardSize;
  private List<String> boardColors;
  private List<String> players;
  private String bottomColor;
  private String rules;
  private String csv;
  private List<List<String>> csvData;
  private PieceInterface[][] pieceGrid;

  private LocationParser locationParser;
  private JsonParser jsonParser;

  public BoardBuilder() {
    locationParser = new LocationParser();
    jsonParser = new JsonParser();
  }


  /**
   * Overridden interface method. builds a pieceinterface which is essentially the grid
   * that holds the pieces of the game.
   * @param jsonObject - parsed .json file
   * @returns - a pieceinterface grid
   * @throws Exception - if the csv file in the JSONObject isn't valid
   */
  @Override
  public PieceInterface[][] build(JSONObject jsonObject) throws Exception {
    extractJSONObj(jsonObject);
    pieceGrid = new PieceInterface[boardSize.get(0)][boardSize.get(1)];
    csvData = locationParser.getInitialLocations(csv);
    iterateCSVData(csvData);
    return pieceGrid;
  }

  /**
   * Iterates through the list<list> as given by the csvParser. creates pieces and adds them to the
   * pieceGrid
   * @param csvData - list of list representing the locations of the pieces
   */
  private void iterateCSVData(List<List<String>> csvData) {
    for (int r = 0; r < boardSize.get(0); r++) {
      for (int c = 0; c < boardSize.get(1); c++) {
        String[] square = this.csvData.get(r).get(c).split("_");
        if (square.length < 2){
          continue;
        }
        String pieceColor = square[0];
        String pieceType = square[1];
        String pieceImagePath = "src/images/"+DEFAULT_STYLE+"/"+ pieceColor + pieceType + ".png";
        String pieceJsonPath = "data/"+gameType+"/pieces/"+pieceType+".json";
        JSONObject pieceJSON = jsonParser.loadFile(new File(pieceJsonPath));
        JSONObject attributes = pieceJSON.getJSONObject("attributes");
        JSONObject moveVectors = pieceJSON.getJSONObject("moveVectors");

//        Piece piece = new Piece(pieceColor, getMoveVectors(moveVectors,pieceColor), getAttributes(attributes), pieceImagePath);
//        pieceGrid[r][c] = piece;
      }

    }
  }

  /**
   * @param attributes - JSON object that contains a bunch of attribute field values
   * @return a map of all the attributes and their values
   */
  private Map<String, Boolean> getAttributes(JSONObject attributes) {
    Map<String, Boolean> map = new HashMap<>();
    for (String attribute : attributes.keySet()){
      map.put(attribute, attributes.getBoolean(attribute));
    }

    return map;
  }

  /**
   * @param moveVectors - JSONObject which contains a bunch Lists<lists> that represent movement vectors
   * @return a map of the movement vector type (ie takeMoveVectors) to a list<list>
   */
  private Map<String,List<Vector>> getMoveVectors(JSONObject moveVectors, String color) {
    Map<String,List<Vector>> map = new HashMap<>();
    for (String vectorType : moveVectors.keySet()){
      JSONArray jsonArray = moveVectors.getJSONArray(vectorType);
      List<Vector> vectors = extractMoveVectors(jsonArray, color);
      map.put(vectorType,vectors);
    }
    return map;
  }

  /**
   * @param jsonArray Takes a jsonArray of strings that represent 1 type of movement vector (ie takeMove).
   * @return ret - List<List<Integer>> version of the jsonArrays
   */
  private List<Vector> extractMoveVectors(JSONArray jsonArray, String color) {
    List<Vector> ret = new ArrayList<>();

    for (int i = 0; i<jsonArray.length();i++){
      String[] splitString = jsonArray.getString(i).split(",");
      int row = parseInt(splitString[0]);
      int col = parseInt(splitString[1]);
      if (color.equals(bottomColor)){
        row = row * -1;
      }

      ret.add(new Vector(row,col));
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
    List<String> a = Arrays.asList(jsonObject.getString("boardSize").split("x"));
    a.forEach((num) -> boardSize.add(parseInt(num)));
    boardColors = extractColors(jsonObject.getJSONArray("boardColors"));
    players = extractColors(jsonObject.getJSONArray("players"));
    bottomColor = players.get(0); //assumes that bottom player is the first given player color
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
