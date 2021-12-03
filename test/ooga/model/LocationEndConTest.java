package ooga.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import ooga.Location;
import ooga.controller.BoardBuilder;
import ooga.controller.Builder;
import ooga.controller.InvalidEndGameConfigException;
import ooga.controller.InvalidGameConfigException;
import ooga.controller.InvalidPieceConfigException;
import ooga.model.Board.GameState;
import ooga.model.EndConditionHandler.EndConditionRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LocationEndConTest {


  EndConditionRunner endConRunner;
  List<PlayerInterface> players;
  Board board;

  @BeforeEach
  void setUp()
      throws InvalidEndGameConfigException, InvalidGameConfigException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    String testFile = "data/chess/testLocationEndCon.json";
    Builder boardBuilder = new BoardBuilder(new File(testFile));
    players = boardBuilder.getInitialPlayers();
    endConRunner = boardBuilder.getEndConditionHandler();
    board = new Board(players);
    board.setEndCondition(endConRunner);
  }

  @Test
  void testWinBlack()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException, InvalidPieceConfigException {
    board.movePiece(new Location(1, 0), new Location(3, 0));
    board.movePiece(new Location(3, 0), new Location(4, 0));
    board.movePiece(new Location(1, 1), new Location(3, 1));
    board.movePiece(new Location(3, 1), new Location(4, 1));
    board.movePiece(new Location(1, 2), new Location(3, 2));
    board.movePiece(new Location(3, 2), new Location(4, 2));
    board.movePiece(new Location(1, 3), new Location(3, 3));
    assertEquals(GameState.RUNNING, board.checkGameState());
    board.movePiece(new Location(3, 3), new Location(4, 3));
    assertEquals(GameState.CHECKMATE, board.checkGameState());
    assertEquals("b", board.getWinner());
  }

  @Test
  void testWinWhite()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException, InvalidPieceConfigException {
    board.movePiece(new Location(6, 0), new Location(4, 0)); //pawn
    board.movePiece(new Location(6, 1), new Location(4, 1)); //pawn
    board.movePiece(new Location(6, 2), new Location(4, 2)); //pawn
    assertEquals(GameState.RUNNING, board.checkGameState());
    board.movePiece(new Location(6, 3), new Location(4, 3)); //pawn

    assertEquals(GameState.CHECKMATE, board.checkGameState());
    assertEquals("w", board.getWinner());
  }

  @Test
  void testWhiteNotEnoughPieces() //when you dont have enough pieces to make all the target locations
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException, InvalidPieceConfigException {

    board.movePiece(new Location(1, 4), new Location(3, 4)); //pawn
    board.movePiece(new Location(0, 3), new Location(4, 7)); //queen
    board.movePiece(new Location(4, 7), new Location(6, 7)); //queen eats
    board.movePiece(new Location(6, 7), new Location(6, 6)); //queen eats
    board.movePiece(new Location(6, 6), new Location(6, 5)); //queen eats
    assertEquals(GameState.RUNNING, board.checkGameState());
    board.movePiece(new Location(6, 5), new Location(6, 4)); //queen eats
    assertEquals(GameState.RUNNING, board.checkGameState());
    board.movePiece(new Location(6, 4), new Location(6, 3)); //queen eats

    assertEquals(GameState.CHECKMATE, board.checkGameState());
    assertEquals("b", board.getWinner());
  }

  @Test
  void testBlackNotEnoughPieces()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException, InvalidPieceConfigException {
    board.movePiece(new Location(6, 4), new Location(4, 4)); //pawn
    board.movePiece(new Location(7, 3), new Location(3, 7)); //queen
    board.movePiece(new Location(3, 7), new Location(1, 7)); //queen eats
    board.movePiece(new Location(1, 7), new Location(1, 6)); //queen eats
    board.movePiece(new Location(1, 6), new Location(1, 5)); //queen eats
    assertEquals(GameState.RUNNING, board.checkGameState());
    board.movePiece(new Location(1, 5), new Location(1, 4)); //queen eats
    assertEquals(GameState.RUNNING, board.checkGameState());
    board.movePiece(new Location(1, 4), new Location(1, 3)); //queen eats

    assertEquals(GameState.CHECKMATE, board.checkGameState());
    assertEquals("w", board.getWinner());
  }

  @Test
  void testPartiallyWrongTeam()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException, InvalidPieceConfigException {
    board.movePiece(new Location(1, 0), new Location(3, 0));//black
    board.movePiece(new Location(3, 0), new Location(4, 0));//black
    board.movePiece(new Location(1, 1), new Location(3, 1));//black
    board.movePiece(new Location(3, 1), new Location(4, 1));//black
    board.movePiece(new Location(1, 2), new Location(3, 2));//black
    board.movePiece(new Location(3, 2), new Location(4, 2)); //black
    board.movePiece(new Location(6, 3), new Location(4, 3)); //white

    assertEquals(GameState.RUNNING, board.checkGameState());
  }

  @Test
  void testWrongPieces()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException, InvalidPieceConfigException {
    board.movePiece(new Location(6, 1), new Location(4, 1)); //pawn
    board.movePiece(new Location(6, 2), new Location(4, 2)); //pawn
    board.movePiece(new Location(6, 3), new Location(4, 3)); //pawn
    board.movePiece(new Location(7, 3), new Location(4, 0)); //queen

    assertEquals(GameState.RUNNING, board.checkGameState());
  }
}
