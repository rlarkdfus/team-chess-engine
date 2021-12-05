package ooga.controller;

import ooga.Location;
import ooga.controller.Config.Builder;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.controller.Config.PieceViewBuilder;
import ooga.model.*;
import ooga.view.GameOverScreen;
import ooga.view.GameView;
import ooga.view.ViewInterface;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GameController extends Controller {

    public static final int DEFAULT_INITIAL_TIME = 5;
    public static final int DEFAULT_INITIAL_INCREMENT = 5;

    private TimeController timeController;

    @Override
    protected Engine initializeModel(Builder boardBuilder) {
        List<PlayerInterface> players = boardBuilder.getInitialPlayers();
        Engine model = new GameBoard(players);
        model.setEndCondition(boardBuilder.getEndConditionHandler());

        timeController = new TimeController(DEFAULT_INITIAL_TIME, DEFAULT_INITIAL_INCREMENT);
        startTimersForNewGame(players);
        return model;
    }

    @Override
    protected ViewInterface initializeView(List<PieceViewBuilder> pieces) {
        ViewInterface view = new GameView(this);
        view.initializeDisplay(pieces);
        return view;
    }

    @Override
    public void movePiece(Location start, Location end) throws FileNotFoundException, InvalidPieceConfigException {
        super.movePiece(start, end);
        GameState gameState = getGameState();
        if(gameState != GameState.RUNNING) {
            new GameOverScreen(this, gameState.toString());
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
        timeController.setInitialTime(minutes);
    }

    /**
     * tells the timeController to change the increment time for the next game
     *
     * @param seconds the new increment (s)
     */
    @Override
    public void setIncrement(int seconds) {
        timeController.setIncrement(seconds);
    }
}
