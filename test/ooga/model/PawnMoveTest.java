package ooga.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ooga.Location;
import ooga.controller.BoardBuilder;
import ooga.controller.Builder;
import ooga.model.Moves.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PawnMoveTest {
//  Move move;
  PieceInterface pawn;
  List<PieceInterface> allpieces;
  Location endLocation;
  Builder builder;
  @BeforeEach
  void setUp() {
    String testFile = "data/chess/oneBlackPawn.json";
    makePiece(testFile);
//    move = new PawnMove();
//    move.setMove(2,0,false,true);
    makeAllpieces();
  }

  @Test
  void testExecuteMove() {
    for(Move move : pawn.getMoves()) {
      move.updateMoveLocations(pawn, allpieces);
    }
    //should have 2 options to move
    int numLegalMoves = pawn.getEndLocations().size();
    assertEquals(2, numLegalMoves,"should have 2 options to move");

    //should not take piece at new location. assumes that endlocation is valid
    endLocation = new Location(2, 0);
    Move move = pawn.getMove(endLocation);
    move.executeMove(pawn, allpieces, endLocation);
    move.updateMoveLocations(pawn, allpieces);

    numLegalMoves = pawn.getEndLocations().size();
    assertEquals(1, numLegalMoves,"should have 1 option to move");
  }

  private void makePiece(String file) {
    builder = new BoardBuilder(new File(file));
    PieceInterface piece = null;
    for (PieceInterface playerPiece : builder.getInitialPlayers().get(1).getPieces()) {
      if (playerPiece.getLocation().equals(new Location(0, 0))){
        piece = playerPiece;
        break;
      }
    }
    assertEquals("b",piece.getTeam());
    assertEquals("P",piece.getName());
    this.pawn = piece;
  }

  private void makeAllpieces() {
    allpieces = new ArrayList<>();
    allpieces.add(new Piece("b","K",new Location(0,7), new ArrayList<>(), new HashMap<>(), 1));
    allpieces.add(new Piece("w","K",new Location(7,0), new ArrayList<>(), new HashMap<>(), 1));
  }

}
