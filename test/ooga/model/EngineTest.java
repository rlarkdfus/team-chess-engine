package ooga.model;

import ooga.Location;
import ooga.Turn;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class EngineTest {
    private Engine board;
    @BeforeEach
    void setUp() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        board = new Board(new ArrayList<>());
    }

//    @Test
//    void initializeBoard() {
//        board.initializeBoard();
//    }

    @Test
    void movePiece() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Location start = new Location(6, 0);
        Location end = new Location(5, 0);
        Turn turn = board.movePiece(start, end);
        Assertions.assertNotNull(turn);
    }

    @Test
    void gameFinished() {
    }

    @Test
    void getLegalMoves() {
        List<Location> moves = board.getLegalMoves(new Location(6, 0));
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