package ooga.view;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ooga.controller.Controller;
import org.junit.jupiter.api.Test;
import org.testfx.service.query.EmptyNodeQueryException;
import util.DukeApplicationTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewTest extends DukeApplicationTest {

    // this method is run BEFORE EACH test to set up application in a fresh state
    @Override
    public void start(Stage stage) {
        new Controller();
    }

    @Test
    void testChangeColor1() {
        ColorPicker colorPicker1 = lookup("#board_color_1").query();
        Rectangle testSquare = lookup("#square_location(0,0)").query();
        Color expected = Color.RED;
        setValue(colorPicker1, expected);
        assertEquals(expected, testSquare.getFill());
    }

    @Test
    void testChangeColor2() {
        ColorPicker colorPicker2 = lookup("#board_color_2").query();
        Rectangle testSquare = lookup("#square_location(0,1)").query();
        Color expected = Color.GREEN;
        setValue(colorPicker2, expected);
        assertEquals(expected, testSquare.getFill());
    }

    @Test
    void testChangeStyle() {
        ComboBox styleChanger = lookup("#piece_style").query();
        String expected = "horsey";
        clickOn(styleChanger);
        clickOn(expected);
        assertDoesNotThrow(() -> lookup("#pieceView_location(0,0)_style(horsey)").query());
    }

    @Test
    void testSingleClickPieceHighlight() {
        String location = "(7,3)";
        PieceView testPiece = lookup(String.format("#pieceView_location%s_style(companion)", location)).query();
        clickOn(testPiece);
        assertDoesNotThrow(() -> lookup(String.format("#select_location%s", location)).query());
    }

    @Test
    void testDoubleClickPieceDeselect() {
        String location = "(7,4)";
        PieceView testPiece = lookup(String.format("#pieceView_location%s_style(companion)", location)).query();
        doubleClickOn(testPiece);
        assertThrows(EmptyNodeQueryException.class, () -> lookup(String.format("#select_location%s", location)).query());
    }

    @Test
    void testSingleRightClickEmptySquareHighlight() {
        String location = "(3,3)";
        Rectangle spot = lookup(String.format("#square_location%s", location)).query();
        rightClickOn(spot);
        assertDoesNotThrow(() -> lookup(String.format("#annotate_location%s", location)).query());
    }

    @Test
    void testDoubleRightClickEmptySquareDeselect() {
        String location = "(4,4)";
        Rectangle spot = lookup(String.format("#square_location%s", location)).query();
        rightClickOn(spot).rightClickOn(spot);
        assertThrows(EmptyNodeQueryException.class, () -> lookup(String.format("#annotate_location%s", location)).query());
    }

    @Test
    void testNewGame() {
        String whiteStart = "(6,0)";
        String whiteEnd = "(4,0)";
        testMovePiece(whiteStart, whiteEnd);
        Button reset = lookup("#new_game").queryButton();
        clickOn(reset);
        assertDoesNotThrow(() -> lookup(String.format("#pieceView_location%s_style(companion)", whiteStart)).query());
    }

    @Test
    void testMoveWhitePawnLegal() {

        String whiteStart = "(6,3)";
        String whiteEnd = "(4,3)";
        assertTrue(testMovePiece(whiteStart, whiteEnd));
    }

    @Test
    void testMoveWhitePawnIllegal() {
        String whiteStart = "(6,3)";
        String whiteEnd = "(5,2)";
        assertFalse(testMovePiece(whiteStart, whiteEnd));
    }

    @Test
    void testMoveBlackPawnLegal() {
        String whiteStart = "(6,3)";
        String whiteEnd = "(4,3)";
        String blackStart = "(1,2)";
        String blackEnd = "(4,2)";
        testMovePiece(whiteStart, whiteEnd);
        assertTrue(testMovePiece(blackStart, blackEnd));
    }

    @Test
    void testMoveBlackPawnIllegal() {
        String whiteStart = "(6,3)";
        String whiteEnd = "(4,3)";
        String blackStart = "(1,2)";
        String blackEnd = "(2,3)";
        testMovePiece(whiteStart, whiteEnd);
        assertFalse(testMovePiece(blackStart, blackEnd));
    }

    @Test
    void testMoveOutOfTurnWhite() {
        String whiteStart1 = "(6,3)";
        String whiteEnd1 = "(4,3)";
        String whiteStart2 = "(6,5)";
        String whiteEnd2 = "(4,5)";
        testMovePiece(whiteStart1, whiteEnd1);
        assertFalse(testMovePiece(whiteStart2, whiteEnd2));
    }

    @Test
    void testMoveOutOfTurnBlack() {
        String blackStart = "(1,3)";
        String blackEnd = "(4,3)";
        assertFalse(testMovePiece(blackStart, blackEnd));
    }

    /**
     * Utility function for testing moving a piece to a spot
     * @param start
     * @param end
     * @return if the movement was successful
     */
    private boolean testMovePiece(String start, String end) {
        PieceView testPiece = lookup(String.format("#pieceView_location%s_style(companion)", start)).query();
        Rectangle destinationSpot = lookup(String.format("#square_location%s", end)).query();
        clickOn(testPiece);
        moveTo(destinationSpot);
        clickOn(destinationSpot);
        try {
            lookup(String.format("#pieceView_location%s_style(companion)", end)).query();
            return true;
        }
        catch (EmptyNodeQueryException e) {
            return false;
        }
    }
}
