package ooga.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import ooga.model.MoveVector;
import ooga.model.Vector;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class BoardBuilderTest {

  BoardBuilder boardBuilder;
  JsonParser jp;
  LocationParser lp;
  JSONObject parsedFile;
  List<List<String>> parsedCSV;
  String gameType;

  @BeforeEach
  void setUp() {
    boardBuilder = new BoardBuilder();
    lp = new LocationParser();
    jp = new JsonParser();
    String testFile = "data/chess/oneBlackPawn.json";
    parsedFile = jp.loadFile(new File(testFile));
    gameType = parsedFile.getString("type");
    try {
      parsedCSV = lp.getInitialLocations(parsedFile.getString("csv"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testPlayerList() {

  }

  @Test
  void testCorrectImagePath() {
    String[] square = parsedCSV.get(0).get(0).split("_");
    String pieceColor = square[0];
    String pieceType = square[1];
    String pieceImagePath =
        "src/images/" + BoardBuilder.DEFAULT_STYLE + "/" + pieceColor + pieceType + ".png";
    assertEquals("src/images/companion/bP.png", pieceImagePath, "incorrect image path");
  }

  @Test
  void testGetAttributes()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    Method getAttributes = boardBuilder.getClass()
        .getDeclaredMethod("getAttributes", JSONObject.class);
    getAttributes.setAccessible(true);
    Map<String, Boolean> map = (Map<String, Boolean>) getAttributes.invoke(boardBuilder,
        getPiece());

    assertEquals(true, map.get("limited"), "limited should be true");
    assertEquals(true, map.get("canEnPassant"), "canEnPassant should be true");
    assertEquals(true, map.get("canTransform"), "canTransform should be true");
    assertEquals(false, map.get("canCastle"), "canCastle should be false");

  }

  @Test
  void testGetMovevector()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    Method getMoveVector = boardBuilder.getClass()
        .getDeclaredMethod("getMoveVector", JSONObject.class, String.class);
    getMoveVector.setAccessible(true);
    MoveVector moveVector = (MoveVector) getMoveVector.invoke(boardBuilder, getPiece(), "b");

    List<Vector> actualMove = moveVector.getMoveVectors();
    List<Vector> actualTake = moveVector.getTakeVectors();
    List<Vector> actualInitial = moveVector.getInitialVectors();
    List<List<Vector>> actual = List.of(actualMove, actualTake, actualInitial);

    List<Vector> expectedMove = List.of(new Vector(1, 0));
    List<Vector> expectedTake = List.of(new Vector(1, -1), new Vector(1, 1));
    List<Vector> expectedInitial = List.of(new Vector(2, 0), new Vector(1, 0));
    List<List<Vector>> expected = List.of(expectedMove, expectedTake, expectedInitial);

    List<String> currList = List.of("move", "take", "initial");
    for (int idx = 0; idx < 3; idx++) {
      List<Vector> list1 = actual.get(idx);
      List<Vector> list2 = expected.get(idx);
      for (int v = 0; v < list1.size(); v++) {

        assertEquals(list1.get(v).getdCol(), list2.get(v).getdCol(),
            currList.get(idx) + " vector :" + v + " column is wrong");
        assertEquals(list1.get(v).getdRow(), list2.get(v).getdRow(),
            currList.get(idx) + " vector :" + v + " row is wrong");

      }
    }
  }

  private JSONObject getPiece() {
    String[] square = parsedCSV.get(0).get(0).split("_");
    String pieceType = square[1];
    String pieceJsonPath = "data/" + gameType + "/pieces/" + pieceType + ".json";
    return jp.loadFile(new File(pieceJsonPath));
  }
}
