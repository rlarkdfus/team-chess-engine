package ooga.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.controller.Builder;
import ooga.controller.InvalidGameConfigException;
import ooga.model.EndConditionHandler.LocationEndCondition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LocationEndConTest {
  LocationEndCondition l;
  Builder boardBuilder;
  @BeforeEach
  void setUp() {
    l = new LocationEndCondition();
//    String testFile = "data/chess/oneBlackPawn.json";
//    boardBuilder = new BoardBuilder2(new File(testFile));
//    boardBuilder.getEndConditionHandler();
    try {
      l.setArgs(Map.of("pieceType", List.of("P","P"),
          "location", List.of("1,0","2,0")), List.of(new Piece("b","P",new ooga.Location(1,0),new ArrayList<>(), new HashMap<>(), 1)));
    } catch (InvalidGameConfigException e) {
    }
  }
//  public Piece(String team, String name, Location location, List<Move> moves, Map<String, Boolean> attributes, int score)

  @Test
  void testWinBlack()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    PlayerInterface player1 = new Player("b");
    player1.addPiece(new Piece("b","P",new ooga.Location(1,0),new ArrayList<>(), new HashMap<>(), 1));
    player1.addPiece(new Piece("b","P",new ooga.Location(2,0),new ArrayList<>(), new HashMap<>(), 1));
    List<PlayerInterface> players = List.of(player1);

    boolean ret = l.isGameOver(players);
    assertEquals(true, ret);
  }

  @Test
  void testWinWhite()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    PlayerInterface player1 = new Player("b");
    player1.addPiece(new Piece("w","P",new ooga.Location(1,0),new ArrayList<>(), new HashMap<>(), 1));
    player1.addPiece(new Piece("w","P",new ooga.Location(2,0),new ArrayList<>(), new HashMap<>(), 1));
    List<PlayerInterface> players = List.of(player1);
    boolean ret = l.isGameOver(players);
    assertEquals(true, ret);
  }

  @Test
  void testPartiallyWrongTeam()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    PlayerInterface player1 = new Player("b");
    PlayerInterface player2 = new Player("w");
    player1.addPiece(new Piece("b","P",new ooga.Location(1,0),new ArrayList<>(), new HashMap<>(), 1));
    player2.addPiece(new Piece("w","P",new ooga.Location(2,0),new ArrayList<>(), new HashMap<>(), 1));
    List<PlayerInterface> players = List.of(player1, player2);
    boolean ret = l.isGameOver(players);
    assertEquals(false, ret);
  }

  @Test
  void testWrongPieceWrongLocation()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    PlayerInterface player1 = new Player("b");
    PlayerInterface player2 = new Player("w");
    player1.addPiece(new Piece("b","K",new ooga.Location(0,0),new ArrayList<>(), new HashMap<>(), 1));
    player1.addPiece(new Piece("b","Q",new ooga.Location(0,6),new ArrayList<>(), new HashMap<>(), 1));
    List<PlayerInterface> players = List.of(player1, player2);
    boolean ret = l.isGameOver(players);
    assertEquals(false, ret);
  }

  @Test
  void testRightPieceWrongLocation()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    PlayerInterface player1 = new Player("b");
    PlayerInterface player2 = new Player("w");
    player1.addPiece(new Piece("b","P",new ooga.Location(0,0),new ArrayList<>(), new HashMap<>(), 1));
    player1.addPiece(new Piece("b","P",new ooga.Location(0,6),new ArrayList<>(), new HashMap<>(), 1));
    List<PlayerInterface> players = List.of(player1, player2);
    boolean ret = l.isGameOver(players);
    assertEquals(false, ret);
  }

  @Test
  void testWrongPieceRightLocation()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    PlayerInterface player1 = new Player("b");
    PlayerInterface player2 = new Player("w");
    player1.addPiece(new Piece("b","K",new ooga.Location(1,0),new ArrayList<>(), new HashMap<>(), 1));
    player1.addPiece(new Piece("b","Q",new ooga.Location(2,0),new ArrayList<>(), new HashMap<>(), 1));
    List<PlayerInterface> players = List.of(player1, player2);
    boolean ret = l.isGameOver(players);
    assertEquals(false, ret);
  }

  @Test
  void testPartiallyWrongPiece()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    PlayerInterface player1 = new Player("b");
    PlayerInterface player2 = new Player("w");
    player1.addPiece(new Piece("b","P",new ooga.Location(1,0),new ArrayList<>(), new HashMap<>(), 1));
    player1.addPiece(new Piece("b","Q",new ooga.Location(2,0),new ArrayList<>(), new HashMap<>(), 1));
    List<PlayerInterface> players = List.of(player1, player2);
    boolean ret = l.isGameOver(players);
    assertEquals(false, ret);
  }

  @Test
  void testPartiallyWrongLocation()
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    PlayerInterface player1 = new Player("b");
    PlayerInterface player2 = new Player("w");
    player1.addPiece(new Piece("b","P",new ooga.Location(1,0),new ArrayList<>(), new HashMap<>(), 1));
    player1.addPiece(new Piece("b","P",new ooga.Location(0,6),new ArrayList<>(), new HashMap<>(), 1));
    List<PlayerInterface> players = List.of(player1, player2);
    boolean ret = l.isGameOver(players);
    assertEquals(false, ret);
  }

}
