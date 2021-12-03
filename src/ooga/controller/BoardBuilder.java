package ooga.controller;

import static java.lang.Integer.parseInt;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import ooga.model.EndConditionHandler.EndConditionRunner;
import ooga.model.Piece;
import ooga.model.Player;
import ooga.model.PlayerInterface;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @Authors Albert, Luis
 *
 * This class builds all the in-game specific objects that the game uses to run.
 * We assume that the file is .json since there is a filter when a file is chosen.
 * Users will call .build(File) with their file, and then use the 3 getter methods:
 *  getInitialPieceViews, getInitialPlayers, and getEndConditionHandler
 */
public class BoardBuilder implements Builder {

  public static final int ARG_LENGTH = 4;
  public static final String PROPERTIES_FILE = "JSONMappings";
  public static final String CSV_DELIMITER = "csvDelimiter";
  public static final String RULES = "rules";
  public static final String TYPE = "type";
  public static final String BOARD = "board";

  private final ResourceBundle mappings;

  private String gameType;
  private String boardShape;
  private List<Integer> boardSize;
  private List<String> boardColors;
  private String bottomColor;
  private List<List<String>> csvData;
  private List<PlayerInterface> playerList;
  private List<PieceViewBuilder> pieceList;

  private final LocationParser locationParser;
  private final JsonParser jsonParser;
  private PieceBuilder pieceBuilder;
  private EndConditionRunner endCondition;

  /**
   * Constructor for the BoardBuilder Object.
   * Automatically parses and builds objects for the file given as a parameter
   * @param defaultFile - a default file object that's defined in controller
   */
  public BoardBuilder(File defaultFile) {
    mappings = ResourceBundle.getBundle(PROPERTIES_FILE);
    jsonParser = new JsonParser();
    locationParser = new LocationParser();
    try {
      build(defaultFile);
    } catch (Exception e) {
      //default file shouldn't have any issues
      e.printStackTrace();
    }
  }

  /**
   * Overridden interface method. First parses the input file then builds 3 things that will be used:
   *  playerList - a list of players that is used in Board
   *  pieceList - a list of PieceViewBuilder objects that is used in View to build PieceView Objects
   *  endCondition - an EndConditionHandler Object that is used by Board to determine if the game is over
   *
   * @param file - the file to be parsed and used to build the json object
   * @throws CsvException - if the game's csv isn't 8x8
   * @throws FileNotFoundException - if any files referenced in the game json aren't existent
   * @throws PlayerNotFoundException - if any pieces in the json are assigned to nonexistent players
   * @throws InvalidPieceConfigException - if any piece configuration jsons are invalid
   * @throws InvalidGameConfigException - if the game json is invalid
   * @throws InvalidEndGameConfigException - if the rule json is invalid
   */
  @Override
  public void build(File file)
      throws CsvException, FileNotFoundException, PlayerNotFoundException, InvalidPieceConfigException, InvalidGameConfigException, InvalidEndGameConfigException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    pieceList = new ArrayList<>();
    playerList = new ArrayList<>();
    JSONObject gameJson = jsonParser.loadFile(file);
    extractJSONObj(gameJson);

    pieceBuilder = new PieceBuilder(mappings, gameType,bottomColor);
    EndConditionBuilder endConditionBuilder= new EndConditionBuilder(jsonParser);
    iterateCSVData();
    endCondition = endConditionBuilder.getEndConditions(gameJson.getString(RULES),playerList);
  }

  /**
   * Overridden interface getter method.
   * @return a list of pieceViewBuilder objects which contain all the information to build PieceView
   * objects
   */
  @Override
  public List<PieceViewBuilder> getInitialPieceViews() {
    return pieceList;
  }

  /**
   * Overridden interface getter method.
   * @return a list of players which are used in the model run the game
   * objects
   */
  @Override
  public List<PlayerInterface> getInitialPlayers() {
    return playerList;
  }

  /**
   * Overridden interface getter method.
   * @return an EndConditionInterface Object which is used by the model to determine when the game is over
   */
  @Override
  public EndConditionRunner getEndConditionHandler() {
    return endCondition;
  }

//  /**
//   * This method takes in a piece and converts it into a converted piece of a different type
//   * An example use case for this is in promotion, where a pawn turns into a promoted version of the
//   * piece
//   * @param piece from the board that will be changed to another kind of piece
//   * @param pieceType The kind of piece that this will be changed to
//   * @return -
//   * @throws FileNotFoundException
//   * @throws InvalidPieceConfigException
//   */
//  @Override
//  public PieceInterface convertPiece(PieceInterface piece, String pieceType)
//      throws FileNotFoundException, InvalidPieceConfigException {
//    String pieceTeam = piece.getTeam();
//    String[] pieceData = new String[]{pieceTeam, pieceType};
//    int pieceRow = piece.getLocation().getRow();
//    int pieceColumn = piece.getLocation().getCol();
//    return pieceBuilder.buildPiece(pieceData, pieceRow, pieceColumn);
//  }

  /**
   * Iterates through the list<list> as given by the csvParser. Create pieces and adds them to the
   * pieceGrid
   */
  private void iterateCSVData()
      throws InvalidPieceConfigException, PlayerNotFoundException, FileNotFoundException, CsvException {
    for (int r = 0; r < boardSize.get(0); r++) {
      for (int c = 0; c < boardSize.get(1); c++) {
        String[] square = pieceInformation(r, c);
        if (square == null){continue;}
        Piece piece = pieceBuilder.buildPiece(square, r, c);
        pieceList.add(new PieceViewBuilder(piece));
        playerList.get(determinePlayer(r, c, square[0])).addPiece(piece);
      }
    }
  }

  /**
   * takes the given row and column indices and returns the square at that location.
   * throws a csv exception if the square doesn't exist (because there isn't a 8x8 array of pieces)
   */
  private String[] pieceInformation(int r, int c) throws CsvException {
    String[] square;
    try {
      square = csvData.get(r).get(c).split(mappings.getString(CSV_DELIMITER));
    }catch (Exception e){
      throw new CsvException(r);
    }
    if (square.length < 2) {
      return null;
    }
    return square;
  }

  /**
   * determines the index in playerList that matches team
   */
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
   * if any values are missing, an exception is thrown
   */
  private void extractJSONObj(JSONObject jsonObject) throws InvalidGameConfigException {
    try{
      gameType = jsonObject.getString(mappings.getString(TYPE));
      boardShape = jsonObject.getString(mappings.getString(BOARD));

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
      throw new InvalidGameConfigException(e.toString());
    }
  }

  /**
   * converts a jsonarray object to a list object
   */
  private List<String> convertJSONArrayOfStrings(JSONArray jsonArray) {
    List<String> ret = new ArrayList<>();
    for (int i = 0; i < jsonArray.length(); i++) {
      ret.add(jsonArray.getString(i));
    }
    return ret;
  }
}