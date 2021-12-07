package ooga.model.EndConditionHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import ooga.Location;
import ooga.controller.Config.BoardBuilder;
import ooga.controller.Config.Builder;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.model.*;
import ooga.model.Powerups.PowerupInterface;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PuzzleEndConditionTest {
    EndConditionRunner endConRunner;
    List<PlayerInterface> players;
    GameEngine board;

    @BeforeEach
    void setUp() {
        String testFile = "data/chess/simplePuzzle.json";
        Builder boardBuilder = new BoardBuilder(new File(testFile));
        players = boardBuilder.getInitialPlayers();
        endConRunner = boardBuilder.getEndConditionHandler();
        List<PowerupInterface> powerups = boardBuilder.getPowerupsHandler();
        board = new GameBoard(players, endConRunner, powerups, boardBuilder.getBoardSize());
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