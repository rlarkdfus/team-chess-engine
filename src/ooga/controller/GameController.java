package ooga.controller;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;
import javafx.beans.property.StringProperty;
import ooga.Location;
import ooga.LogUtils;
import ooga.controller.Config.Builder;
import ooga.controller.Config.JSONWriter;
import ooga.controller.Config.JsonParser;
import ooga.controller.Config.PieceViewBuilder;
import ooga.model.*;
import ooga.view.GameOverScreen;
import ooga.view.GameView;
import ooga.view.ViewInterface;
import ooga.view.util.ViewUtility;
import org.json.JSONObject;

/**
 * @Authors albert luis gordon sam richard
 *
 * purpose - this class is a controller type object so it connects the model and view. This specific
 *  subclass is used for when we are in game mode, which allows for users to play a turn, toggle the timer,
 *  and log their wins.
 * assumptions - we assume that the defaultConfiguration file is valid. if this isn't the case, the
 *  game will crash.
 * dependencies - this class depends on the game model classes, the game view classes, and the
 *  boardbuilder classes.
 * To use - The user must only create a new GameController object in main.
 */
public class GameController extends Controller implements GameControllerInterface{
  public static final File DEFAULT_CHESS_CONFIGURATION = new File("data/chess/defaultChess.json");
  public static final String WINS = "wins";
  public static final String JSON_WRITER_FILE_PATH = "data/chess/profiles/profiles";
  private final File userProfilesFile = new File("data/chess/profiles/profiles.json");
  public static final int DEFAULT_INITIAL_TIME = 5;
  public static final int DEFAULT_INITIAL_INCREMENT = 5;

  private TimeController timeController;

  private int initialTime = DEFAULT_INITIAL_TIME;
  private int increment = DEFAULT_INITIAL_INCREMENT;
  private GameEngine model;

  private GameOverScreen gameOverScreen;
  private Map<Enum, JSONObject> players;
  private Map<Enum, String> usernames;

  public GameController(){
    super();
  }

  @Override
  protected File getDefaultConfiguration() {
    return DEFAULT_CHESS_CONFIGURATION;
  }

  /**
   * this method overrides an abstract superclass method. Here a model is created and initalized
   * with the pieces, endcondition handler, powerups, and bounds that were built by the boardbuilder.
   * We also initialize a timecontroller and start the timer
   * @param boardBuilder - a boardbuilder object that holds vital objects like the pieces, powerups, and endconditions
   * @return - an Engine object that is initialized.
   */
  @Override
  protected Engine initializeModel(Builder boardBuilder) {
    List<PlayerInterface> players = boardBuilder.getInitialPlayers();
    model = new GameBoard(players, boardBuilder.getEndConditionHandler(), boardBuilder.getPowerupsHandler(),
            boardBuilder.getBoardSize());
    configureTimersStartOfApplication();
    System.out.println("initializing model w/ time settings: " + initialTime + ", " + increment);
    timeController = new TimeController(initialTime, increment);
    timeController.configTimers(players);
    startTimersForNewGame(players);
    return model;
  }

  /**
   * This method overrides an abstract superclass method. Here the view is created and initialized
   * with the pieces and bounds built by the boardbuilder.
   * @param pieces -  a list of data objects that are used to produce javafx objects in view
   * @param bounds -  a location object that is used to define the bounds of the display board
   * @return - a view object that's been initialized.
   */
  @Override
  protected ViewInterface initializeView(List<PieceViewBuilder> pieces, Location bounds) {
    ViewInterface view = new GameView(this);
    //view.resetDisplay(pieces, bounds);
    view.initializeDisplay(pieces, bounds);
    return view;
  }

