package ooga.view;

import javafx.stage.Stage;
import ooga.Location;
import ooga.controller.EditorController;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import util.ViewTestUtility;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static util.ViewTestUtility.*;

public class EditorViewTest extends DukeApplicationTest {

    private ViewTestUtility viewTestUtility;
    private final Map<String, Location> WHITE_PIECE_MENU_LOCATIONS = Map.of(
            KING, new Location(0, 0),
            QUEEN, new Location(0, 1),
            ROOK, new Location(0, 2),
            BISHOP, new Location(0, 3),
            KNIGHT, new Location(0, 4),
            PAWN, new Location(0, 5)
    );

    private final Map<String, Location> BLACK_PIECE_MENU_LOCATIONS = Map.of(
            KING, new Location(1, 0),
            QUEEN, new Location(1, 1),
            ROOK, new Location(1, 2),
            BISHOP, new Location(1, 3),
            KNIGHT, new Location(1, 4),
            PAWN, new Location(1, 5)
    );

    @Override
    public void start(Stage stage) {
        new EditorController();
        viewTestUtility = new ViewTestUtility();
    }

    @Test
    void testAddSinglePieceToBoard() {
        Location start = WHITE_PIECE_MENU_LOCATIONS.get(PAWN);
        Location end = new Location(5, 3);
        viewTestUtility.testMovePiece(WHITE, PAWN, start, end);
        assertTrue(viewTestUtility.pieceExists(WHITE, PAWN, end));
    }

    @Test
    void testAddMultiplePiecesToBoard() {
        Location start = WHITE_PIECE_MENU_LOCATIONS.get(PAWN);
        Location end = new Location(5, 3);
        viewTestUtility.testMovePiece(WHITE, PAWN, start, end);
        assertTrue(viewTestUtility.pieceExists(WHITE, PAWN, end));

        start = BLACK_PIECE_MENU_LOCATIONS.get(PAWN);
        end = new Location(5, 4);
        viewTestUtility.testMovePiece(BLACK, PAWN, start, end);
        assertTrue(viewTestUtility.pieceExists(BLACK, PAWN, end));

        start = WHITE_PIECE_MENU_LOCATIONS.get(ROOK);
        end = new Location(5, 5);
        viewTestUtility.testMovePiece(WHITE, ROOK, start, end);
        assertTrue(viewTestUtility.pieceExists(WHITE, ROOK, end));
    }

    @Test
    void testReplacePiece() {
        Location start1 = BLACK_PIECE_MENU_LOCATIONS.get(BISHOP);
        Location end = new Location(2, 6);
        viewTestUtility.testMovePiece(BLACK, BISHOP, start1, end);
        Location start2 = WHITE_PIECE_MENU_LOCATIONS.get(KNIGHT);
        viewTestUtility.testMovePiece(WHITE, KNIGHT, start2, end);
        assertFalse(viewTestUtility.pieceExists(BLACK, BISHOP, end));
    }

    @Test
    void testTakeSelf() {
        Location start = WHITE_PIECE_MENU_LOCATIONS.get(KING);
        Location end = new Location(4, 4);
        viewTestUtility.testMovePiece(WHITE, KING, start, end);
        clickOn(viewTestUtility.queryPieceView(WHITE, KING, start, STYLE_COMPANION));
        doubleClickOn(viewTestUtility.queryPieceView(WHITE, KING, end, STYLE_COMPANION));
        assertFalse(viewTestUtility.pieceExists(WHITE, KING, end));
    }

    @Test
    void testShowAllLocationsAsLegal() {
        Location start = BLACK_PIECE_MENU_LOCATIONS.get(BISHOP);
        Location end = new Location(3, 4);
        viewTestUtility.testMovePiece(BLACK, BISHOP, start, end);
        System.out.println(viewTestUtility.queryPieceView(BLACK, BISHOP, end, STYLE_COMPANION));
        clickOn(viewTestUtility.queryPieceView(BLACK, BISHOP, start, STYLE_COMPANION));
        clickOn(viewTestUtility.queryPieceView(BLACK, BISHOP, end, STYLE_COMPANION));
        for (int i = 0; i < 8; i++) {
            int finalI = i;
            for (int j = 0; j < 8; j++) {
                int finalJ = j;
                assertDoesNotThrow(() -> lookup(String.format("#legal_location(%d,%d)", finalI, finalJ)).query());
            }
        }
    }

    @Test
    void testFillTopRowWithQueens() {
        Location start = WHITE_PIECE_MENU_LOCATIONS.get(QUEEN);
        Location end = new Location(0, 0);
        viewTestUtility.testMovePiece(WHITE, QUEEN, start, end);
        for (int col = 0; col < 8; col++) {
            Location current = new Location(end.getRow(), col);
            clickOn(viewTestUtility.queryBoardSquare(current));
            assertDoesNotThrow(() -> viewTestUtility.queryPieceView(WHITE, QUEEN, current, STYLE_COMPANION));
        }
    }

    @Test
    void testFillEntireBoardWithKings() {
        Location start = BLACK_PIECE_MENU_LOCATIONS.get(KING);
        Location end = new Location(0, 0);
        viewTestUtility.testMovePiece(BLACK, KING, start, end);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Location current = new Location(row, col);
                clickOn(viewTestUtility.queryBoardSquare(current));
                assertDoesNotThrow(() -> viewTestUtility.queryPieceView(BLACK, KING, current, STYLE_COMPANION));
            }
        }
    }
}
