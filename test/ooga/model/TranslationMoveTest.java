package ooga.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ooga.Location;
import ooga.controller.BoardBuilder2;
import ooga.controller.Builder;
import ooga.model.Moves.Move;
import ooga.model.Moves.TranslationMove;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TranslationMoveTest {
  Move move;
  PieceInterface p;
  List<PieceInterface> allpieces;
  Location endLocation;
  Builder builder;
  @BeforeEach
  void setUp() {
    String testFile = "data/chess/oneBlackKnight.json";
    makePiece(testFile);
    move = new TranslationMove();
    move.setMove(0,1,true,true);
    makeAllpieces();
  }

  @Test
  void testLimitedUnlimitedMovement() {
    List<Location> endLocation;

    //no other pieces and limited
    move = new TranslationMove();
    move.setMove(0,1,true,true);
    move.updateMoveLocations(p,allpieces);
    endLocation =  move.getEndLocations();
    assertEquals(1, endLocation.size(), "should have 1 end locations because limited");

    //no other pieces and unlimited
    move = new TranslationMove();
    move.setMove(0,1,true,false);
    move.updateMoveLocations(p,allpieces);
    endLocation =  move.getEndLocations();
    assertEquals(6, endLocation.size(), "should have 6 end locations because unlimited and black king");

  }

  @Test
  void testAllyEnemyCollision(){
    List<Location> endLocation;

    //ally piece in the way and unlimited.
    move = new TranslationMove();
    move.setMove(0,1,true,false);
    allpieces.add(new Piece("b","P",new Location(0,2), new ArrayList<>(), new HashMap<>(),1));
    move.updateMoveLocations(p,allpieces);
    endLocation =  move.getEndLocations();
    assertEquals(1, endLocation.size(), "should have 0 end locations because unlimited and ally");

    //enemy piece in the way and unlimited.
    move = new TranslationMove();
    move.setMove(0,1,true,false);
    allpieces.remove(2);
    allpieces.add(new Piece("w","P",new Location(0,2), new ArrayList<>(), new HashMap<>(),1));
    move.updateMoveLocations(p,allpieces);
    endLocation =  move.getEndLocations();
    assertEquals(2, endLocation.size(), "should have 2 end locations because unlimited and enemy");
  }

  private void makePiece(String file) {
    builder = new BoardBuilder2(new File(file));
    PieceInterface piece = builder.getInitialPlayers().get(1).getPiece(new Location(0,0));
    assertEquals("b",piece.getTeam());
    assertEquals("N",piece.getName());
    this.p = piece;
  }

  private void makeAllpieces() {
    allpieces = new ArrayList<>();
    allpieces.add(new Piece("b","K",new Location(0,7), new ArrayList<>(), new HashMap<>(), 1));
    allpieces.add(new Piece("w","K",new Location(7,0), new ArrayList<>(), new HashMap<>(), 1));
  }

}
