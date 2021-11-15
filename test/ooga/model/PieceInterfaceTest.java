package ooga.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ooga.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PieceInterfaceTest {
    private PieceInterface piece;

    @BeforeEach
    void setUp() {
//        List<List<Integer>> vectors = new ArrayList<>();
//        vectors.add(Arrays.asList(1, 0));
//        piece = new Piece("white", vectors, false, 1);

        List<Vector> vectors = new ArrayList<>();
        vectors.add(new Vector(-1, 0));
        vectors.add(new Vector(1, 0));
        vectors.add(new Vector(0, 1));
        vectors.add(new Vector(0, -1));

        Location loc = new Location(6, 0);
        MoveVector vec = new MoveVector(vectors, vectors, vectors);
        piece = new Piece("w", "P", loc, vec, Map.of("limited",true));
    }

    @Test
    void getTeam() {
        Assertions.assertEquals(piece.getTeam(), "w");
    }

    @Test
    void getMoves() {
        Assertions.assertNotNull(piece.getMoves());
    }

    @Test
    void isLimited() {
        Assertions.assertFalse(piece.isLimited());
    }

    @Test
    void getScore() {
        Assertions.assertEquals(piece.getScore(), 1);
    }
}