package ooga.controller;

import ooga.Location;
import ooga.controller.Config.Builder;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.controller.Config.PieceViewBuilder;
import ooga.model.Engine;
import ooga.model.GameBoard;
import ooga.model.GameState;
import ooga.model.PlayerInterface;
import ooga.view.GameOverScreen;
import ooga.view.GameView;
import ooga.view.ViewInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameController extends Controller {
  public static final File DEFAULT_CHESS_CONFIGURATION = new File("data/chess/defaultChess.json");

  private TimeController timeController;

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

  @Override
  public void movePiece(Location start, Location end) {
    super.movePiece(start, end);
    GameState gameState = getGameState();
    if (gameState != GameState.RUNNING) {
      new GameOverScreen(this, gameState.toString());
    }
  }

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
}
