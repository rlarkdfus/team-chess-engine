//package ooga.model;
//
//import org.assertj.core.api.Assert;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class PlayerInterfaceTest {
//    private PlayerInterface player;
//    private PieceInterface piece;
//    @BeforeEach
//    void setUp() {
//        player = new Player("white");
//        piece = new Piece();
//        player.addPiece(piece);
//    }
//
//    @Test
//    void removePiece() {
//        player.removePiece(piece);
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