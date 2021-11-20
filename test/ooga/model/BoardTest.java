package ooga.model;

import static org.junit.jupiter.api.Assertions.*;

import ooga.Location;
import ooga.Turn;
import ooga.Turn.PieceMove;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

class BoardTest {
  private Board board;

  @BeforeEach
  void setUp() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    board = new Board(new ArrayList<>());
  }

  @Test
  void testRemovingPiece() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    Location loc1 = new Location(0,1);
    Location loc2 = new Location(0,7);
    Turn turn = board.movePiece(loc1, loc2);
    int expected = 1;
    int test = turn.getRemoved().size();
    assertEquals(expected, test);
  }

  @Test
  void testMovesCorrectly() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    Location end = new Location(3,3);
    Location loc1 = new Location(1,3);
    Location loc2 = new Location(3,3);

    Turn turn = board.movePiece(loc1, loc2);
    PieceMove pieceMove = turn.getMoves().get(0);

    assertTrue(end.equals(pieceMove.getEndLocation()));
  }
}