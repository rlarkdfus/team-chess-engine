package ooga.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.Location;
import ooga.model.LocationEndConditionHandler;
import ooga.model.Piece;
import ooga.model.PieceInterface;
import ooga.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EliminationEndConditionHandlerTest {
  EliminationEndConditionHandler e;
  Player player;
  @BeforeEach
  void setUp() {
    e = new EliminationEndConditionHandler();
    player = new Player("b");
     List <PieceInterface> pieces = List.of(new Piece("b","P",new Location(1,0),new ArrayList<>(), new HashMap<>(), 1),
        new Piece("b","P",new Location(2,0),new ArrayList<>(), new HashMap<>(), 1));
    for(PieceInterface pieceInterface : pieces) {
      player.addPiece(pieceInterface);
    }
    e.setArgs(Map.of("pieceType", List.of("P","K"),
        "amount", List.of("2", "1")), List.of(new Player("b")));
  }

  @Test
  void testWinBlack() {
    List<PieceInterface> pieces;
    pieces = List.of();
    Player player1 = new Player("b");
    boolean ret = e.isGameOver(List.of(player1));
    assertEquals(true, ret);
  }

//  @Test
//  void testWinWhite() {
//    List<PieceInterface> pieces;
//    pieces = List.of(new Piece("w","P",new Location(1,0),new ArrayList<>(), new HashMap<>(), 1),
//        new Piece("w","P",new Location(2,0),new ArrayList<>(), new HashMap<>(), 1));
//    boolean ret = l.isGameOver(pieces);
//    assertEquals(true, ret);
//  }
}