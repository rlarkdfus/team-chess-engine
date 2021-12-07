package ooga.model;

import ooga.Location;
import ooga.controller.Config.BoardBuilder;
import ooga.model.Powerups.PowerupInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EditorBoardTest {
    private EditorEngine board;
    public static final File DEFAULT_CHESS_CONFIGURATION = new File("data/chess/defaultChessNoTimer.json");

    @BeforeEach
    void setUp() {
        BoardBuilder boardBuilder = new BoardBuilder(DEFAULT_CHESS_CONFIGURATION);
        board = new EditorBoard(boardBuilder.getInitialPlayers(), boardBuilder.getBoardSize());
    }

    @Test
    void testAddPiece() {
        board.addPiece("w","K", new Location(7, 3));
        board.addPiece("b","K", new Location(0, 3));

        Assertions.assertTrue(board.canMovePiece(new Location(7, 3)));
        Assertions.assertFalse(board.canMovePiece(new Location(5, 3)));

        System.out.println(board);

        Assertions.assertEquals(64, board.getLegalMoves(new Location(7, 0)).size());
        Assertions.assertEquals(64, board.getLegalMoves(new Location(5, 3)).size());

        Assertions.assertNotNull(board.getPieces());
    }
}