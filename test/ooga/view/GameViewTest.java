package ooga.view;

import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ooga.Location;
import ooga.controller.GameController;
import org.junit.jupiter.api.Test;
import org.testfx.service.query.EmptyNodeQueryException;
import util.DukeApplicationTest;
import util.ViewTestUtility;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.ViewTestUtility.*;

public class GameViewTest extends DukeApplicationTest {
    
    private ViewTestUtility viewTestUtility;

    @Override
    public void start(Stage stage) {
        new GameController();
        viewTestUtility = new ViewTestUtility();
    }

    @Test
    void testSetTimerInitialTime() {
        Slider minutesSlider = lookup("#minutes_slider").query();
        Button newGame = lookup("#new_game").queryButton();
        String expected = "09:00";
        setValue(minutesSlider, Integer.parseInt(expected.split(":")[0]));
        clickOn(newGame);
        Text blackTimeLabel = lookup("#black_timer_display").query();
        assertEquals(expected, blackTimeLabel.getText());
    }

    @Test
    void testSetIncrementLabelUpdate() {
        Slider incrementSlider = lookup("#increment_slider").query();
        String expected = "2";
        setValue(incrementSlider, Integer.parseInt(expected));
        Label incrementSliderLabel = lookup("#increment_slider_value").query();
        assertEquals(expected, incrementSliderLabel.getText());
    }

    @Test
    void testSetTimeLabelUpdate() {
        Slider minutesSlider = lookup("#minutes_slider").query();
        String expected = "18";
        setValue(minutesSlider, Integer.parseInt(expected));
        Label incrementSliderLabel = lookup("#minutes_slider_value").query();
        assertEquals(expected, incrementSliderLabel.getText());
    }

    @Test
    void testChangeColor1() {
        ColorPicker colorPicker1 = lookup("#board_color_1").query();
        Location location = new Location(0, 0);
        Rectangle testSquare = viewTestUtility.queryBoardSquare(location);
        Color expected = Color.RED;
        setValue(colorPicker1, expected);
        assertEquals(expected, testSquare.getFill());
    }

    @Test
    void testChangeColor2() {
        ColorPicker colorPicker2 = lookup("#board_color_2").query();
        Location location = new Location(0, 1);
        Rectangle testSquare = viewTestUtility.queryBoardSquare(location);
        Color expected = Color.GREEN;
        setValue(colorPicker2, expected);
        assertEquals(expected, testSquare.getFill());
    }

    @Test
    void testChangeStyle() {
        ComboBox styleChanger = lookup("#piece_style").query();
        String style = "horsey";
        Location location = new Location(0, 0);
        clickOn(styleChanger);
        clickOn(style);
        assertDoesNotThrow(() -> viewTestUtility.queryPieceView(BLACK, ROOK, location, style));
    }

    @Test
    void testSingleClickPieceHighlight() {
        Location location = new Location(7, 3);
        PieceView testPiece = viewTestUtility.queryPieceView(WHITE, QUEEN, location, STYLE_COMPANION);
        clickOn(testPiece);
        assertDoesNotThrow(() -> lookup(String.format("#select_location(%d,%d)", location.getRow(), location.getCol())).query());
    }

    @Test
    void testShowPawnLegalMoves() {
        Location location = new Location(6, 4);
        PieceView testPiece = viewTestUtility.queryPieceView(WHITE, PAWN, location, STYLE_COMPANION);
        clickOn(testPiece);
        for (int i = 1; i <= 2; i++) {
            int finalI = i;
            assertDoesNotThrow(() -> lookup(String.format("#legal_location(%d,%d)", location.getRow() - finalI, location.getCol())).query());
        }
    }

    @Test
    void testSingleRightClickEmptySquareHighlight() {
        Location location = new Location(3, 3);
        rightClickOn(viewTestUtility.queryBoardSquare(location));
        assertDoesNotThrow(() -> lookup(String.format("#annotate_location(%d,%d)", location.getRow(), location.getCol())).query());
    }

    @Test
    void testDoubleRightClickEmptySquareDeselect() {
        Location location = new Location(4, 4);
        Rectangle spot = viewTestUtility.queryBoardSquare(location);
        rightClickOn(spot).rightClickOn(spot);
        assertThrows(EmptyNodeQueryException.class, () -> lookup(String.format("#annotate_location(%d,%d)", location.getRow(), location.getCol())).query());
    }

    @Test
    void testNewGame() {
        Location whiteStart = new Location(6, 0);
        Location whiteEnd = new Location(5, 0);
        viewTestUtility.testMovePiece(WHITE, PAWN, whiteStart, whiteEnd);
        Button reset = lookup("#new_game").queryButton();
        clickOn(reset);
        assertDoesNotThrow(() -> viewTestUtility.queryPieceView(WHITE, PAWN, whiteStart, STYLE_COMPANION));
    }

