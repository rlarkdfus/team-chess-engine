package ooga.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


class ParserTest extends DukeApplicationTest {
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
    assertEquals("white", testObj.getJSONArray("players").getString(0), "should be white. got: " + testObj.getJSONArray("players").getString(0));
    assertEquals("black", testObj.getJSONArray("players").getString(1), "should be black. got: " + testObj.getJSONArray("players").getString(1));
    assertEquals("default", testObj.getString("rules"), "should be default. got: " + testObj.getString("rules"));
    assertEquals("oneBlackPawn.csv", testObj.getString("csv"),"should be oneBlackPawn.csv");
  }

//  @Test
//  void testPieceParser()throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//    Method readFile = p.getClass().getDeclaredMethod("readFile", File.class);
//    readFile.setAccessible(true);
//    File f = new File("data/chess/oneBlackPawn.json");
//    String jsonString = (String) readFile.invoke(p, f);
//
//    Method buildJSON = p.getClass().getDeclaredMethod("buildJSON", String.class);
//    buildJSON.setAccessible(true);
//    JSONObject testObj = (JSONObject) buildJSON.invoke(p,jsonString);
//    String gameType = testObj.getString("type");
//
//    for (int i = 0; i < testObj.getJSONArray("pieces").length();i++){
//      String piece = testObj.getJSONArray("pieces").getJSONObject(i).getString("type");
//      String color = testObj.getJSONArray("pieces").getJSONObject(i).getString("color");
//      String location = testObj.getJSONArray("pieces").getJSONObject(i).getString("location");
//      String imagePath = "src/"+color+"/"+piece+".png";
//
//      assertEquals("black",color,"should be black");
//      assertEquals("src/black/black_pawn.png",imagePath,"should be src/black/black_pawn.png");
//      assertEquals("1,0",location,"should be 1,0");
//
//      String pieceJSONString = (String) readFile.invoke(p, new File("data/"+gameType+"/pieces/"+piece+".json"));
//      JSONObject pieceObj = (JSONObject) buildJSON.invoke(p,pieceJSONString);
//
//      List<String> takeMoves = JSONtoList(pieceObj.getJSONArray("takeMoves"));
//      boolean checkTakeMoves = takeMoves.containsAll(List.of("1,-1","1,1"));
//
//      List<String> moves = JSONtoList(pieceObj.getJSONArray("moves"));
//      boolean checkMoves = moves.containsAll(List.of("1,0"));
//
//      List<String> initialMoves = JSONtoList(pieceObj.getJSONArray("initialMoves"));
//      boolean checkInitialMoves = initialMoves.containsAll(List.of("2,0","1,0"));
//
//      Map<String,Boolean> attributes = JSONtoMap(pieceObj.getJSONObject("attributes"));
//      boolean checkAttributes = attributes.keySet().containsAll(List.of("limited","canEnPassant","canTransform","canCastle"));
//      assertEquals(true, checkTakeMoves,"not all takemoves are there");
//      assertEquals(true, checkMoves,"not all moves are there");
//      assertEquals(true, checkInitialMoves,"not all initialmoves are there");
//      assertEquals(true, checkAttributes, "not all attributes are there");
//
//
//
//    }
//
//  }
  private List<String> JSONtoList(JSONArray jsonArray){
    List<String> ret = new ArrayList<>();
    for (int i = 0; i < jsonArray.length(); i++){
      ret.add(jsonArray.getString(i));
    }
    return ret;
  }

  private Map<String,Boolean> JSONtoMap(JSONObject jsonObj){
    Map<String,Boolean> ret = new HashMap<>();
    for (String key : jsonObj.keySet()){
      ret.put(key,jsonObj.getBoolean(key));
    }
    return ret;
  }
}