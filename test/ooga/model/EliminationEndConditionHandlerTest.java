package ooga.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.controller.BoardBuilder2;
import ooga.controller.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EliminationEndConditionHandlerTest {
  EliminationEndConditionHandler e;
  List<PlayerInterface> players;
  @BeforeEach
  void setUp() {
    e = new EliminationEndConditionHandler();
    String testFile = "data/chess/defaultChess.json";
    Builder boardBuilder = new BoardBuilder2(new File(testFile));
    players = boardBuilder.getInitialPlayers();
    
    e.setArgs(Map.of("pieceType", List.of("P","K"), "amount", List.of("2", "1")),getAllPieces());
  }
  @Test
  void testSetArgs() throws NoSuchFieldException, IllegalAccessException {
    Field f;
    f = e.getClass().getDeclaredField("previousTurnPieces");
    f.setAccessible(true);
    List<PieceInterface> previousTurnPieces = (List<PieceInterface>)f.get(e);
    assertEquals(getAllPieces().size(), previousTurnPieces.size());

    f = e.getClass().getDeclaredField("piecesToEliminate");
    f.setAccessible(true);
    Map<String, Integer> piecesToEliminate = ( Map<String, Integer>)f.get(e);
    for (String s : piecesToEliminate.keySet()){
      System.out.println(s);
    }
    assertEquals(4, piecesToEliminate.keySet().size());
  }

  @Test
  void testBlackWin() {
    List<PieceInterface> pieces = getAllPieces();
    removePieces(pieces, List.of("b_K","b_P","b_P"));
    boolean ret = e.isGameOver(pieces);
    assertEquals(true, ret,"game should be over");
  }

  @Test
  void testWhiteWin() {
    List<PieceInterface> pieces = getAllPieces();
    removePieces(pieces, List.of("w_K","w_P","w_P"));
    boolean ret = e.isGameOver(pieces);
    assertEquals(true, ret,"game should end");
  }

  @Test
  void testAlmostWin() {
    List<PieceInterface> pieces = getAllPieces();
    removePieces(pieces, List.of("b_K","b_P"));
    boolean ret = e.isGameOver(pieces);
    assertEquals(false, ret,"still need to remove 1 more b_pawn");
  }

  @Test
  void testBothSidesPartiallyWin() {
    List<PieceInterface> pieces = getAllPieces();
    removePieces(pieces, List.of("b_K","b_P","w_P"));
    boolean ret = e.isGameOver(pieces);
    assertEquals(false, ret,"still need to remove 1 more b_pawn, 1 w_pawn, 1 w_king");
  }

  private List<PieceInterface> getAllPieces(){
    List<PieceInterface> allpieces = new ArrayList<>();
    for (PlayerInterface p : players){
      allpieces.addAll(p.getPieces());
    }
    return allpieces;
  }

  private void removePieces(List<PieceInterface> pieces, List<String> ToBeRemoved) {
    for (String piece : ToBeRemoved){
      String team = piece.split("_")[0];
      String pieceType = piece.split("_")[1];
      for (PieceInterface p : pieces) {
        if (p.getTeam().equals(team) && p.getName().equals(pieceType)) {
          pieces.remove(p);
          break;
        }
      }
    }
  }
}