package ooga.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ooga.Location;
import ooga.controller.BoardBuilder;
import ooga.controller.Builder;
import ooga.model.Moves.JumpMove;
import ooga.model.Moves.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JumpMoveTest {
  Move move;
  PieceInterface p;
  List<PieceInterface> allpieces;
  Location endLocation;
  Builder builder;
  @BeforeEach
  void setUp() {
    String testFile = "data/chess/oneBlackKnight.json";
    makePiece(testFile);
    move = new JumpMove();
    move.setMove(2,1,true,true);
    makeAllpieces();
  }

  @Test
  void testExecuteMove() {
    //move to new location. assumes that endlocation is valid
    endLocation = new Location(2, 1);
    move.executeMove(p, allpieces, endLocation);
    assertEquals(true,endLocation.equals(p.getLocation()),"piece should move to 2,1. got: "+ p.getLocation());

    //take piece at new location. assumes that endlocation is valid
    endLocation = new Location(2+2, 1+1);
    allpieces.add(new Piece("w","P",new Location(4,2), new ArrayList<>(), new HashMap<>(), 1));
    move.executeMove(p, allpieces, endLocation);
    assertEquals(true,endLocation.equals(p.getLocation()),"piece should move to 4,2. got: "+ p.getLocation());
    assertEquals(2, allpieces.size(), "allpieces should've lost a piece. should only have 2 kings");
  }

  @Test
  void testLimitedUnlimitedMovement() {
    List<Location> endLocationList;

    //no other pieces and limited
    move = new JumpMove();
    move.setMove(0, 1, true, true);
    move.updateMoveLocations(p, allpieces);
    endLocationList = move.getEndLocations();
    assertEquals(1, endLocationList.size(), "should have 1 end locations because limited");

    //no other pieces and unlimited
    move = new JumpMove();
    move.setMove(2, 1, true, false);
    move.updateMoveLocations(p, allpieces);
    endLocationList = move.getEndLocations();
    assertEquals(3, endLocationList.size(), "should have 3 end locations because unlimited");
  }

  @Test
  void testJump(){
    List<Location> endLocationList;

    //jump over ally pieces and unlimited
    move = new JumpMove();
    allpieces.add(new Piece("b","P",new Location(0,1), new ArrayList<>(), new HashMap<>(),1));
    move.setMove(0,2,true,false);
    move.updateMoveLocations(p,allpieces);
    endLocationList =  move.getEndLocations();
    assertEquals(3, endLocationList.size(), "should have 3 end locations because unlimited and jumps over ally");

    //jump over enemy pieces and unlimited
    move = new JumpMove();
    allpieces.remove(2);
    allpieces.add(new Piece("w","P",new Location(0,1), new ArrayList<>(), new HashMap<>(),1));
    move.setMove(0,2,true,false);
    move.updateMoveLocations(p,allpieces);
    endLocationList =  move.getEndLocations();
    assertEquals(3, endLocationList.size(), "should have 3 end locations because unlimited and jumps over ally");

  }

  @Test
  void testAllyEnemyCollision(){
    List<Location> endLocationList;

    //ally piece in the way and unlimited.
    move = new JumpMove();
    move.setMove(2,1,true,false);
    allpieces.add(new Piece("b","P",new Location(2,1), new ArrayList<>(), new HashMap<>(),1));
    move.updateMoveLocations(p,allpieces);
    endLocationList =  move.getEndLocations();
    assertEquals(0, endLocationList.size(), "should have 0 end locations because unlimited and ally");

    //enemy piece in the way and limited.
    move = new JumpMove();
    move.setMove(0,1,true,true);
    allpieces.remove(2);
    allpieces.add(new Piece("w","P",new Location(0,2), new ArrayList<>(), new HashMap<>(),1));
    move.updateMoveLocations(p,allpieces);
    endLocationList =  move.getEndLocations();
    assertEquals(1, endLocationList.size(), "should have 1 end locations because limited and enemy");
  }

  private void makePiece(String file) {
    builder = new BoardBuilder(new File(file));
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
