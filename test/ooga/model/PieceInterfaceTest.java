package ooga.model;

import ooga.controller.BoardBuilder;
import ooga.controller.Builder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

import ooga.Location;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PieceInterfaceTest {
    private PieceInterface piece;
    private List<PieceInterface> allpieces;
    private Builder builder;

    @BeforeEach
    void setUp() {
        String testFile = "data/chess/oneBlackPawn.json";
        makePiece(testFile);
        makeAllpieces();
    }

    @Test
    void getTeam() {
        Assertions.assertEquals(piece.getTeam(), "b");
    }

    @Test
    void getMoves() {
//        Assertions.assertNotNull(piece.getMoveVectors()); //FIXME
    }

    @Test
    void isLimited() {
        Assertions.assertTrue(piece.isLimited());
    }

    @Test
    void getScore() {
        Assertions.assertEquals(piece.getScore(), 1);
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
        this.piece = piece;
    }

    private void makeAllpieces() {
        allpieces = new ArrayList<>();
        allpieces.add(new Piece("b","K",new Location(0,7), new ArrayList<>(), new HashMap<>(), 1));
        allpieces.add(new Piece("w","K",new Location(7,0), new ArrayList<>(), new HashMap<>(), 1));
    }
}