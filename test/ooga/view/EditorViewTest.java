package ooga.view;

import javafx.stage.Stage;
import ooga.Location;
import ooga.controller.EditorController;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import util.ViewTestUtility;
import static org.junit.jupiter.api.Assertions.*;
import static util.ViewTestUtility.*;

public class EditorViewTest extends DukeApplicationTest {

    private ViewTestUtility viewTestUtility;

    @Override
    public void start(Stage stage) {
        new EditorController();
        viewTestUtility = new ViewTestUtility();
    }

    @Test
    void testMovePawn() {
        Location whiteStart = new Location(6, 3);
        Location whiteEnd = new Location(5, 3);
        viewTestUtility.testMovePiece(WHITE, PAWN, whiteStart, whiteEnd);
        assertTrue(viewTestUtility.pieceExists(WHITE, PAWN, whiteEnd));
    }

    @Test
    void testTakeSelf() {
        Location position = new Location(6, 3);
        viewTestUtility.testMovePiece(WHITE, PAWN, position, position);
        assertFalse(viewTestUtility.pieceExists(WHITE, PAWN, position));
    }

    @Test
    void testShowAllLocationsAsLegal() {
        Location location = new Location(6, 4);
        PieceView testPiece = viewTestUtility.queryPieceView(WHITE, PAWN, location, STYLE_COMPANION);
        clickOn(testPiece);
        for (int i = 0; i < 8; i++) {
            int finalI = i;
            for (int j = 0; j < 8; j++) {
                int finalJ = j;
                assertDoesNotThrow(() -> lookup(String.format("#legal_location(%d,%d)", finalI, finalJ)).query());
            }
        }
    }
}
