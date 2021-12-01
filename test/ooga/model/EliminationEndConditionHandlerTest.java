package ooga.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.Location;
import ooga.controller.BoardBuilder;
import ooga.controller.Builder;
import ooga.model.Board.GameState;
import ooga.model.EndConditionHandler.EliminationEndCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EliminationEndConditionHandlerTest {

  EliminationEndCondition e;
  List<PlayerInterface> players;

  @BeforeEach
  void setUp() {
    e = new EliminationEndCondition();
    String testFile = "data/chess/defaultChess.json";
    Builder boardBuilder = new BoardBuilder(new File(testFile));
    players = boardBuilder.getInitialPlayers();

    e.setArgs(Map.of("pieceType", List.of("P","P", "K")), getAllPieces());
  }

  @Test
  void testSetArgs() throws NoSuchFieldException, IllegalAccessException {
    Field f;
    f = e.getClass().getDeclaredField("previousTurnPieces");
    f.setAccessible(true);
    List<PieceInterface> previousTurnPieces = (List<PieceInterface>) f.get(e);
    assertEquals(getAllPieces().size(), previousTurnPieces.size());

    f = e.getClass().getDeclaredField("piecesToEliminate");
    f.setAccessible(true);
    Map<String, Integer> piecesToEliminate = (Map<String, Integer>) f.get(e);
    assertEquals(4, piecesToEliminate.keySet().size());
    assertEquals(true, piecesToEliminate.keySet().contains("b_P"),
        "piecestoeliminate doesn't have b_P");
    assertEquals(true, piecesToEliminate.keySet().contains("b_K"),
        "piecestoeliminate doesn't have b_K");
    assertEquals(true, piecesToEliminate.keySet().contains("w_P"),
        "piecestoeliminate doesn't have w_P");
    assertEquals(true, piecesToEliminate.keySet().contains("w_K"),
        "piecestoeliminate doesn't have w_K");

  }

  @Test
  void testBlackWin()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    removePieces("w", List.of(new Location(7, 4), new Location(6, 0), new Location(6, 1)));
    GameState ret = e.isGameOver(players);
    assertEquals(GameState.CHECKMATE, ret, "game should be over");
    assertEquals("b", e.getWinner(), "black should win");

  }

  @Test
  void testWhiteWin()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

    removePieces("b", List.of(new Location(0, 4), new Location(1, 0), new Location(1, 1)));
    GameState ret = e.isGameOver(players);
    assertEquals(GameState.CHECKMATE, ret, "game should end");
    assertEquals("w", e.getWinner(), "white should win");
  }

  @Test
  void testAlmostWin()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    removePieces("b", List.of(new Location(0, 4), new Location(1, 0)));
    GameState ret = e.isGameOver(players);
    assertEquals(GameState.RUNNING, ret, "still need to remove 1 more b_pawn");
  }

  @Test
  void testBothSidesPartiallyWin()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    removePieces("b", List.of(new Location(0, 4), new Location(1, 0)));
    removePieces("w",List.of(new Location(6, 1)));
    GameState ret = e.isGameOver(players);
    assertEquals(GameState.RUNNING, ret, "still need to remove 1 more b_pawn, 1 w_pawn, 1 w_king");
  }

  private List<PieceInterface> getAllPieces() {
    List<PieceInterface> allpieces = new ArrayList<>();
    for (PlayerInterface p : players) {
      allpieces.addAll(p.getPieces());
    }
    return allpieces;
  }

  private void removePieces(String team, List<Location> ToBeRemoved)
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    for (PlayerInterface p : players) {
      if (p.getTeam().equals(team)) {
        int initSize = p.getPieces().size();
        for (Location l : ToBeRemoved) {
          p.removePiece(l);
        }
        assertEquals(initSize-ToBeRemoved.size(), p.getPieces().size());
      }
    }
  }
}