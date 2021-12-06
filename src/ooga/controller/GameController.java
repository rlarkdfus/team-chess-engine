package ooga.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import ooga.Location;
import ooga.controller.Config.Builder;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.controller.Config.JSONWriter;
import ooga.controller.Config.JsonParser;
import ooga.controller.Config.PieceViewBuilder;
import ooga.model.Engine;
import ooga.model.GameBoard;
import ooga.model.GameState;
import ooga.model.PlayerInterface;
import ooga.view.GameOverScreen;
import ooga.view.GameView;
import ooga.view.View;
import ooga.view.ViewInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class GameController extends Controller {
  public static final File DEFAULT_CHESS_CONFIGURATION = new File("data/chess/defaultChess.json");

  private TimeController timeController;
    private final File userProfilesFile = new File("data/chess/profiles/profiles.json");
    private int initialTime;
    private int increment;

    private GameOverScreen gameOverScreen;
    private Map<Enum, JSONObject> players;
    private Map<Enum, String> usernames;

  @Override
  protected File getDefaultConfiguration() {
    return DEFAULT_CHESS_CONFIGURATION;
  }

  @Override
  protected Engine initializeModel(Builder boardBuilder) {
    List<PlayerInterface> players = boardBuilder.getInitialPlayers();
    Engine model = new GameBoard(players, boardBuilder.getEndConditionHandler(), new ArrayList<>());
    timeController = new TimeController(initialTime, increment);
    timeController.configTimers(players);
    startTimersForNewGame(players);
    return model;
  }

  @Override
  protected ViewInterface initializeView(List<PieceViewBuilder> pieces) {
    ViewInterface view = new GameView(this);
    view.initializeDisplay(pieces);
    return view;
  }
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

    public void movePiece(Location start, Location end) {
        super.movePiece(start, end);
        GameState gameState = getGameState();
        if (gameState != GameState.RUNNING) {
            gameOverScreen = new GameOverScreen(this, gameState.toString());
        }
        incrementPlayerWin(gameState);
        getUsernameAndWins();
    }

    private void incrementPlayerWin(GameState gameState) {
        if(players != null) {
            Iterator playersIter = players.keySet().iterator();
            while (playersIter.hasNext()) {
                Enum player = (Enum) playersIter.next();
                if (gameState == player) {
                    incrementWinAndSaveJSON(gameState, player);
                }
            }
        }
    }

    private void incrementWinAndSaveJSON(GameState gameState, Enum player) {
        JSONObject playerInfo = players.get(player);
        int wins =  players.get(player).getInt("wins") + 1;
        players.remove(wins);
        playerInfo.put("wins", wins);
        players.remove(player);
        players.put(player, playerInfo);
        JSONObject userProfiles = JsonParser.loadFile(userProfilesFile);
        userProfiles.remove(usernames.get(gameState));
        userProfiles.put(usernames.get(gameState), playerInfo);
        try {
            JSONWriter.saveFile(userProfiles, "data/chess/profiles/profiles");
        } catch (Exception e) {
        }
    }

    //TODO: TIMER

  /**
   * reset timers for a new game and start the first player's timer
   */
  private void startTimersForNewGame(List<PlayerInterface> players) {
    timeController.resetTimers(players);
    timeController.startPlayer1Timer(players);
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

    public void setPlayers(Map<Enum, String> usernames, Map<Enum, JSONObject> players) {
        this.players = players;
        this.usernames = usernames;
    }
}
