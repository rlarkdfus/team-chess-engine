package ooga.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import ooga.controller.BoardBuilder;
import ooga.controller.Builder;
import ooga.controller.InvalidEndGameConfigException;
import ooga.controller.InvalidGameConfigException;
import ooga.controller.InvalidPieceConfigException;
import ooga.model.EndConditionHandler.EndConditionRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimerEndConditionHandlerTest {

  EndConditionRunner endConRunner;
  List<PlayerInterface> players;
  Board board;

  @BeforeEach
  void setUp()
      throws InvalidEndGameConfigException, InvalidGameConfigException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    String testFile = "data/chess/testTimerEndCon.json";
    Builder boardBuilder = new BoardBuilder(new File(testFile));
    players = boardBuilder.getInitialPlayers();
    endConRunner = boardBuilder.getEndConditionHandler();
    board = new GameBoard(players);
    board.setEndCondition(endConRunner);
  }

  @Test
  void testBlackWin()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException, InvalidPieceConfigException, InterruptedException, ExecutionException {
    for (PlayerInterface playerInterface : players) {
      if (playerInterface.getTeam().equals("w")){
        playerInterface.toggleTimer();
      }
      break;
    }
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    ScheduledFuture scheduledFuture =
      executorService.schedule(new Callable() {
        public GameState call() {
          System.out.println("should be over " + endConRunner.satisfiedEndCondition(players));
          return endConRunner.satisfiedEndCondition(players);
        }
      }, 60, TimeUnit.SECONDS);
    assertEquals(GameState.BLACK,scheduledFuture.get());
    executorService.shutdown();
  }
}