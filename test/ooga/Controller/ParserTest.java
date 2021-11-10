package ooga.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


class ParserTest extends DukeApplicationTest {
  Parser p;
  @BeforeEach
  void setUp(){
    p = new Parser();
  }

  @Test
  void testParser()throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method readFile = p.getClass().getDeclaredMethod("readFile", File.class);
    readFile.setAccessible(true);
    File f = new File("data/chess/oneBlackPawn.json");
    String jsonString = (String) readFile.invoke(p, f);

    Method buildJSON = p.getClass().getDeclaredMethod("buildJSON", String.class);
    buildJSON.setAccessible(true);
    JSONObject testObj = (JSONObject) buildJSON.invoke(p,jsonString);

    assertEquals("chess", testObj.getString("type"), "should be chess. got: " + testObj.getString("type"));
    assertEquals("square", testObj.getString("board"), "should be square. got: " + testObj.getString("board"));
    assertEquals("8x8", testObj.getString("boardSize"), "should be 8x8. got: " + testObj.getString("boardSize"));
    assertEquals("black", testObj.getJSONArray("boardColors").getString(0), "should be black. got: " + testObj.getJSONArray("boardColors").getString(0));
    assertEquals("white", testObj.getJSONArray("boardColors").getString(1), "should be white. got: " + testObj.getJSONArray("boardColors").getString(1));
    assertEquals("default", testObj.getString("rules"), "should be default. got: " + testObj.getString("rules"));
    JSONObject pieces = testObj.getJSONArray("pieces").getJSONObject(0);
    assertEquals("pawn", pieces.getString("type"), "should be pawn. got: " + pieces.getString("type"));
    assertEquals("black", pieces.getString("color"), "should be black. got: " + pieces.getString("color"));
    assertEquals("1,0", pieces.getString("position"), "should be 1,0. got: " + pieces.getString("position"));

  }

  @Test
  void testPieceParser()throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method readFile = p.getClass().getDeclaredMethod("readFile", File.class);
    readFile.setAccessible(true);
    File f = new File("data/chess/oneBlackPawn.json");
    String jsonString = (String) readFile.invoke(p, f);

    Method buildJSON = p.getClass().getDeclaredMethod("buildJSON", String.class);
    buildJSON.setAccessible(true);
    JSONObject testObj = (JSONObject) buildJSON.invoke(p,jsonString);
    String gameType = testObj.getString("type");
    JSONObject pieces = testObj.getJSONArray("pieces").getJSONObject(0);
    for (int i = 0; i < testObj.getJSONArray("pieces").length();i++){
      String piece = testObj.getJSONArray("pieces").getJSONObject(i).getString("type");
      jsonString = (String) readFile.invoke(p, new File("data/"+gameType+"/"+piece+".json"));
      JSONObject pieceObj = (JSONObject) buildJSON.invoke(p,jsonString);
      assertEquals("src/black/pawn.png", pieceObj.getString("image"), "should be src/black/pawn.png. got: " + pieceObj.getString("image"));
    }

  }
}