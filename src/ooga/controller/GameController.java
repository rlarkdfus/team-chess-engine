package ooga.controller;

import ooga.Location;
import ooga.model.*;
import ooga.view.GameOverScreen;
import ooga.view.GameView;
import ooga.view.View;

import java.util.ArrayList;
import java.util.List;

public class GameController extends Controller {

    public static final int DEFAULT_INITIAL_TIME = 5;
    public static final int DEFAULT_INITIAL_INCREMENT = 5;

    private GameOverScreen gameOverScreen;
    private View view;
    private TimeController timeController;

    @Override
    public void start() {
        try {
            //buildGame() is the 3 lines below
            model = new GameBoard(boardBuilder.getInitialPlayers());
            model.setEndCondition(boardBuilder.getEndConditionHandler());
            view = new GameView(this);
            view.initializeDisplay(boardBuilder.getInitialPieceViews());

            timeController = new TimeController(DEFAULT_INITIAL_TIME, DEFAULT_INITIAL_INCREMENT);
//            timeController.configTimers(model.getPlayers()); this is done in boardbuilder
            startTimersForNewGame();
        } catch (Exception E) {
            E.printStackTrace();
            //view.showError(E.toString());
        }
    }

    public void movePiece(Location start, Location end) {
        List<PieceViewBuilder> pieceViewList = new ArrayList<>();
        for (PieceInterface piece : model.movePiece(start, end)) {
            pieceViewList.add(new PieceViewBuilder(piece));
        }
        view.updateDisplay(pieceViewList);
        GameState gameState = model.checkGameState();
        if(gameState != GameState.RUNNING) {
            gameOverScreen = new GameOverScreen(this, gameState.toString());
        }
    }


    //TODO: TIMER
    /**
     * reset timers for a new game and start the first player's timer
     */
    private void startTimersForNewGame() {
        System.out.println("starting timers");
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
