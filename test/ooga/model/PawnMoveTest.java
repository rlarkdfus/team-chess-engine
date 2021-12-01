//package ooga.model;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import ooga.Location;
//import ooga.controller.BoardBuilder;
//import ooga.controller.Builder;
//import ooga.model.Moves.Move;
//import ooga.model.Moves.PawnMove;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//public class PawnMoveTest {
//  Move move;
//  PieceInterface p;
//  List<PieceInterface> allpieces;
//  Location endLocation;
//  Builder builder;
//  @BeforeEach
//  void setUp() {
//    String testFile = "data/chess/oneBlackKnight.json";
//    makePiece(testFile);
//    move = new PawnMove();
//    move.setMove(2,0,false,true);
//    makeAllpieces();
//  }
//
//  @Test
//  void testExecuteMove() {
//    List<Location> m;
//
//    //should have 2 options to move
//    move.updateMoveLocations(p, allpieces);
//    m = move.getEndLocations();
//    assertEquals(2, m.size(),"should have 2 options to move");
//
//    //should not take piece at new location. assumes that endlocation is valid
//    endLocation = new Location(2, 0);
//    allpieces.add(new Piece("w","P",new Location(2,0), new ArrayList<>(), new HashMap<>(), 1));
//    move.executeMove(p, allpieces, endLocation);
//    assertEquals(true,endLocation.equals(p.getLocation()),"piece should not move to 2,0. got: "+ p.getLocation());
//    assertEquals(3, allpieces.size(), "allpieces should be same size");
//
//    //after move 2, should only be able to move 1
//    allpieces.remove(2);
//    move.updateMoveLocations(p, allpieces);
//    m = move.getEndLocations();
//    assertEquals(1, m.size(),"should only have 1 option");
//    assertEquals(true, m.get(0).equals(new Location(3,0)),"next move should only move 1 fwd");
//
//
//  }
//
//  private void makePiece(String file) {
//    builder = new BoardBuilder(new File(file));
//    PieceInterface piece = builder.getInitialPlayers().get(1).getPiece(new Location(0,0));
//    assertEquals("b",piece.getTeam());
//    assertEquals("N",piece.getName());
//    this.p = piece;
//  }
//
//  private void makeAllpieces() {
//    allpieces = new ArrayList<>();
//    allpieces.add(new Piece("b","K",new Location(0,7), new ArrayList<>(), new HashMap<>(), 1));
//    allpieces.add(new Piece("w","K",new Location(7,0), new ArrayList<>(), new HashMap<>(), 1));
//  }
//
//}
