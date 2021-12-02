package ooga.model;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.Location;
import ooga.controller.BoardBuilder;
import ooga.controller.Builder;
import ooga.controller.InvalidEndGameConfigException;
import ooga.controller.InvalidGameConfigException;
import ooga.model.Board.GameState;
import ooga.model.EndConditionHandler.CheckmateEndCondition;
import ooga.model.EndConditionHandler.EndConditionInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckmateEndConditionTest {

  EndConditionInterface c;
  List<PlayerInterface> players;

  @BeforeEach
  void setUp() throws InvalidEndGameConfigException, InvalidGameConfigException {
    c = new CheckmateEndCondition();
    String testFile = "data/chess/defaultChess.json";
    Builder boardBuilder = new BoardBuilder(new File(testFile));
    players = boardBuilder.getInitialPlayers();
    c.setArgs(Map.of(), new ArrayList<>());
  }

  @Test
  void testNoCheck() {
    assertEquals(GameState.RUNNING, c.isGameOver(players));
  }

  @Test
  void testWhiteWin()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    movePiece("w", "Q", new Location(1, 5));
    movePiece("w", "B", new Location(4, 2));
    removePiece("b","P",new Location(1,5));
    updatePieceMoves();
    assertEquals(GameState.CHECKMATE, c.isGameOver(players));
    assertEquals("w", c.getWinner());
  }

  @Test
  void testBlackWin()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    movePiece("b", "Q", new Location(6, 5));
    movePiece("b", "B", new Location(3, 2));
    removePiece("w","P",new Location(6,5));
    updatePieceMoves();
    System.out.println(printboard());
    assertEquals(GameState.CHECKMATE, c.isGameOver(players));
    assertEquals("b", c.getWinner());
  }

  @Test
  void testBlackCheck() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    movePiece("b", "Q", new Location(6, 5));
    removePiece("w","P",new Location(6,5));
    updatePieceMoves();
    assertEquals(GameState.CHECK, c.isGameOver(players));
  }

  @Test
  void testWhiteCheck() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    movePiece("w", "Q", new Location(1, 5));
    removePiece("b","P",new Location(1,5));
    updatePieceMoves();
    assertEquals(GameState.CHECK, c.isGameOver(players));
  }

  private void movePiece(String team, String pieceType, Location location) {
    for (PlayerInterface player : players) {
      if (player.getTeam().equals(team)) {
        for (PieceInterface piece : player.getPieces()) {
          if (piece.getName().equals(pieceType)) {
            piece.moveTo(location);
            return;
          }
        }
      }
    }
  }

  private void removePiece(String team, String pieceType, Location location)
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    for (PlayerInterface player : players) {
      if (player.getTeam().equals(team)) {
        player.removePiece(location);
      }
    }
  }

  private void updatePieceMoves() {
    for (PlayerInterface player : players) {
      for (PieceInterface piece : player.getPieces()) {
        piece.updateMoves(allPieces());
      }
    }
  }

  private List<PieceInterface> allPieces() {
    List<PieceInterface> allPieces = new ArrayList<>();
    for (PlayerInterface player : players) {
      allPieces.addAll(player.getPieces());
    }
    return allPieces;
  }

  private String printboard() {
    StringBuilder str = new StringBuilder();
    str.append("\t 0\t 1\t 2\t 3\t 4\t 5\t 6\t 7\n");
    for (int i = 0; i < 8; i++) {
      str.append(i + "\t|");
//            str.append("|");
      for (int j = 0; j < 8; j++) {
        Location location = new Location(i, j);
        boolean found = false;

        for (PieceInterface piece : allPieces()) {
          if (piece.getLocation().equals(location)) {
            str.append(piece.toString() + "\t");
            found = true;
          }
        }
        if (!found) {
          str.append("\t");
        }
        str.append("|");
      }
      str.append("\n");
    }
    str.append("__________________________________\n");
    return str.toString();
  }
}
