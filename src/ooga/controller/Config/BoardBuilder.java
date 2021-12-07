package ooga.controller.Config;

import static java.lang.Integer.parseInt;
import static ooga.controller.Config.PieceBuilder.buildPiece;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import ooga.Location;
import ooga.LogUtils;
import ooga.model.EndConditionHandler.EndConditionRunner;
import ooga.model.Piece;
import ooga.model.Player;
import ooga.model.PlayerInterface;
import ooga.model.Powerups.PowerupInterface;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Albert, Luis
 * purpose - this class builds objects that will be used by the model to run the game. It builds all
 *  the different pieces, and it builds the endConditions that determine when the game ends.
 * assumptions - we assume that the file is a json and formatted with the proper keys. If any of this
 *  isn't there, then we throw exceptions that get caught and sent to the view.
 * dependencies - this class depends on the EndConditionBuilder, the PieceBuilder, and the JsonParser,
 *  and the LocationParser (aka the csv parser)
 * To use - The user will call build() with a File object. Then to get the objects, the user will call the
 *  3 getter methods.
 */
public class BoardBuilder implements Builder {

  public static final int ARG_LENGTH = 4;
  public static final String PROPERTIES_FILE = "JSONMappings";
  public static final String CSV_DELIMITER = "csvDelimiter";
  public static final String JSON_DELIMITER = "jsonDelimiter";
  public static final String RULE_TYPE = "ruleType";
  public static final String PIECE_TYPE = "pieceType";
  public static final String POWER_UPS = "powerups";
  public static final String RULES = "rules";
  public static final String TYPE = "type"; //unused
  public static final String BOARD = "board"; //unused
  public static final String TIME = "time";
  public static final String TIME_INCREMENT = "timeIncrement";
  public static final String BOARD_SIZE = "boardSize";
  public static final String X = "x";
  public static final String BOARD_COLORS = "boardColors"; //unused
  public static final String PLAYERS = "players";
  public static final String CSV = "csv";

  public static final ResourceBundle mappings= ResourceBundle.getBundle(PROPERTIES_FILE);

  private Location boardSize;
  private int time;
  private int timeIncrement;
  private List<List<String>> csvData;
  private List<PlayerInterface> playerList;
  private List<PieceViewBuilder> pieceList;
  private EndConditionRunner endCondition;
  private List<PowerupInterface> powerupsList;

  /**
   * Constructor for the BoardBuilder Object.
   * Automatically parses and builds objects for the file given as a parameter
   * @param defaultFile - a default file object that's defined in controller
   */
  public BoardBuilder(File defaultFile) {
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
   * @throws InvalidPowerupsConfigException - if the powerups json is invalid
   */
  @Override
  public void build(File file)
      throws CsvException, FileNotFoundException, PlayerNotFoundException, InvalidPieceConfigException, InvalidGameConfigException, InvalidEndGameConfigException, InvalidPowerupsConfigException {
    pieceList = new ArrayList<>();
    playerList = new ArrayList<>();
    JSONObject gameJson = JsonParser.loadFile(file);
    extractJSONObj(gameJson);

    iterateCSVData();
    endCondition = EndConditionBuilder.getEndConditions(gameJson.getString(RULES),playerList);
    powerupsList = PowerUpsBuilder.getPowerups(gameJson.getString(POWER_UPS),boardSize);
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

  @Override
  public List<Location> getPowerupLocations() {
    List<Location> locations = new ArrayList<>();
    for (PowerupInterface powerup : powerupsList) {
      locations.addAll(powerup.getPowerupLocations());
    }
    return locations;
  }

  /**
   * Overridden interface getter method.
   * @return a Location object that represent the bounds of the board
   */
  @Override
  public Location getBoardSize() {
    return boardSize;
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

  /**
   * Overridden interface getter method.
   * @return a list of powerup Objects which are used by the model to determine when and what powerups
   * should be activated
   */
  @Override
  public List<PowerupInterface> getPowerupsHandler() {
    return powerupsList;
  }

  /**
   * Iterates through the list<list> as given by the csvParser. Create pieces and adds them to the
   * pieceGrid
   */
  private void iterateCSVData()
      throws PlayerNotFoundException, CsvException {
    for (int r = 0; r < boardSize.getRow(); r++) {
      for (int c = 0; c < boardSize.getCol(); c++) {
        String[] square = pieceInformation(r, c);
        if (square == null){continue;}
        Piece piece = buildPiece(square[0], square[1],new Location(r,c),boardSize);
        pieceList.add(new PieceViewBuilder(piece));
        playerList.get(determinePlayer(r, c, square[0])).addPiece(piece);
      }
    }
  }

  /**
   * takes the given row and column indices and returns the square at that location.
   * @param  r row
   * @param c column
   * @throws CsvException if the square doesn't exist (because there isn't a 8x8 array of pieces)
   * @return String[] of square location
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
   * determines the index in playerList that matches team. This is used to figure out which player a
   *  piece should be stored in
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
    String errorKey = null;
    try{

      errorKey = TIME;
      time = jsonObject.getInt(mappings.getString(TIME));

      errorKey = TIME_INCREMENT;
      timeIncrement = jsonObject.getInt(mappings.getString(TIME_INCREMENT));

      errorKey = BOARD_SIZE;
      String[] dimension = jsonObject.getString(mappings.getString(BOARD_SIZE)).split(X);
      boardSize = new Location(parseInt(dimension[0]),parseInt(dimension[1]));

      errorKey = PLAYERS;
      for (String player : convertJSONArrayOfStrings(
          jsonObject.getJSONArray(mappings.getString(PLAYERS)))) {
        PlayerInterface newPlayer = new Player(player);
        LogUtils.info(this,"time: "+time +" timeincrement: " +timeIncrement);
        newPlayer.configTimer(time,timeIncrement);
        playerList.add(newPlayer);
      }

      errorKey = CSV;
      String csv = jsonObject.getString(mappings.getString(CSV));
      csvData = LocationParser.getInitialLocations(csv);

    }catch (Exception e){
      throw new InvalidGameConfigException(errorKey);
    }
  }

  /**
   * converts a jsonarray object to a list object
   * @param jsonArray inputted jsonArray
   * @return List of strings
   */
  private List<String> convertJSONArrayOfStrings(JSONArray jsonArray) {
    List<String> ret = new ArrayList<>();
    for (int i = 0; i < jsonArray.length(); i++) {
      ret.add(jsonArray.getString(i));
    }
    return ret;
  }
}