package ooga.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
  void testWinBlack() {
    List<PieceInterface> pieces;
    pieces = List.of(new Piece("b","P",new ooga.Location(1,0),new ArrayList<>(), new HashMap<>(), 1),
        new Piece("b","P",new ooga.Location(2,0),new ArrayList<>(), new HashMap<>(), 1));
    boolean ret = l.isGameOver(pieces);
    assertEquals(true, ret);
  }

  @Test
  void testWinWhite() {
    List<PieceInterface> pieces;
    pieces = List.of(new Piece("w","P",new ooga.Location(1,0),new ArrayList<>(), new HashMap<>(), 1),
        new Piece("w","P",new ooga.Location(2,0),new ArrayList<>(), new HashMap<>(), 1));
    boolean ret = l.isGameOver(pieces);
    assertEquals(true, ret);
  }

  @Test
  void testPartiallyWrongTeam() {
    List<PieceInterface> pieces;
    pieces = List.of(new Piece("b","P",new ooga.Location(1,0),new ArrayList<>(), new HashMap<>(), 1),
        new Piece("w","P",new ooga.Location(2,0),new ArrayList<>(), new HashMap<>(), 1));
    boolean ret = l.isGameOver(pieces);
    assertEquals(false, ret);
  }

  @Test
  void testWrongPieceWrongLocation() {
    List<PieceInterface> pieces;
    pieces = List.of(new Piece("b","K",new ooga.Location(0,0),new ArrayList<>(), new HashMap<>(), 1),
        new Piece("b","Q",new ooga.Location(0,1),new ArrayList<>(), new HashMap<>(), 1));
    boolean ret = l.isGameOver(pieces);
    assertEquals(false, ret);
  }

  @Test
  void testRightPieceWrongLocation() {
    List<PieceInterface> pieces;
    pieces = List.of(new Piece("b","P",new ooga.Location(0,0),new ArrayList<>(), new HashMap<>(), 1),
        new Piece("b","P",new ooga.Location(0,1),new ArrayList<>(), new HashMap<>(), 1));
    boolean ret = l.isGameOver(pieces);
    assertEquals(false, ret);
  }

  @Test
  void testWrongPieceRightLocation() {
    List<PieceInterface> pieces;
    pieces = List.of(new Piece("b","K",new ooga.Location(1,0),new ArrayList<>(), new HashMap<>(), 1),
        new Piece("b","N",new ooga.Location(2,0),new ArrayList<>(), new HashMap<>(), 1));
    boolean ret = l.isGameOver(pieces);
    assertEquals(false, ret);
  }

  @Test
  void testPartiallyWrongPiece() {
    List<PieceInterface> pieces;
    pieces = List.of(new Piece("b","P",new ooga.Location(1,0),new ArrayList<>(), new HashMap<>(), 1),
        new Piece("b","N",new ooga.Location(2,0),new ArrayList<>(), new HashMap<>(), 1));
    boolean ret = l.isGameOver(pieces);
    assertEquals(false, ret);
  }

  @Test
  void testPartiallyWrongLocation() {
    List<PieceInterface> pieces;
    pieces = List.of(new Piece("b", "P", new ooga.Location(1, 0), new ArrayList<>(), new HashMap<>(), 1),
        new Piece("b", "P", new ooga.Location(2, 1), new ArrayList<>(), new HashMap<>(), 1));
    boolean ret = l.isGameOver(pieces);
    assertEquals(false, ret);
  }

}
