package ooga.model;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ooga.Location;
import ooga.controller.Config.BoardBuilder;
import ooga.controller.Config.Builder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerInterfaceTest {
    private PlayerInterface player;
    private PieceInterface piece;
    private List<PieceInterface> allpieces;
    private Builder builder;
    @BeforeEach
    void setUp() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        player = new Player("white");
        String testFile = "data/chess/oneBlackPawn.json";
        makePiece(testFile);
        makeAllpieces();
        player.addPiece(piece);
    }

    @Test
    void removePiece() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        player.removePiece(piece);
        Assertions.assertEquals(player.getPieces().size(), 0);
    }

    @Test
    void getPieces() {
        Assertions.assertNotNull(player.getPieces());
    }

    @Test
    void addPiece(){
        player.addPiece(piece);
        Assertions.assertEquals(player.getPieces().size(), 1);
    }

    @Test
    void getTeam() {
        Assertions.assertEquals(player.getTeam(), "white");
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
        allpieces.add(new Piece("b","K",new Location(0,7), new ArrayList<>(), 1));
        allpieces.add(new Piece("w","K",new Location(7,0), new ArrayList<>(), 1));
    }
}