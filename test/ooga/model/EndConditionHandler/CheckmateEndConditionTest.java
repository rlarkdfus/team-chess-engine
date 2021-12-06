package ooga.model.EndConditionHandler;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import ooga.Location;
import ooga.controller.Config.BoardBuilder;
import ooga.controller.Config.Builder;
import ooga.controller.Config.InvalidEndGameConfigException;
import ooga.controller.Config.InvalidGameConfigException;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.model.Board;
import ooga.model.EndConditionHandler.EndConditionRunner;
import ooga.model.GameBoard;
import ooga.model.GameState;
import ooga.model.PlayerInterface;
import ooga.model.Powerups.PowerupInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckmateEndConditionTest {

  EndConditionRunner endConRunner;
  List<PlayerInterface> players;
  Board board;

  @BeforeEach
  void setUp()
      throws InvalidEndGameConfigException, InvalidGameConfigException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    String testFile = "data/chess/defaultChessNoTimer.json";
    Builder boardBuilder = new BoardBuilder(new File(testFile));
    players = boardBuilder.getInitialPlayers();
    endConRunner = boardBuilder.getEndConditionHandler();
    List<PowerupInterface> powerups = boardBuilder.getPowerupsHandler();
    board = new GameBoard(players, endConRunner, powerups, boardBuilder.getBoardSize());
  }

  @Test
  void testNoCheck() {
    Assertions.assertEquals(GameState.RUNNING, board.checkGameState());
  }

  @Test
  void testWhiteWin()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException, InvalidPieceConfigException {
    board.movePiece(new Location(6,4),new Location(4,4)); //pawn
    board.movePiece(new Location(7,3),new Location(5, 5)); //queen
    board.movePiece(new Location(7,5),new Location(4,2)); //bishop
    board.movePiece(new Location(5,5),new Location(1, 5)); //queen
    System.out.println(board.toString());
    assertEquals(GameState.WHITE, board.checkGameState());
  }

  @Test
  void testBlackWin()
      throws FileNotFoundException, InvocationTargetException, InvalidPieceConfigException, NoSuchMethodException, IllegalAccessException {
    board.movePiece(new Location(1,4),new Location(3,4)); //pawn
    board.movePiece(new Location(0,3),new Location(2, 5)); //queen
    board.movePiece(new Location(0,5),new Location(3,2)); //bishop
    board.movePiece(new Location(2,5),new Location(6, 5)); //queen
    assertEquals(GameState.BLACK, board.checkGameState());
  }
//
//  @Test
//  void testBlackCheck()
//      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException, InvalidPieceConfigException {
//    board.movePiece(new Location(1,4),new Location(3,4)); //pawn
//    board.movePiece(new Location(0,3),new Location(2, 5)); //queen
//    board.movePiece(new Location(2,5),new Location(6, 5)); //queen
//    assertEquals(GameState.CHECK, board.checkGameState());
//  }
//
//  @Test
//  void testWhiteCheck()
//      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException, InvalidPieceConfigException {
//    board.movePiece(new Location(6,4),new Location(4,4)); //pawn
//    board.movePiece(new Location(7,3),new Location(5, 5)); //queen
//    board.movePiece(new Location(5,5),new Location(1, 5)); //queen
//    assertEquals(GameState.CHECK, board.checkGameState());
//  }
}
