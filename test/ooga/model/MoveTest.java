package ooga.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import ooga.Location;
import ooga.controller.BoardBuilder2;
import ooga.controller.Builder;
import ooga.model.Moves.JumpMove;
import ooga.model.Moves.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MoveTest {
  Move move;
  PieceInterface p;
  List<PieceInterface> allpieces;
  Location endLocation;
  Builder builder;
  @BeforeEach
  void setUp() {
    String testFile = "data/chess/oneBlackPawn.json";
    p = makePiece(testFile);
    move = new JumpMove();
    move.setMove(2,1,true,true);
    allpieces = new ArrayList<>();
  }

  @Test
  void testExecuteMove() {
    //move to new location. assumes that endlocation is valid
    endLocation = new Location(4+2, 4+1);
    move.executeMove(p, allpieces, endLocation);
    assertEquals(true,endLocation.equals(p.getLocation()),"piece should move to 6,5");
  }

  @Test
  void testUpdateMoveLocations() {
    //move to new location. assumes that endlocation is valid
    endLocation = new Location(4+2, 4+1);
//    move.updateMoveLocations();
//    assertEquals(true,endLocation.equals(p.getLocation()),"piece should move to 6,5");
  }

  private PieceInterface makePiece(String file) {
    builder = new BoardBuilder2(new File(file));
    PieceInterface piece = builder.getInitialPlayers().get(1).getPiece(new Location(0,0));
    assertEquals("b",piece.getTeam());
    return piece;
  }


}
