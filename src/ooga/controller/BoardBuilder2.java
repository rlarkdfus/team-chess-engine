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
import ooga.Location;
import ooga.model.Moves.Move;
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
  private String rules;
  private String csv;
  private List<List<String>> csvData;
  private List<PlayerInterface> playerList;
  private List<PieceViewBuilder> pieceList;

  private LocationParser locationParser;
  private JsonParser jsonParser;

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
    iterateCSVData();
  }

  @Override
  public List<PieceViewBuilder> getInitialPieceViews() {
    return pieceList;
  }

  @Override
  public List<PlayerInterface> getInitialPlayers() {
    return playerList;
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

        int playerListIdx = determinePlayer(r, c, square[0]);
        Piece piece = buildPiece(r, c);
        pieceList.add(new PieceViewBuilder(piece));
        playerList.get(playerListIdx).addPiece(piece);
      }
    }
  }

  private Piece buildPiece(int r, int c) throws FileNotFoundException, InvalidPieceConfigException {
    String[] square = csvData.get(r).get(c).split(mappings.getString("csvDelimiter"));
    String team = square[0];
    String pieceName = square[1];
    Location location = new Location(r, c);

    String pieceJsonPath = "data/" + gameType + "/pieces/" + pieceName + ".json";
    JSONObject pieceJSON = jsonParser.loadFile(new File(pieceJsonPath));

    List<Move> moves;
    Map<String, Boolean> attributes;
    int value;
    String errorKey = null;
    try {
      errorKey = mappings.getString("moves");
      moves = getMoves(pieceJSON, team);
      errorKey = mappings.getString("attributes");
      attributes = getAttributes(pieceJSON);
      errorKey = mappings.getString("value");
      value = pieceJSON.getInt(mappings.getString("value"));
    } catch (Throwable e) {
      throw new InvalidPieceConfigException(r, c, pieceJsonPath, errorKey);
    }
    return new Piece(team, pieceName, location, moves, attributes, value);

  }


  private List<Move> getMoves(JSONObject pieceObj, String team) throws Throwable {
    JSONObject moveTypes = pieceObj.getJSONObject(mappings.getString("moves"));
    List<Move> moveList = new ArrayList<>();
    for (String moveType : moveTypes.keySet()) {
      List<Move> newMoves = makeTypeOfMove(moveType, (JSONArray) moveTypes.get(moveType), team);
      moveList.addAll(newMoves);
    }
    return moveList;
  }

  private List<Move> makeTypeOfMove(String moveType, JSONArray arguments, String team)
      throws Throwable {
    List<Move> moves = new ArrayList<>();
    for (int i = 0; i < arguments.length(); i++) {
      Move newMove = makeNewMove(moveType);
      if (newMove == null) {
        continue;
      }
      setMoveArgs(team, newMove, arguments.getString(i));
      moves.add(newMove);
    }
    return moves;
  }

  private Move makeNewMove(String moveType) throws Throwable {
    Class<?> clazz = Class.forName("ooga.model.Moves." + moveType);
    Move newMove = (Move) clazz.getDeclaredConstructor().newInstance();
    return newMove;
  }

  private void setMoveArgs(String team, Move newMove, String arg) throws Exception {
    String[] args = arg.split(mappings.getString("jsonDelimiter"));
    if (args.length == ARG_LENGTH) {
      int dRow = team.equals(bottomColor) ? -parseInt(args[0]) : parseInt(args[0]);
      int dCol = parseInt(args[1].strip());
      boolean takes = args[3].strip().equals(mappings.getString("takes"));
      boolean limited = args[3].strip().equals(mappings.getString("limited"));
      newMove.setMove(dRow, dCol, takes, limited);
    } else {
      throw new Exception();
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
   * @param pieceJSON - JSON object of the piece
   * @return a map of all the attributes and their values
   */
  private Map<String, Boolean> getAttributes(JSONObject pieceJSON) {
    JSONObject attributes = pieceJSON.getJSONObject(mappings.getString("attributes"));
    Map<String, Boolean> map = new HashMap<>();
    for (String attribute : attributes.keySet()) {
      map.put(attribute, attributes.getBoolean(attribute));
    }

    return map;
  }

  /**
   * sets the instance variables to the values given by the inputted json object
   *
   * @param jsonObject - jsonobject representation of the .json file
   */
  private void extractJSONObj(JSONObject jsonObject) {
    gameType = jsonObject.getString(mappings.getString("type"));
    boardShape = jsonObject.getString(mappings.getString("board"));
    style = jsonObject.getString(mappings.getString("style"));
    boardSize = new ArrayList<>();
    List<String> a = Arrays.asList(jsonObject.getString(mappings.getString("boardSize")).split("x"));
    a.forEach((num) -> boardSize.add(parseInt(num)));
    boardColors = extractColors(jsonObject.getJSONArray(mappings.getString("boardColors")));
    players = extractColors(jsonObject.getJSONArray(mappings.getString("players")));
    bottomColor = players.get(0); //assumes that bottom player is the first given player color
    rules = jsonObject.getString(mappings.getString("rules"));
    csv = jsonObject.getString(mappings.getString("csv"));
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