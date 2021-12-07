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

class TeleportMoveTest {
    List<PieceInterface> allpieces;
    Location endLocation;
    Builder builder;
    PieceInterface pawn;

    @BeforeEach
    void setUp() {
        String testFile = "data/chess/testTeleport.json";
        makePiece(testFile);
    }

    @Test
    void testExecuteMove() {
        pawn = MoveUtility.pieceAt(new Location(6, 0), allpieces);
        for(Move move : pawn.getMoves()) {
            move.updateMoveLocations(pawn, allpieces);
        }
        //should have 2 options to move
        int numLegalMoves = pawn.getEndLocations().size();
        assertEquals(3, numLegalMoves,"should have 3 options to move");

        //should not take piece at new location. assumes that endlocation is valid
        endLocation = new Location(3, 3);
        Move move = pawn.getMove(endLocation);
        move.executeMove(pawn, allpieces, endLocation);
        move.updateMoveLocations(pawn, allpieces);
    }

    private void makePiece(String file) {
        builder = new BoardBuilder(new File(file));
        allpieces = new ArrayList<>();
        for (PlayerInterface player : builder.getInitialPlayers()) {
            for (PieceInterface p : player.getPieces()) {
                allpieces.add(p);
            }
        }
    }
}