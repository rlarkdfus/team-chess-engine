package ooga.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import ooga.model.PieceInterface;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class BoardBuilderTest {

  BoardBuilder b;
  JsonParser jp;
  LocationParser lp;
  JSONObject parsedFile;
  List<List<String>> parsedCSV;
  JSONObject piece;
  @BeforeEach
  void setUp(){
    b = new BoardBuilder();
    lp = new LocationParser();
    jp = new JsonParser();
    String testFile = "data/chess/oneBlackPawn.json";
    parsedFile = jp.loadFile(new File(testFile));
  }

  @Test
  void testBuild(){
    PieceInterface[][] ret;
    PieceInterface piece;
    try {
      ret = b.build(parsedFile);
      piece = ret[0][0];
      assertEquals("b", piece.getTeam(),"team should be black");
//      assertEquals("pawn", piece.getPieceName(),"name should be pawn");

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
  @Test
  void testCorrectImagePath(){
    String[] square = parsedCSV.get(0).get(0).split("_");
    String pieceColor = square[0];
    String pieceType = square[1];
    String pieceImagePath = "src/images/"+BoardBuilder.DEFAULT_STYLE+"/"+ pieceColor + pieceType + ".png";
    assertEquals("src/images/companion/bP.png",pieceImagePath,"incorrect image path");
  }
  @Test
  void testGetAttributes()throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method readFile = b.getClass().getDeclaredMethod("getAttributes", JSONObject.class);
    readFile.setAccessible(true);
    JSONObject attributes = parsedFile.getJSONObject("attributes");
    Map<String, Boolean> map = (Map<String, Boolean>) readFile.invoke(jp, attributes);

//    assertEquals();
  }
}
