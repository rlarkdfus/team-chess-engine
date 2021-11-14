//package ooga.model;
//
//import org.assertj.core.api.Assert;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class PlayerInterfaceTest {
//    private PlayerInterface player;
//    private PieceInterface piece;
//    @BeforeEach
//    void setUp() {
//        player = new Player("white");
//        List<Piece.Vector> vectors = new ArrayList<>();
//        vectors.add(new Piece.Vector(-1, 0));
//        vectors.add(new Piece.Vector(1, 0));
//        vectors.add(new Piece.Vector(0, 1));
//        vectors.add(new Piece.Vector(0, -1));
//
//
//        piece = new Piece("white", vectors, false);
//        player.addPiece(piece);
//    }
//
//    @Test
//    void removePiece() {
//        player.removePiece((Piece) piece);
//        Assertions.assertNull(player.getPieces());
//    }
//
//    @Test
//    void getPieces() {
//        Assertions.assertNotNull(player.getPieces());
//    }
//
//    @Test
//    void addPiece() {
//        player.addPiece(piece);
//        Assertions.assertEquals(player.getPieces().size(), 2);
//    }
//
//    @Test
//    void getTeam() {
//        Assertions.assertEquals(player.getTeam(), "white");
//    }
//}