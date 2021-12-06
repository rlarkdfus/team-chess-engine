package ooga.model.Moves;

import ooga.Location;
import ooga.controller.Config.BoardBuilder;
import ooga.controller.Config.Builder;
import ooga.model.Piece;
import ooga.model.PieceInterface;
import ooga.model.Player;
import ooga.model.PlayerInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CastleMoveTest {
    List<PieceInterface> allpieces;
    Location endLocation;
    Builder builder;

    @BeforeEach
    void setUp() {
        String testFile = "data/chess/castling.json";
        makePiece(testFile);
    }

    @Test
    void testExecuteMove() {
        PieceInterface wK = MoveUtility.pieceAt(new Location(7, 4), allpieces);
        PieceInterface bK = MoveUtility.pieceAt(new Location(0, 4), allpieces);

        for(Move move : wK.getMoves()){
            move.updateMoveLocations(wK, allpieces);
        }

        //should have 2 options to move
        int numLegalMoves = wK.getEndLocations().size();
        assertEquals(6, numLegalMoves,"should have 5 options to move");

        //should not take piece at new location. assumes that endlocation is valid
        endLocation = new Location(7, 2);
        Move move = wK.getMove(endLocation);
        System.out.println(move);
        move.executeMove(wK, allpieces, endLocation);
        move.updateMoveLocations(wK, allpieces);

        System.out.println(wK.getEndLocations());
        for(PieceInterface p : allpieces){
            System.out.println(p.getName() + " " + p.getTeam() + " " + p.getLocation());
        }

        Assertions.assertTrue(new Location(7, 2).equals(wK.getLocation()));
        Assertions.assertTrue(MoveUtility.pieceAt(new Location(7, 3), allpieces).getName().equals("R"));
        Assertions.assertTrue(MoveUtility.pieceAt(new Location(7, 3), allpieces).getTeam().equals("w"));
    }

    private void makePiece(String file) {
        builder = new BoardBuilder(new File(file));
        PieceInterface piece = null;
        allpieces = new ArrayList<>();
        for (PlayerInterface player : builder.getInitialPlayers()) {
            for (PieceInterface p : player.getPieces()) {
                allpieces.add(p);
            }
        }
    }
}