package ooga.model;

import static org.junit.jupiter.api.Assertions.*;

import ooga.Location;
import ooga.controller.BoardBuilder;
import ooga.controller.InvalidPieceConfigException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

class BoardTest {
  private Engine board;

  @BeforeEach
  void setUp() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    board = ModelTestHelper.createBoard();
  }

  @Test
  void testRemovingPiece() throws FileNotFoundException, InvalidPieceConfigException {
    List<Location[]> moves = List.of(
            new Location[]{new Location(6, 4), new Location(5, 4)},
            new Location[]{new Location(1, 1), new Location(3, 1)},
            new Location[]{new Location(7, 5), new Location(3, 1)}
    );
    int before = board.movePiece(new Location(7, 6), new Location(5, 5)).size();
    board.movePiece(new Location(5, 5), new Location(7, 6)).size();
    int after = 0;
    for(Location[] move : moves) {
      after = board.movePiece(move[0], move[1]).size();
    }
    assertEquals(1, before - after);
  }

  @Test
  void testMovesCorrectly() throws FileNotFoundException, InvalidPieceConfigException {
    Location start = new Location(1,0);
    Location end = new Location(2,0);

    List<Location> pieceLocations = new ArrayList<>();
    for(PieceInterface piece : board.movePiece(start, end)) {
      pieceLocations.add(piece.getLocation());
    }

    assertFalse(start.inList(pieceLocations));
    assertTrue(end.inList(pieceLocations));
  }
}