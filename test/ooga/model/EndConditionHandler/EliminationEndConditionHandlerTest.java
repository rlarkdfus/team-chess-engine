package ooga.model.EndConditionHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.Location;
import ooga.controller.Config.BoardBuilder;
import ooga.controller.Config.Builder;
import ooga.controller.Config.InvalidEndGameConfigException;
import ooga.controller.Config.InvalidGameConfigException;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.model.*;
import ooga.model.EndConditionHandler.EndConditionInterface;
import ooga.model.EndConditionHandler.EndConditionRunner;
import ooga.model.Powerups.PowerupInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EliminationEndConditionHandlerTest {

  EndConditionRunner endConRunner;
  List<PlayerInterface> players;
  Board board;

  @BeforeEach
  void setUp()
      throws InvalidEndGameConfigException, InvalidGameConfigException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    String testFile = "data/chess/testEliminationEndCon.json";
    Builder boardBuilder = new BoardBuilder(new File(testFile));
    players = boardBuilder.getInitialPlayers();
    endConRunner = boardBuilder.getEndConditionHandler();
    List<PowerupInterface> powerups = boardBuilder.getPowerupsHandler();
    board = new GameBoard(players, endConRunner, powerups, boardBuilder.getBoardSize());
  }

  @Test
  void testBlackWin()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException, InvalidPieceConfigException {
    board.movePiece(new Location(1,4),new Location(3,4)); //pawn
    board.movePiece(new Location(0,3),new Location(4, 7)); //queen
    board.movePiece(new Location(4,7),new Location(6, 7)); //queen eats
    board.movePiece(new Location(6,7),new Location(6, 6)); //queen eats
    board.movePiece(new Location(6,6),new Location(6, 5)); //queen eats
    Assertions.assertEquals(GameState.BLACK, board.checkGameState());
  }

  @Test
  void testWhiteWin()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException, InvalidPieceConfigException {
      board.movePiece(new Location(6,4),new Location(4,4)); //pawn
      board.movePiece(new Location(7,3),new Location(3, 7)); //queen
      board.movePiece(new Location(3,7),new Location(1, 7)); //queen eats
      board.movePiece(new Location(1,7),new Location(1, 6)); //queen eats
      board.movePiece(new Location(1,6),new Location(1, 5)); //queen eats
      assertEquals(GameState.WHITE, board.checkGameState());
  }

  @Test
  void testAlmostWin()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException, InvalidPieceConfigException {
    board.movePiece(new Location(6,4),new Location(4,4)); //pawn
    board.movePiece(new Location(7,3),new Location(3, 7)); //wqueen
    board.movePiece(new Location(3,7),new Location(1, 7)); //wqueen eats
    board.movePiece(new Location(1,7),new Location(1, 6)); //wqueen eats
    assertEquals(GameState.RUNNING, board.checkGameState());
  }

  @Test
  void testBothSidesPartiallyWin()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException, InvalidPieceConfigException {
    board.movePiece(new Location(6,4),new Location(4,4)); //pawn
    board.movePiece(new Location(7,3),new Location(3, 7)); //wqueen
    board.movePiece(new Location(3,7),new Location(1, 7)); //wqueen eats
    board.movePiece(new Location(1,7),new Location(1, 6)); //wqueen eats
    board.movePiece(new Location(1,4),new Location(3,4)); //pawn
    board.movePiece(new Location(0,3),new Location(4, 7)); //bqueen
    board.movePiece(new Location(4,7),new Location(6, 7)); //bqueen eats
    assertEquals(GameState.RUNNING, board.checkGameState());
  }

  private List<PieceInterface> getAllPieces() {
    List<PieceInterface> allpieces = new ArrayList<>();
    for (PlayerInterface p : players) {
      allpieces.addAll(p.getPieces());
    }
    return allpieces;
  }

  @Test
  void testSetArgs() throws NoSuchFieldException, IllegalAccessException {
    EndConditionInterface e = getEndConObject();
    Field f;
    f = e.getClass().getDeclaredField("previousTurnPieces");
    f.setAccessible(true);
    List<PieceInterface> previousTurnPieces = (List<PieceInterface>) f.get(e);
    assertEquals(getAllPieces().size(), previousTurnPieces.size());

    f = e.getClass().getDeclaredField("piecesToEliminate");
    f.setAccessible(true);
    Map<String, Integer> piecesToEliminate = (Map<String, Integer>) f.get(e);
    assertEquals(2, piecesToEliminate.keySet().size());
    assertEquals(true, piecesToEliminate.keySet().contains("b_P"),
        "piecestoeliminate doesn't have b_P");
    assertEquals(true, piecesToEliminate.keySet().contains("w_P"),
        "piecestoeliminate doesn't have w_P");
  }

  private EndConditionInterface getEndConObject()
      throws NoSuchFieldException, IllegalAccessException {
    Field f;
    f = endConRunner.getClass().getDeclaredField("endConditions");
    f.setAccessible(true);
    List<EndConditionInterface> endConditionInterfaceList = (List<EndConditionInterface>) f.get(endConRunner);
    for (EndConditionInterface e : endConditionInterfaceList){
      if (e.getClass().toString().equals("class ooga.model.EndConditionHandler.EliminationEndCondition")){
        return e;
      }
    }
    return null;
  }
}