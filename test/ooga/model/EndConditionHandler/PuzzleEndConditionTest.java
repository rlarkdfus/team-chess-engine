package ooga.model.EndConditionHandler;

import ooga.Location;
import ooga.controller.Config.*;
import ooga.model.*;
import ooga.model.Powerups.PowerupInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleEndConditionTest {
    EndConditionRunner endConRunner;
    List<PlayerInterface> players;
    Board board;

    @BeforeEach
    void setUp() {
        String testFile = "data/chess/simplePuzzle.json";
        Builder boardBuilder = new BoardBuilder(new File(testFile));
        players = boardBuilder.getInitialPlayers();
        endConRunner = boardBuilder.getEndConditionHandler();
        List<PowerupInterface> powerups = boardBuilder.getPowerupsHandler();
        board = new GameBoard(players, endConRunner, powerups);
    }

    @Test
    void testBlackWin() throws FileNotFoundException, InvalidPieceConfigException {
        board.movePiece(new Location(3, 6), new Location(4, 6));
        Assertions.assertEquals(GameState.BLACK, board.checkGameState());
    }

    @Test
    void testWhiteWin() throws FileNotFoundException, InvalidPieceConfigException {
        board.movePiece(new Location(3, 6), new Location(3, 7));
        Assertions.assertEquals(GameState.WHITE, board.checkGameState());
    }
}