    @Test
    void testMoveWhitePawnLegal() {
        Location whiteStart = new Location(6, 3);
        Location whiteEnd = new Location(5, 3);
        assertTrue(viewTestUtility.testMovePiece(WHITE, PAWN, whiteStart, whiteEnd));
    }

    @Test
    void testMoveWhitePawnIllegal() {
        Location whiteStart = new Location(6, 3);
        Location whiteEnd = new Location(5, 2);
        assertFalse(viewTestUtility.testMovePiece(WHITE, PAWN, whiteStart, whiteEnd));
    }

    @Test
    void testMoveBlackPawnLegal() {
        Location whiteStart = new Location(6, 3);
        Location whiteEnd = new Location(5, 3);
        Location blackStart = new Location(1, 2);
        Location blackEnd = new Location(2, 2);
        viewTestUtility.testMovePiece(WHITE, PAWN, whiteStart, whiteEnd);
        assertTrue(viewTestUtility.testMovePiece(BLACK, PAWN, blackStart, blackEnd));
    }

    @Test
    void testMoveBlackPawnIllegal() {
        Location whiteStart = new Location(6,3);
        Location whiteEnd = new Location(4, 3);
        Location blackStart = new Location(1,2);
        Location blackEnd = new Location(2, 3);
        viewTestUtility.testMovePiece(WHITE, PAWN, whiteStart, whiteEnd);
        assertFalse(viewTestUtility.testMovePiece(BLACK, PAWN, blackStart, blackEnd));
    }

    @Test
    void testMoveWhiteKnightLegal() {
        Location whiteStart = new Location(7,1);
        Location whiteEnd = new Location(5,2);
        assertTrue(viewTestUtility.testMovePiece(WHITE, KNIGHT, whiteStart, whiteEnd));
    }

    @Test
    void testMoveWhiteKnightIllegal() {
        Location whiteStart = new Location(7,1);
        Location whiteEnd = new Location(5,1);
        assertFalse(viewTestUtility.testMovePiece(WHITE, KNIGHT, whiteStart, whiteEnd));
    }

    @Test
    void testMoveBlackKnightLegal() {
        Location whiteStart = new Location(7, 1);
        Location whiteEnd = new Location(5, 2);
        Location blackStart = new Location(0, 1);
        Location blackEnd = new Location(2, 2);
        viewTestUtility.testMovePiece(WHITE, KNIGHT, whiteStart, whiteEnd);
        assertTrue(viewTestUtility.testMovePiece(BLACK, KNIGHT, blackStart, blackEnd));
    }

    @Test
    void testMoveBlackKnightIllegal() {
        Location whiteStart = new Location(7, 1);
        Location whiteEnd = new Location(5, 2);
        Location blackStart = new Location(0, 1);
        Location blackEnd = new Location(2, 3);
        viewTestUtility.testMovePiece(WHITE, KNIGHT, whiteStart, whiteEnd);
        assertFalse(viewTestUtility.testMovePiece(BLACK, KNIGHT, blackStart, blackEnd));
    }

    @Test
    void testMoveOutOfTurnWhite() {
        Location whiteStart1 = new Location(6, 3);
        Location whiteEnd1 = new Location(4, 3);
        Location whiteStart2 = new Location(6, 5);
        Location whiteEnd2 = new Location(4, 5);
        viewTestUtility.testMovePiece(WHITE, PAWN, whiteStart1, whiteEnd1);
        assertFalse(viewTestUtility.testMovePiece(WHITE, PAWN, whiteStart2, whiteEnd2));
    }

    @Test
    void testMoveOutOfTurnBlack() {
        Location blackStart = new Location(1, 3);
        Location blackEnd = new Location(4, 3);
        assertFalse(viewTestUtility.testMovePiece(BLACK, PAWN, blackStart, blackEnd));
    }

    @Test
    void testKill() {
        Location whiteStart1 = new Location(7, 1);
        Location whiteEnd1 = new Location(5, 2);
        Location blackStart1 = new Location(1, 1);
        Location blackEnd1 = new Location(3, 1);
        Location whiteStart2 = new Location(5, 2);
        Location whiteEnd2 = new Location(3, 1);

        viewTestUtility.testMovePiece(WHITE, KNIGHT, whiteStart1, whiteEnd1);
        viewTestUtility.testMovePiece(BLACK, PAWN, blackStart1, blackEnd1);
        viewTestUtility.testMovePiece(WHITE, KNIGHT, whiteStart2, whiteEnd2);

        assertTrue(viewTestUtility.pieceExists(WHITE, KNIGHT, whiteEnd2));
        assertFalse(viewTestUtility.pieceExists(BLACK, PAWN, whiteEnd2));
    }
}
