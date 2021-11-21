package ooga.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.Location;
import ooga.model.Moves.JumpMove;
import ooga.model.Moves.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JumpMoveTest {
  Move move;
  PieceInterface p;
  List<PieceInterface> allpieces;
  Location endLocation;
  @BeforeEach
  void setUp() {
//    String team, String name, Location location, List<Move> moves, Map<String, Boolean> attributes, int score
    String team = "b";
    String name = "P";
    int r = 4;
    int c = 4;
    Location location = new Location(r,c);
    Move m = new JumpMove();
    m.setMove(2,1,true,true);
    List<Move> moves = List.of(m);
    Map<String, Boolean> attributes = Map.of("limited",true);
    int score = 1;
    p = new Piece(team, name, location, moves, attributes,score);
    move = new JumpMove();
    move.setMove(2,1,true,true);
    allpieces = new ArrayList<>();
  }

  @Test
  void testExecuteMove() {
    //move to empty square
    endLocation = new Location()
    move.executeMove(p, allpieces, endLocation);

  }
}
