package ooga.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import ooga.Location;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.controller.Config.JSONWriter;
import ooga.controller.Config.JsonParser;
import ooga.controller.Config.PieceViewBuilder;
import ooga.model.*;
import ooga.view.GameOverScreen;
import ooga.view.GameView;
import ooga.view.View;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class GameController extends Controller {

    public static final int DEFAULT_INITIAL_TIME = 5;
    public static final int DEFAULT_INITIAL_INCREMENT = 5;

    private final File userProfilesFile = new File("data/chess/profiles/profiles.json");
    private int initialTime;
    private int increment;

    private GameOverScreen gameOverScreen;
    private View view;
    private TimeController timeController;
    private Map<Enum, JSONObject> players;
    private Map<Enum, String> usernames;

    @Override
    protected void configureInitialGameSettings() {
        initialTime = DEFAULT_INITIAL_TIME;
        increment = DEFAULT_INITIAL_INCREMENT;
    }

    @Override
    public void start() {
        try {
            //buildGame() is the 3 lines below
            model = new GameBoard(boardBuilder.getInitialPlayers(), boardBuilder.getEndConditionHandler(), boardBuilder.getPowerupsHandler());
            view = new GameView(this);
            view.initializeDisplay(boardBuilder.getInitialPieceViews());
            timeController = new TimeController(initialTime, increment);
            timeController.configTimers(model.getPlayers());
            startTimersForNewGame();
        } catch (Exception E) {
            E.printStackTrace();
            //view.showError(E.toString());
        }
    }

    public void movePiece(Location start, Location end) throws FileNotFoundException, InvalidPieceConfigException {
        List<PieceViewBuilder> pieceViewList = new ArrayList<>();
        model.movePiece(start, end).forEach(piece -> pieceViewList.add(new PieceViewBuilder(piece)));
        view.updateDisplay(pieceViewList);
        GameState gameState = model.checkGameState();
        if (gameState != GameState.RUNNING) {
            gameOverScreen = new GameOverScreen(this, gameState.toString());
        }
        incrementPlayerWin(gameState);
    }

    private void incrementPlayerWin(GameState gameState) throws FileNotFoundException {
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

    private void incrementWinAndSaveJSON(GameState gameState, Enum player) throws FileNotFoundException {
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
    private void startTimersForNewGame() {
        timeController.resetTimers(model.getPlayers());
        timeController.startPlayer1Timer(model.getPlayers());
    }

    /**
     * tells the timeController to change the initial time for the next game
     *
     * @param minutes the new initial time (min)
     */
    @Override
    public void setInitialTime(int minutes) {
        //timeController.setInitialTime(minutes);
        initialTime = minutes;
    }

    /**
     * tells the timeController to change the increment time for the next game
     *
     * @param seconds the new increment (s)
     */
    @Override
    public void setIncrement(int seconds) {
        //timeController.setIncrement(seconds);
        increment = seconds;
    }

    public void setPlayers(Map<Enum, String> usernames, Map<Enum, JSONObject> players) {
        this.players = players;
        this.usernames = usernames;
    }
}
