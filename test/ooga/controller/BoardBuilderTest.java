package ooga.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import ooga.model.EndConditionHandler.EliminationEndCondition;
import ooga.model.EndConditionHandler.EndConditionInterface;
import ooga.model.Moves.EnPassantMove;
import ooga.model.Moves.Move;
import ooga.model.Moves.PawnMove;
import ooga.model.Moves.TakeOnlyMove;
import ooga.model.Moves.TranslationMove;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class BoardBuilderTest {

  Builder boardBuilder;
  PieceBuilder pieceBuilder;
  JsonParser jp;
  String gameType;
  String team;

  @BeforeEach
  void setUp() {
    String testFile = "data/chess/oneBlackPawn.json";
    try {
      boardBuilder = new BoardBuilder(new File(testFile));
    }catch (Exception e){
      System.out.println(e.getClass());
    }

    getPieceBuilder();
    gameType = "chess";
    team = "b";
    jp = new JsonParser();
  }

  @Test
  void testEndCondition()   {
    //EndGameBuilder Test
    EndConditionInterface endConditionHandler = boardBuilder.getEndConditionHandler();
    boolean correctEndCondition = endConditionHandler.getClass() == new EliminationEndCondition().getClass();
    assertEquals(true, correctEndCondition);
  }

  @Test
  void testInvalidEndCondition()   {
    //EndGameBuilder Test
    String testFile = "data/chess/errorInvalidEndConJson.json";
    assertThrowsExactly(InvalidEndGameConfigException.class,()->{
      boardBuilder.build(new File(testFile));
    } );


  }

  @Test
  void testPlayerList() {
    List<PlayerInterface> players = boardBuilder.getInitialPlayers();
    assertEquals(2, players.size(), "incorrect number of players. expected 2. got: " + players.size());
    for (PlayerInterface p : players){
      if (p.getTeam().equals(team)){
        List<PieceInterface> pieces = p.getPieces();
        assertEquals(1, pieces.size(), "black team should only have 1 piece. got: " + pieces.size());
        PieceInterface piece = pieces.get(0);
        assertEquals("P",piece.getName(),"should be a pawn. got: " + piece.getName());
        assertEquals(1, piece.getScore(), "piece score should be 1. got: " + piece.getScore());
      }else{
        assertEquals("w", p.getTeam(), "other team should be white. got: " + p.getTeam());
        List<PieceInterface> pieces = p.getPieces();
        assertEquals(1, pieces.size(), "white team should have 1 pieces. got: " + pieces.size());
      }
    }
  }

  @Test
  void testPieceList() {
    List<PieceViewBuilder> pieces = boardBuilder.getInitialPieceViews();
    assertEquals(2, pieces.size(), "incorrect number of pieces. expected 2. got: " + pieces.size());
    PieceViewBuilder p = pieces.get(0);
      assertEquals("P",p.getName(),"name should be P. got: " + p.getName());
      assertEquals(team,p.getTeam(),"team should be b. got: " + p.getTeam());
      assertEquals(0,p.getLocation().getRow(),"location row should be 0. got: " + p.getLocation().getRow());
      assertEquals(0,p.getLocation().getCol(),"location col should be 0. got: " + p.getLocation().getCol());

  }

  @Test
  void testGetAttributes()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, FileNotFoundException {
    //PieceBuilder Test
    Method getAttributes = pieceBuilder.getClass()
        .getDeclaredMethod("getAttributes", JSONObject.class);
    getAttributes.setAccessible(true);
    Map<String, Boolean> map = (Map<String, Boolean>) getAttributes.invoke(pieceBuilder,
        getPiece());

    assertEquals(true, map.get("limited"), "limited should be true");
    assertEquals(true, map.get("canTransform"), "canTransform should be true");

  }

  @Test
  void testGetMoves()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException, FileNotFoundException {
    //PieceBuilder Test

    List<Move> actual;
    List<Move> expected;

    Method getMoves = pieceBuilder.getClass()
        .getDeclaredMethod("getMoves", JSONObject.class, String.class);
    getMoves.setAccessible(true);

    actual = (List<Move>) getMoves.invoke(pieceBuilder, getPiece(), team);
    for (Move m : actual){
      System.out.println(m.getClass());
    }
    expected =List.of(new EnPassantMove(),new EnPassantMove(),new TakeOnlyMove(),new TakeOnlyMove(),new TranslationMove(),new PawnMove());

    assertEquals(expected.size(), actual.size(),"wrong number of moves.");

    for (int i = 0; i<actual.size(); i++){
      Class expectedClass = expected.get(i).getClass();
      Class actualClass = actual.get(i).getClass();
      assertEquals(expectedClass, actualClass, "wrong type of Move! expected: " + expectedClass + ". got: " + actualClass);
    }
  }

  @Test
  void testConvertingAPiece() throws FileNotFoundException, InvalidPieceConfigException {
    PlayerInterface player = boardBuilder.getInitialPlayers().get(1);
    List<PieceInterface> pieces = player.getPieces();
    PieceInterface piece = pieces.get(0);

    PieceInterface newPiece = boardBuilder.convertPiece(piece, "Q");

    String expectedTeam = "b";
    String expectedType = "Q";

    String actualTeam = newPiece.getTeam();
    String actualType = newPiece.getName();

    assertEquals(expectedTeam, actualTeam);
    assertEquals(expectedType, actualType);
  }
  @Test
  void testExceptions(){
    String filepath = "data/chess/errorGameJson.json";
    try {
      boardBuilder.build(new File(filepath));
    } catch (Exception e) {
      assertEquals(InvalidGameConfigException.class, e.getClass(),"should throw InvalidGameConfigException");
    }

    filepath = "data/chess/errorCSVJson.json";
    try {
      boardBuilder.build(new File(filepath));
    } catch (Exception e) {
      assertEquals(CsvException.class, e.getClass(),"should throw CsvException");
    }

    filepath = "data/chess/errorPieceJson.json";
    try {
      boardBuilder.build(new File(filepath));
    } catch (Exception e) {
      assertEquals(InvalidPieceConfigException.class, e.getClass(),"should throw InvalidPieceConfigException");
    }

    filepath = "data/chess/errorPlayerJson.json";
    try {
      boardBuilder.build(new File(filepath));
    } catch (Exception e) {
      assertEquals(PlayerNotFoundException.class, e.getClass(),"should throw PlayerNotFoundException");
    }
  }

  private JSONObject getPiece()
      throws NoSuchFieldException, IllegalAccessException, FileNotFoundException {
    Field f = boardBuilder.getClass().getDeclaredField("csvData");
    f.setAccessible(true);
    List<List<String>> parsedCSV = (List<List<String>>) f.get(boardBuilder);
    String[] square = parsedCSV.get(0).get(0).split("_");
    String pieceType = square[1];
    String pieceJsonPath = "data/" + gameType + "/pieces/" + pieceType + ".json";
    return jp.loadFile(new File(pieceJsonPath));
  }

  private void getPieceBuilder() {
    try {
      Field f = boardBuilder.getClass().getDeclaredField("pieceBuilder");
      f.setAccessible(true);
      pieceBuilder = (PieceBuilder) f.get(boardBuilder);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }
}
