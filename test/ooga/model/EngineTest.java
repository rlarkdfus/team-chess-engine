package ooga.model;

import ooga.Location;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

class EngineTest {
    private Engine board;
    @BeforeEach
    void setUp() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        board = ModelTestHelper.createBoard();
    }

    @Test
    void movePiece() throws FileNotFoundException, InvalidPieceConfigException {
        Location start = new Location(6, 0);
        Location end = new Location(5, 0);
        List<PieceInterface> pieces = board.movePiece(start, end);
        List<Location> pieceLocations = ModelTestHelper.getPieceLocations(pieces);
        Assertions.assertNotNull(end.inList(pieceLocations));
    }

    @Test
    void gameFinished() {
    }

    @Test
    void getLegalMoves() {
        List<Location> moves = board.getLegalMoves(new Location(1, 0));
        Assertions.assertNotNull(moves);
    }

    @Test
    void getNoLegalMoves() {
        List<Location> moves = board.getLegalMoves(new Location(4, 0));
        Assertions.assertNull(moves);
    }

    @Test
    void canMovePiece() {
        Assertions.assertTrue(board.canMovePiece(new Location(6, 0)));
        Assertions.assertTrue(board.canMovePiece(new Location(6, 1)));
    }

    @Test
    void canNotMovePiece() {
        Assertions.assertFalse(board.canMovePiece(new Location(1, 0)));
        Assertions.assertFalse(board.canMovePiece(new Location(0, 0)));
    }
}