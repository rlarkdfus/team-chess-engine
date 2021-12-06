package ooga.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import ooga.controller.Config.BoardBuilder;
import ooga.controller.Config.Builder;
import ooga.controller.Config.CsvException;
import ooga.controller.Config.InvalidEndGameConfigException;
import ooga.controller.Config.InvalidGameConfigException;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.controller.Config.InvalidPowerupsConfigException;
import ooga.controller.Config.JsonParser;
import ooga.controller.Config.PieceBuilder;
import ooga.controller.Config.PieceViewBuilder;
import ooga.controller.Config.PlayerNotFoundException;
import ooga.model.EndConditionHandler.CheckmateEndCondition;
import ooga.model.EndConditionHandler.EliminationEndCondition;
import ooga.model.EndConditionHandler.EndConditionInterface;
import ooga.model.EndConditionHandler.EndConditionRunner;
import ooga.model.Moves.EnPassantMove;
import ooga.model.Moves.Move;
import ooga.model.Moves.PawnMove;
import ooga.model.Moves.PromoteMove;
import ooga.model.Moves.TakeOnlyMove;
import ooga.model.Moves.TranslationMove;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;
import ooga.model.Powerups.PowerupInterface;
import ooga.model.Powerups.TimerPowerup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class BoardBuilderTest {

  Builder boardBuilder;
  JsonParser jp;
  String team;

  @BeforeEach
  void setUp() {
    String testFile = "data/chess/oneBlackPawn.json"; //should have 1 pawn and king per side
    try {
      boardBuilder = new BoardBuilder(new File(testFile));
    } catch (Exception e) {
      System.out.println(e.getClass());
    }

    team = "b";
    jp = new JsonParser();
  }

  @Test
  void asdf(){
    Logger logger = LogManager.getLogger(this.getClass());

    logger.debug("Debug log message");
    logger.info("Info log message");
    logger.warn("Warn log message");
    logger.error("Error log message");
    logger.fatal("Fatal log message");
    logger.trace("Trace log message");

  }
  @Test
  void testEndCondition() throws NoSuchFieldException, IllegalAccessException {
    //EndGameBuilder Test
    EndConditionRunner endConditionHandler = boardBuilder.getEndConditionHandler();
    Field f = endConditionHandler.getClass().getDeclaredField("endConditions");
    f.setAccessible(true);
    List<EndConditionInterface> endConditions = (List<EndConditionInterface>) f.get(
        endConditionHandler);
    List<Class> endConditionTypes = new ArrayList<>();
    for (EndConditionInterface e : endConditions) {
      endConditionTypes.add(e.getClass());
    }
    assertEquals(true, endConditionTypes.contains(EliminationEndCondition.class));
    assertEquals(true, endConditionTypes.contains(CheckmateEndCondition.class));

  }

  @Test
  void testPowerUps()
      throws InvalidPowerupsConfigException, FileNotFoundException, InvalidEndGameConfigException, PlayerNotFoundException, InvalidPieceConfigException, InvalidGameConfigException, CsvException {
    //EndGameBuilder Test
    boardBuilder.build(new File("data/chess/testTimerPowerup.json"));
    List<PowerupInterface> powerups = boardBuilder.getPowerupsHandler();
    List<Class> powerupTypes = new ArrayList<>();
    for (PowerupInterface pu : powerups) {
      powerupTypes.add(pu.getClass());
    }
    assertEquals(true, powerupTypes.contains(TimerPowerup.class));

  }

  @Test
  void testPlayerList() {
    List<PlayerInterface> players = boardBuilder.getInitialPlayers();
    assertEquals(2, players.size(),
        "incorrect number of players. expected 2. got: " + players.size());
    for (PlayerInterface p : players) {
      if (p.getTeam().equals(team)) {
        List<String> pieceTypes = new ArrayList<>();
        for (PieceInterface piece : p.getPieces()) {
          pieceTypes.add(piece.getName());
        }
        assertEquals(true, pieceTypes.contains("P"), "should have pawn");
        assertEquals(true, pieceTypes.contains("K"), "should have king");

      } else {
        assertEquals("w", p.getTeam(), "other team should be white. got: " + p.getTeam());
        List<PieceInterface> pieces = p.getPieces();
        assertEquals(2, pieces.size(), "white team should have 2 pieces. got: " + pieces.size());
      }
    }
  }

  @Test
  void testPieceList() {
    List<PieceViewBuilder> pieces = boardBuilder.getInitialPieceViews();
    assertEquals(4, pieces.size(), "incorrect number of pieces. expected 4. got: " + pieces.size());
    PieceViewBuilder p;

    //black pawn
    p = pieces.get(0);
    assertEquals("P", p.getName(), "name should be P. got: " + p.getName());
    assertEquals("b", p.getTeam(), "team should be b. got: " + p.getTeam());
    assertEquals(0, p.getLocation().getRow(),
        "location row should be 0. got: " + p.getLocation().getRow());
    assertEquals(0, p.getLocation().getCol(),
        "location col should be 0. got: " + p.getLocation().getCol());

    p = pieces.get(1);
    assertEquals("K", p.getName(), "name should be K. got: " + p.getName());
    assertEquals("b", p.getTeam(), "team should be b. got: " + p.getTeam());
    assertEquals(0, p.getLocation().getRow(),
        "location row should be 0. got: " + p.getLocation().getRow());
    assertEquals(7, p.getLocation().getCol(),
        "location col should be 7. got: " + p.getLocation().getCol());

    p = pieces.get(2);
    assertEquals("K", p.getName(), "name should be K. got: " + p.getName());
    assertEquals("w", p.getTeam(), "team should be w. got: " + p.getTeam());
    assertEquals(7, p.getLocation().getRow(),
        "location row should be 7. got: " + p.getLocation().getRow());
    assertEquals(0, p.getLocation().getCol(),
        "location col should be 0. got: " + p.getLocation().getCol());

    p = pieces.get(3);
    assertEquals("P", p.getName(), "name should be P. got: " + p.getName());
    assertEquals("w", p.getTeam(), "team should be w. got: " + p.getTeam());
    assertEquals(7, p.getLocation().getRow(),
        "location row should be 7. got: " + p.getLocation().getRow());
    assertEquals(7, p.getLocation().getCol(),
        "location col should be 7. got: " + p.getLocation().getCol());
  }

  @Test
  void testGetMoves()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, FileNotFoundException {
    //PieceBuilder Test

    List<Move> actual;

    Method getMoves = PieceBuilder.class
        .getDeclaredMethod("getMoves", JSONObject.class);
    getMoves.setAccessible(true);

    actual = (List<Move>) getMoves.invoke(PieceBuilder.class, getPiece().getJSONObject("moves"));
    List<Class> moveClassTypes = new ArrayList<>();

    for (Move m : actual) {
      moveClassTypes.add(m.getClass());
    }
    List<Class> expected = List.of(EnPassantMove.class, EnPassantMove.class, TakeOnlyMove.class,
        TakeOnlyMove.class, PromoteMove.class, TranslationMove.class, PawnMove.class);

    assertEquals(7, actual.size(), "wrong number of moves.");

    assertEquals(true, moveClassTypes.containsAll(expected),
        "wrong type of Move! expected: " + expected.stream().toArray() + ". got: " + actual.stream().toArray());
  }

  @Test
  void testExceptions() {
    assertThrowsExactly(InvalidGameConfigException.class, () -> {
      boardBuilder.build(new File("data/chess/errorGameJson.json"));
    });

    assertThrowsExactly(CsvException.class, () -> {
      boardBuilder.build(new File("data/chess/errorCSVJson.json"));
    });

    assertThrowsExactly(InvalidPieceConfigException.class, () -> {
      boardBuilder.build(new File("data/chess/errorPieceJson.json"));
    });

    assertThrowsExactly(PlayerNotFoundException.class, () -> {
      boardBuilder.build(new File("data/chess/errorPlayerJson.json"));
    });

    assertThrowsExactly(InvalidPowerupsConfigException.class, () -> {
      boardBuilder.build(new File("data/chess/errorPowerupJson.json"));
    });


  }

  private JSONObject getPiece()
      throws NoSuchFieldException, IllegalAccessException, FileNotFoundException {
    Field f = boardBuilder.getClass().getDeclaredField("csvData");
    f.setAccessible(true);
    List<List<String>> parsedCSV = (List<List<String>>) f.get(boardBuilder);
    String[] square = parsedCSV.get(0).get(0).split("_");
    String team = square[0];
    String pieceType = square[1];
    String pieceJsonPath = "data/chess/pieces/" + team + pieceType + ".json";
    return jp.loadFile(new File(pieceJsonPath));
  }
}
