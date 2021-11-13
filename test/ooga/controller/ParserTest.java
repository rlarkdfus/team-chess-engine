package ooga.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
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
    assertEquals(1, pieces.getJSONArray("position").getInt(0), "x should be 1. got: " + pieces.getJSONArray("position").getInt(0));
    assertEquals(0, pieces.getJSONArray("position").getInt(1), "y should be 0. got: " + pieces.getJSONArray("position").getInt(1));


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

    for (int i = 0; i < testObj.getJSONArray("pieces").length();i++){
      String piece = testObj.getJSONArray("pieces").getJSONObject(i).getString("type");
      List<Integer> location = JSONtoList(testObj.getJSONArray("pieces").getJSONObject(i).getJSONArray("location"));

      jsonString = (String) readFile.invoke(p, new File("data/"+gameType+"/pieces/"+piece+".json"));
      JSONObject pieceObj = (JSONObject) buildJSON.invoke(p,jsonString);
      List<Integer> takeMoves = JSONtoList(pieceObj.getJSONArray("takeMoves"));
      List<Integer> moves = JSONtoList(pieceObj.getJSONArray("moves"));
      List<Integer> initialMoves = JSONtoList(pieceObj.getJSONArray("initialMoves"));



    }

  }
  private List<Integer> JSONtoList(JSONArray jsonArray){
    List<Integer> ret = new ArrayList<>();
    for (int i = 0; i < jsonArray.length(); i++){
      ret.add(jsonArray.getInt(i));
    }
    return ret;
  }
}