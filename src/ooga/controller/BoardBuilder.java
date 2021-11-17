package ooga.controller;

import static java.lang.Integer.parseInt;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.Location;
import ooga.model.MoveVector;
import ooga.model.Piece;
import ooga.model.PieceInterface;
import ooga.model.Player;
import ooga.model.PlayerInterface;
import ooga.model.Vector;
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
  private String style;
  private String rules;
  private String csv;
  private List<List<String>> csvData;
  private List<PlayerInterface> playerList;
  private List<PieceViewBuilder> pieceList;

  private LocationParser locationParser;
  private JsonParser jsonParser;

  public BoardBuilder(File file) {
    jsonParser = new JsonParser();
    locationParser = new LocationParser();
    pieceList = new ArrayList<>();
    playerList = new ArrayList<>();
    style = DEFAULT_STYLE;

    try {
      build(jsonParser.loadFile(file));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Overridden interface method. builds a pieceinterface which is essentially the grid
   * that holds the pieces of the game.
   * @param jsonObject - parsed .json file
   * @returns - a pieceinterface grid
   * @throws Exception - if the csv file in the JSONObject isn't valid
   */
  @Override
  public void build(JSONObject jsonObject) throws Exception {
    extractJSONObj(jsonObject);
    for (String player : players){
      playerList.add(new Player(player));
    }
    csvData = locationParser.getInitialLocations(csv);
    iterateCSVData();
  }

  @Override
  public List<PieceViewBuilder> getInitialPieceViews(){
    if (!this.style.equals(style)){
      //make new piece list w style
    }
    return pieceList;
  }

  @Override
  public List<PlayerInterface> getInitialPlayers(){
    return playerList;
  }

  /**
   * Iterates through the list<list> as given by the csvParser. creates pieces and adds them to the
   * pieceGrid
   */
  private void iterateCSVData() throws Exception {
    for (int r = 0; r < boardSize.get(0); r++) {
      for (int c = 0; c < boardSize.get(1); c++) {
        String[] square = csvData.get(r).get(c).split("_");
        if (square.length < 2){           //signifies that this square is empty
          continue;
        }

        String team = square[0];
        String pieceName = square[1];
        Location location = new Location(r,c);

        int playerListIdx = determinePlayer(team);

        String pieceJsonPath = "data/"+gameType+"/pieces/"+pieceName+".json";
        JSONObject pieceJSON = jsonParser.loadFile(new File(pieceJsonPath));

        MoveVector moveVector = getMoveVector(pieceJSON, team);
        Map<String, Boolean> attributes = getAttributes(pieceJSON);

        Piece piece = new Piece(team, pieceName, location, moveVector, attributes);
        pieceList.add(new PieceViewBuilder(piece));
        playerList.get(playerListIdx).addPiece(piece);
      }
    }
  }

  private int determinePlayer(String team) throws Exception {
    int playerListIdx = -1;
    for (PlayerInterface p : playerList){
      if (p.getTeam().equals(team)){
        playerListIdx = playerList.indexOf(p);
      }
    }
    if (playerListIdx < 0){
      //todo: handle exception
      throw new Exception();
    }
    return playerListIdx;
  }

  private MoveVector getMoveVector(JSONObject pieceJSON, String teamColor) {
    Map<String, List<Vector>> map = mapOfMoveVectors(pieceJSON.getJSONObject("moveVectors"),teamColor);
    List<Vector> moves = map.get("moves");
    List<Vector> takeMoves = map.get("takeMoves");
    List<Vector> initialMoves = map.get("initialMoves");

    return new MoveVector(moves,takeMoves,initialMoves);
  }

  /**
   * @param pieceJSON - JSON object of the piece
   * @return a map of all the attributes and their values
   */
  private Map<String, Boolean> getAttributes(JSONObject pieceJSON) {
    JSONObject attributes = pieceJSON.getJSONObject("attributes");
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
  private Map<String,List<Vector>> mapOfMoveVectors(JSONObject moveVectors, String color) {
    Map<String,List<Vector>> map = new HashMap<>();
    for (String vectorType : moveVectors.keySet()){
      JSONArray jsonArray = moveVectors.getJSONArray(vectorType);
      List<Vector> vectors = jsonarrayToVectorList(jsonArray, color);
      map.put(vectorType,vectors);
    }
    return map;
  }

  /**
   * @param jsonArray Takes a jsonArray of strings that represent 1 type of movement vector (ie takeMove).
   * @return ret - List<List<Integer>> version of the jsonArrays
   */
  private List<Vector> jsonarrayToVectorList(JSONArray jsonArray, String color) {
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
    style = jsonObject.getString("style");
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

  public class PieceViewBuilder {

    private String team;
    private String name;
    private Location location;

    public PieceViewBuilder(Piece piece) {
      this.team = piece.getTeam();
      this.name = piece.getName();
      this.location = piece.getLocation();
    }

    public String getTeam() {
      return team;
    }

    public String getName() {
      return name;
    }

    public Location getLocation() {
      return location;
    }
  }
}