  /**
   * this method finds all the players' usernames and the amount of times they've one a game
   * @return - a map of player names to their win count
   */
  @Override
  public Map<String, Integer> getUsernameAndWins() {

    Map<String, Integer> usernameToWinsMap = new HashMap<>();
    if (players != null) {
      Iterator playersIter = players.entrySet().iterator();
      Iterator usernamesIter = usernames.entrySet().iterator();
      while (playersIter.hasNext() && usernamesIter.hasNext()) {
        Entry<Enum, String> usernameEntry = (Entry) usernamesIter.next();
        Entry<Enum, JSONObject> playersEntry = (Entry) playersIter.next();
        usernameToWinsMap.put(usernameEntry.getValue(), playersEntry.getValue().getInt("wins"));
      }
    }
    return usernameToWinsMap;
  }

  /**
   * This overridden method calls the super class method which moves the piece in model, and then
   * updates the view. Then, it checks the game state to see if the game is over. If so, we show the
   * game over screen and update the win counter for the players
   * @param start is initial location of moved piece
   * @param end   is final location of moved piece
   */
  @Override
  public void movePiece(Location start, Location end) {
    super.movePiece(start, end);
    GameState gameState = model.checkGameState();
    if (gameState != GameState.RUNNING) {
      LogUtils.info(this,"winner: "+gameState);
      gameOverScreen = new GameOverScreen(this, gameState.toString());
      incrementPlayerWin(gameState);
      getUsernameAndWins();
    }
  }

  /**
   * this method finds the player who won
   */
  private void incrementPlayerWin(GameState gameState) {
    if (players != null) {
      Iterator playersIter = players.keySet().iterator();
      while (playersIter.hasNext()) {
        Enum player = (Enum) playersIter.next();
        if (gameState == player) {
          incrementWinAndSaveJSON(gameState, player);
        }
      }
    }
  }

  /**
   * this method adds a win to the player's json file
   */
  private void incrementWinAndSaveJSON(GameState gameState, Enum player) {
    JSONObject playerInfo = players.get(player);
    int wins = players.get(player).getInt(WINS) + 1;
    players.remove(wins);
    playerInfo.put(WINS, wins);
    players.remove(player);
    players.put(player, playerInfo);
    JSONObject userProfiles = JsonParser.loadFile(userProfilesFile);
    userProfiles.remove(usernames.get(gameState));
    userProfiles.put(usernames.get(gameState), playerInfo);
    try {
      JSONWriter.saveFile(userProfiles, JSON_WRITER_FILE_PATH);
    } catch (Exception e) {
      ViewUtility.showError("File not found");
    }
  }

  //TODO: TIMER
  private void configureTimersStartOfApplication() {
    setInitialTime(DEFAULT_INITIAL_TIME);
    setIncrement(DEFAULT_INITIAL_INCREMENT);
  }

  /**
   * reset timers for a new game and start the first player's timer
   */
  private void startTimersForNewGame(List<PlayerInterface> players) {
    timeController.resetTimers(players);
    timeController.startPlayer1Timer(players);
  }

  /**
   * gets the amount of time left on a player's timer
   * @param side the side of the player
   * @return a StringProperty ("mm:ss") representing the amount of time left
   */
  @Override
  public StringProperty getTimeLeft(int side) {
    return model.getPlayers().get(side).getTimeLeft();
  }

  /**
   * tells the timeController to change the initial time for the next game
   *
   * @param minutes the new initial time (min)
   */
  @Override
  public void setInitialTime(int minutes) {
    initialTime = minutes;
  }

  /**
   * tells the timeController to change the increment time for the next game
   *
   * @param seconds the new increment (s)
   */
  @Override
  public void setIncrement(int seconds) {
    increment = seconds;
  }

  /**
   * This methods uses the input maps to find the player json file and the player name from the Piece Color
   * that they played as (this is the enum).
   * @param usernames - a map from the piece color to the player's username
   * @param players - a map from the piece color to the player's json file
   */
  public void setPlayers(Map<Enum, String> usernames, Map<Enum, JSONObject> players) {
    this.players = players;
    this.usernames = usernames;
  }

    public List<Integer> getUpdatedScores() {
        List<Integer> scores = new ArrayList<>();
        for (PlayerInterface player : model.getPlayers()) {
            scores.add(player.getScore());
        }
        return scores;
    }
}
