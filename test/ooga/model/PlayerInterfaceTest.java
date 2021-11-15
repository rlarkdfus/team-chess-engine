package ooga.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerInterfaceTest {
    private PlayerInterface player;
    private PieceInterface piece;
    @BeforeEach
    void setUp() {
        player = new Player("white");
        List<Vector> vectors = new ArrayList<>();
        vectors.add(new Vector(-1, 0));
        vectors.add(new Vector(1, 0));
        vectors.add(new Vector(0, 1));
        vectors.add(new Vector(0, -1));

        Location loc = new Location(6, 0);
        MoveVector vec = new MoveVector(vectors, vectors, vectors);
        piece = new Piece("w", "P", loc, vec, Map.of("limited",true));
        player.addPiece(piece);
    }

    @Test
    void removePiece() {
        player.removePiece(piece.getLocation());
        Assertions.assertEquals(player.getPieces().size(), 0);
    }

    @Test
    void getPieces() {
        Assertions.assertNotNull(player.getPieces());
    }

    @Test
    void addPiece() {
        player.addPiece(piece);
        Assertions.assertEquals(player.getPieces().size(), 2);
    }

    @Test
    void getTeam() {
        Assertions.assertEquals(player.getTeam(), "white");
    }
}