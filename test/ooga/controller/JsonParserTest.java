package ooga.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import ooga.controller.Config.JsonParser;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class JsonParserTest {
  JsonParser p;
  @BeforeEach
  void setUp(){
    p = new JsonParser();
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
    assertEquals("#f3dab0", testObj.getJSONArray("boardColors").getString(0), "should be f3dab0. got: " + testObj.getJSONArray("boardColors").getString(0));
    assertEquals("#bb885b", testObj.getJSONArray("boardColors").getString(1), "should be bb885b. got: " + testObj.getJSONArray("boardColors").getString(1));
    assertEquals("w", testObj.getJSONArray("players").getString(0), "should be white(w). got: " + testObj.getJSONArray("players").getString(0));
    assertEquals("b", testObj.getJSONArray("players").getString(1), "should be black(b). got: " + testObj.getJSONArray("players").getString(1));
    assertEquals("data/chess/rules/default.json", testObj.getString("rules"), "should be default. got: " + testObj.getString("rules"));
    assertEquals("data/chess/locations/oneBlackPawn.csv", testObj.getString("csv"),"should be data/chess/locations/oneBlackPawn.csv");
  }

}