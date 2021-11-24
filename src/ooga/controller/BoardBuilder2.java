package ooga.controller;

import static java.lang.Integer.parseInt;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.model.EndConditionHandler.EndConditionInterface;
import ooga.model.Piece;
import ooga.model.PieceInterface;
import ooga.model.Player;
import ooga.model.PlayerInterface;
import org.json.JSONArray;
import org.json.JSONObject;

public class BoardBuilder2 implements Builder {

  public static final String DEFAULT_STYLE = "companion";
  public static final int ARG_LENGTH = 4;
  public static final String PROPERTIES_FILE = "JSONMappings";
  public static final String CSV_DELIMETER = "csvDelimeter";

  private ResourceBundle mappings;

  private String gameType;
  private String boardShape;
  private List<Integer> boardSize;
  private List<String> boardColors;
  private String bottomColor;
  private String style;
  private List<List<String>> csvData;
  private List<PlayerInterface> playerList;
  private List<PieceViewBuilder> pieceList;

  private LocationParser locationParser;
  private JsonParser jsonParser;
  private PieceBuilder pieceBuilder;
  private EndConditionInterface endCondition;

  public BoardBuilder2(File defaultFile) {
    mappings = ResourceBundle.getBundle(PROPERTIES_FILE);
    jsonParser = new JsonParser();
    locationParser = new LocationParser();
    pieceList = new ArrayList<>();
    playerList = new ArrayList<>();
    style = DEFAULT_STYLE;
    try {
      build(defaultFile);
    } catch (Exception e) {
      //default file shouldn't have any issues
      e.printStackTrace();
    }

  }

  /**
   * Overridden interface method. builds a pieceinterface which is essentially the grid that holds
   * the pieces of the game.
   *
   * @param file - the file to be parsed and used to build the json object
   * @throws CsvException
   * @throws FileNotFoundException
   * @throws PlayerNotFoundException
   * @throws InvalidPieceConfigException
   * @throws InvalidGameConfigException
   * @throws InvalidEndGameConfigException
   */
  @Override
  public void build(File file)
      throws CsvException, FileNotFoundException, PlayerNotFoundException, InvalidPieceConfigException, InvalidGameConfigException, InvalidEndGameConfigException {
    JSONObject gameJson = jsonParser.loadFile(file);
    extractJSONObj(gameJson);

    pieceBuilder = new PieceBuilder(mappings, gameType,bottomColor);
    iterateCSVData();
    try {
      buildEndConditionHandler(gameJson.getString(mappings.getString("rules")));
    }catch (Exception e){
      throw new InvalidEndGameConfigException(e.getClass());
    }

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
  public EndConditionInterface getEndConditionHandler() {
    return endCondition;
  }

  /**
   * This method takes in a piece and converts it into a converted piece of a different type
   * An example use case for this is in promotion, where a pawn turns into a promoted version of the
   * piece
   * @param piece Piece from the board that will be changed to another kind of piece
   * @param pieceType The kind of piece that this will be changed to
   * @return
   * @throws FileNotFoundException
   * @throws InvalidPieceConfigException
   */
  @Override
  public PieceInterface convertPiece(PieceInterface piece, String pieceType)
      throws FileNotFoundException, InvalidPieceConfigException {
    String pieceTeam = piece.getTeam();
    String[] pieceData = new String[]{pieceTeam, pieceType};
    int pieceRow = piece.getLocation().getRow();
    int pieceColumn = piece.getLocation().getCol();
    Piece newPiece = pieceBuilder.buildPiece(pieceData, pieceRow, pieceColumn);

    return newPiece;
  }
  /**
   * Iterates through the list<list> as given by the csvParser. creates pieces and adds them to the
   * pieceGrid
   */
  private void iterateCSVData()
      throws InvalidPieceConfigException, PlayerNotFoundException, FileNotFoundException {
    for (int r = 0; r < boardSize.get(0); r++) {
      for (int c = 0; c < boardSize.get(1); c++) {
//        String[] square = csvData.get(r).get(c).split(mappings.getString(CSV_DELIMETER));
        String[] square = csvData.get(r).get(c).split("_");

        if (square.length < 2) {
          continue;           //signifies that this square is empty
        }
        Piece piece = pieceBuilder.buildPiece(square, r, c);
        int playerListIdx = determinePlayer(r, c, square[0]);

        pieceList.add(new PieceViewBuilder(piece));
        try {
          playerList.get(playerListIdx).addPiece(piece);
        }catch (Exception e){
          //todo handle exception
        }
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
  private void extractJSONObj(JSONObject jsonObject) throws InvalidGameConfigException {
    try{
      gameType = jsonObject.getString(mappings.getString("type"));
      boardShape = jsonObject.getString(mappings.getString("board"));
      style = jsonObject.getString(mappings.getString("style"));

      boardSize = new ArrayList<>();
      for (String dimension : jsonObject.getString(mappings.getString("boardSize")).split("x")){
        boardSize.add(parseInt(dimension));
      }

      boardColors = convertJSONArrayOfStrings(
          jsonObject.getJSONArray(mappings.getString("boardColors")));

      for (String player : convertJSONArrayOfStrings(
          jsonObject.getJSONArray(mappings.getString("players")))) {
        playerList.add(new Player(player));
      }
      bottomColor = playerList.get(0).getTeam(); //assumes that bottom player is the first player

      String csv = jsonObject.getString(mappings.getString("csv"));
      csvData = locationParser.getInitialLocations(csv);

    }catch (Exception e){
      throw new InvalidGameConfigException();
    }
  }

  private void buildEndConditionHandler(String ruleJsonFile)
      throws FileNotFoundException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, InvalidGameConfigException {
    JSONObject rules = jsonParser.loadFile(new File(ruleJsonFile));
    String type = rules.getString(mappings.getString("gameType"));
    String[] keys = mappings.getString(type+"RuleKeys").split(mappings.getString("jsonDelimiter"));
    Map<String,List<String>> endConditionProperties = new HashMap<>();
    for (String key : keys){
      endConditionProperties.put(key, convertJSONArrayOfStrings(rules.getJSONArray(key)));
    }
    Class<?> clazz = Class.forName("ooga.model.EndConditionHandler." + type + "EndCondition");
    endCondition = (EndConditionInterface) clazz.getDeclaredConstructor().newInstance();
    List<PieceInterface> initialPieces = new ArrayList<>();
    for (PlayerInterface p : playerList){
      initialPieces.addAll(p.getPieces());
    }
    endCondition.setArgs(endConditionProperties, initialPieces);

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