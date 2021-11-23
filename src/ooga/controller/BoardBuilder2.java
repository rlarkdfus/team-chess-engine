package ooga.controller;

import static java.lang.Integer.parseInt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.model.Piece;
import ooga.model.Player;
import ooga.model.PlayerInterface;
import org.json.JSONArray;
import org.json.JSONObject;

public class BoardBuilder2 implements Builder {

  public static final String DEFAULT_STYLE = "companion";
  public static final int ARG_LENGTH = 4;
  public static final String PROPERTIES_FILE = "JSONMappings";
  private ResourceBundle mappings;


  private String gameType;
  private String boardShape;
  private List<Integer> boardSize;
  private List<String> boardColors;
  private List<String> players;
  private String bottomColor;
  private String style;
  private String csv;
  private List<List<String>> csvData;
  private List<PlayerInterface> playerList;
  private List<PieceViewBuilder> pieceList;

  private LocationParser locationParser;
  private JsonParser jsonParser;
  private PieceBuilder pieceBuilder;

//  private EndConditionHandler endConditionHandler;

  public BoardBuilder2(File defaultFile) {
    mappings = ResourceBundle.getBundle(PROPERTIES_FILE);
    jsonParser = new JsonParser();
    locationParser = new LocationParser();
    pieceList = new ArrayList<>();
    playerList = new ArrayList<>();
    style = DEFAULT_STYLE;
    try {
      build(defaultFile);
    } catch (FileNotFoundException | CsvException | PlayerNotFoundException | InvalidPieceConfigException | InvalidGameConfigException e) {
      //default file shouldn't have any issues
      e.printStackTrace();
    }

  }


  /**
   * Overridden interface method. builds a pieceinterface which is essentially the grid that holds
   * the pieces of the game.
   *
   * @param file - the file to be parsed and used to build the json object
   * @throws Exception - if the csv file in the JSONObject isn't valid
   * @returns - a pieceinterface grid
   */
  @Override
  public void build(File file)
      throws CsvException, FileNotFoundException, PlayerNotFoundException, InvalidPieceConfigException, InvalidGameConfigException {
    JSONObject jsonObject = jsonParser.loadFile(file);
    try {
      extractJSONObj(jsonObject);
    } catch (Exception e) {
      throw new InvalidGameConfigException();
    }
    for (String player : players) {
      playerList.add(new Player(player));
    }
    csvData = locationParser.getInitialLocations(csv);
    pieceBuilder = new PieceBuilder(mappings, gameType,bottomColor);
    iterateCSVData();
//    try {
//      buildEndConditionHandler(jsonObject.getString(mappings.getString("rules")));
//    }catch (Exception e){
//      throw new InvalidGameConfigException();
//    }

  }

  @Override
  public List<PieceViewBuilder> getInitialPieceViews() {
    return pieceList;
  }

  @Override
  public List<PlayerInterface> getInitialPlayers() {
    return playerList;
  }

  @Override
  public void getEndConditionHandler() {
//    return endConditionHandler;
  }
  /**
   * Iterates through the list<list> as given by the csvParser. creates pieces and adds them to the
   * pieceGrid
   */
  private void iterateCSVData()
      throws InvalidPieceConfigException, PlayerNotFoundException, FileNotFoundException {
    for (int r = 0; r < boardSize.get(0); r++) {
      for (int c = 0; c < boardSize.get(1); c++) {
        String[] square = csvData.get(r).get(c).split(mappings.getString("csvDelimiter"));
        if (square.length < 2) {
          //signifies that this square is empty
          continue;
        }

        Piece piece = pieceBuilder.buildPiece(square, r, c);
        int playerListIdx = determinePlayer(r, c, square[0]);

        pieceList.add(new PieceViewBuilder(piece));
        playerList.get(playerListIdx).addPiece(piece);
      }
    }
  }



  private int determinePlayer(int r, int c, String team) throws PlayerNotFoundException {
    int playerListIdx = -1;
    for (PlayerInterface p : playerList) {
      if (p.getTeam().equals(team)) {
        playerListIdx = playerList.indexOf(p);
      }
    }
    if (playerListIdx < 0) {
      throw new PlayerNotFoundException(r, c, team);
    }
    return playerListIdx;
  }



  /**
   * sets the instance variables to the values given by the inputted json object
   *
   * @param jsonObject - jsonobject representation of the .json file
   */
  private void extractJSONObj(JSONObject jsonObject)
      throws FileNotFoundException, ClassNotFoundException, NoSuchMethodException {
    gameType = jsonObject.getString(mappings.getString("type"));
    boardShape = jsonObject.getString(mappings.getString("board"));
    style = jsonObject.getString(mappings.getString("style"));
    boardSize = new ArrayList<>();
    List<String> a = Arrays.asList(jsonObject.getString(mappings.getString("boardSize")).split("x"));
    a.forEach((num) -> boardSize.add(parseInt(num)));
    boardColors = convertJSONArrayOfStrings(jsonObject.getJSONArray(mappings.getString("boardColors")));
    players = convertJSONArrayOfStrings(jsonObject.getJSONArray(mappings.getString("players")));
    bottomColor = players.get(0); //assumes that bottom player is the first given player color
    csv = jsonObject.getString(mappings.getString("csv"));
  }

  private void buildEndConditionHandler(String ruleJsonFile)
      throws FileNotFoundException, ClassNotFoundException, NoSuchMethodException {
    JSONObject rules = jsonParser.loadFile(new File(ruleJsonFile));
    String type = rules.getString(mappings.getString("type"));
    List<String> keys = List.of(mappings.getString(type).split("jsonDelimiter"));
    Map<String,List<String>> endConditionProperties = new HashMap<>();
    for (String key : keys){
      endConditionProperties.put(key, convertJSONArrayOfStrings(rules.getJSONArray(key)));
    }
    Class<?> clazz = Class.forName("ooga.model.EndConditionHandler." + type);
//    endConditionHandler = (EndConditionHandler) clazz.getDeclaredConstructor().newInstance();
//    endConditionHandler.setArgs(endConditionProperties);

  }

  /**
   * @param jsonArray - jsonarray of strings that represent colors of the board/players;
   * @return - a List of strings version of the jsonarray
   */
  private List<String> convertJSONArrayOfStrings(JSONArray jsonArray) {
    List<String> ret = new ArrayList<>();
    for (int i = 0; i < jsonArray.length(); i++) {
      ret.add(jsonArray.getString(i));
    }
    return ret;
  }
}