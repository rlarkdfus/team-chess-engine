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

    public static final String BLACK = "b";
    public static final String WHITE = "w";
    public static final String ROOK = "R";
    public static final String PAWN = "P";
    public static final String KNIGHT = "N";
    public static final String KING = "K";
    public static final String QUEEN = "Q";
    public static final String BISHOP = "B";
    public static final String STYLE_COMPANION = "companion";

    // this method is run BEFORE EACH test to set up application in a fresh state
    @Override
    public void start(Stage stage) {
        new Controller();
        Button uploadConfig = lookup("#upload_configuration").queryButton();
        clickOn(uploadConfig);
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
        String style = "horsey";
        String location = "(0,0)";
        clickOn(styleChanger);
        clickOn(style);
        assertDoesNotThrow(() -> queryPieceView(BLACK, ROOK, location, style));
    }

    @Test
    void testSingleClickPieceHighlight() {
        String location = "(7,3)";
        PieceView testPiece = queryPieceView(WHITE, QUEEN, location, STYLE_COMPANION);
        clickOn(testPiece);
        assertDoesNotThrow(() -> lookup(String.format("#select_location%s", location)).query());
    }

    @Test
    void testDoubleClickPieceDeselect() {
        String location = "(7,4)";
        PieceView testPiece = queryPieceView(WHITE, KING, location, STYLE_COMPANION);
        doubleClickOn(testPiece);
        assertThrows(EmptyNodeQueryException.class, () -> lookup(String.format("#select_location%s", location)).query());
    }

    @Test
    void testSingleRightClickEmptySquareHighlight() {
        String location = "(3,3)";
        rightClickOn(queryBoardSquare(location));
        assertDoesNotThrow(() -> lookup(String.format("#annotate_location%s", location)).query());
    }

    @Test
    void testDoubleRightClickEmptySquareDeselect() {
        String location = "(4,4)";
        Rectangle spot = queryBoardSquare(location);
        rightClickOn(spot).rightClickOn(spot);
        assertThrows(EmptyNodeQueryException.class, () -> lookup(String.format("#annotate_location%s", location)).query());
    }

    @Test
    void testNewGame() {
        String whiteStart = "(6,0)";
        String whiteEnd = "(5,0)";
        testMovePiece(WHITE, PAWN, whiteStart, whiteEnd);
        Button reset = lookup("#new_game").queryButton();
        clickOn(reset);
        assertThrows(EmptyNodeQueryException.class, () -> lookup(String.format("#pieceView_location%s_style(companion)", whiteStart)).query());
    }

    @Test
    void testMoveWhitePawnLegal() {
        String whiteStart = "(6,3)";
        String whiteEnd = "(5,3)";
        assertTrue(testMovePiece(WHITE, PAWN, whiteStart, whiteEnd));
    }

    @Test
    void testMoveWhitePawnIllegal() {
        String whiteStart = "(6,3)";
        String whiteEnd = "(5,2)";
        assertFalse(testMovePiece(WHITE, PAWN, whiteStart, whiteEnd));
    }

    @Test
    void testMoveBlackPawnLegal() {
        String whiteStart = "(6,3)";
        String whiteEnd = "(5,3)";
        String blackStart = "(1,2)";
        String blackEnd = "(2,2)";
        testMovePiece(WHITE, PAWN, whiteStart, whiteEnd);
        assertTrue(testMovePiece(BLACK, PAWN, blackStart, blackEnd));
    }

    @Test
    void testMoveBlackPawnIllegal() {
        String whiteStart = "(6,3)";
        String whiteEnd = "(4,3)";
        String blackStart = "(1,2)";
        String blackEnd = "(2,3)";
        testMovePiece(WHITE, PAWN, whiteStart, whiteEnd);
        assertFalse(testMovePiece(BLACK, PAWN, blackStart, blackEnd));
    }

    @Test
    void testMoveWhiteKnightLegal() {
        String whiteStart = "(7,1)";
        String whiteEnd = "(5,2)";
        assertTrue(testMovePiece(WHITE, KNIGHT, whiteStart, whiteEnd));
    }

    @Test
    void testMoveWhiteKnightIllegal() {
        String whiteStart = "(7,1)";
        String whiteEnd = "(5,1)";
        assertFalse(testMovePiece(WHITE, KNIGHT, whiteStart, whiteEnd));
    }

    @Test
    void testMoveBlackKnightLegal() {
        String whiteStart = "(7,1)";
        String whiteEnd = "(5,2)";
        String blackStart = "(0,1)";
        String blackEnd = "(2,2)";
        testMovePiece(WHITE, KNIGHT, whiteStart, whiteEnd);
        assertTrue(testMovePiece(BLACK, KNIGHT, blackStart, blackEnd));
    }

    @Test
    void testMoveBlackKnightIllegal() {
        String whiteStart = "(7,1)";
        String whiteEnd = "(5,2)";
        String blackStart = "(0,1)";
        String blackEnd = "(2,3)";
        testMovePiece(WHITE, KNIGHT, whiteStart, whiteEnd);
        assertFalse(testMovePiece(BLACK, KNIGHT, blackStart, blackEnd));
    }

    @Test
    void testMoveOutOfTurnWhite() {
        String whiteStart1 = "(6,3)";
        String whiteEnd1 = "(4,3)";
        String whiteStart2 = "(6,5)";
        String whiteEnd2 = "(4,5)";
        testMovePiece(WHITE, PAWN, whiteStart1, whiteEnd1);
        assertFalse(testMovePiece(WHITE, PAWN, whiteStart2, whiteEnd2));
    }

    @Test
    void testMoveOutOfTurnBlack() {
        String blackStart = "(1,3)";
        String blackEnd = "(4,3)";
        assertFalse(testMovePiece(BLACK, PAWN, blackStart, blackEnd));
    }

    @Test
    void testKill() {
        String whiteStart1 = "(7,1)";
        String whiteEnd1 = "(5,2)";
        String blackStart1 = "(1,1)";
        String blackEnd1 = "(3,1)";
        String whiteStart2 = "(5,2)";
        String whiteEnd2 = "(3,1)";

        testMovePiece(WHITE, KNIGHT, whiteStart1, whiteEnd1);
        testMovePiece(BLACK, PAWN, blackStart1, blackEnd1);
        testMovePiece(WHITE, KNIGHT, whiteStart2, whiteEnd2);

        assertTrue(pieceExists(WHITE, KNIGHT, whiteEnd2));
        assertFalse(pieceExists(BLACK, PAWN, whiteEnd2));
    }

    /**
     * Utility function for testing moving a piece to a spot
     * @param side the side the piece is on ("b" or "w")
     * @param start coordinates of start location "(x,y)"
     * @param end coordinates of end location "(x,y)"
     * @return if the movement was successful
     */
    private boolean testMovePiece(String side, String piece, String start, String end) {
        PieceView testPiece = queryPieceView(side, piece, start, STYLE_COMPANION);
        //PieceView testPiece = lookup(String.format("#pieceView_side(%s)_piece(%s)_location%s_style(companion)", side, piece, start)).query();
        Rectangle destinationSpot = lookup(String.format("#square_location%s", end)).query();
        clickOn(testPiece);
        moveTo(destinationSpot);
        clickOn(destinationSpot);
        return pieceExists(side, piece, end);
    }

    /**
     * Utility function for testing if a piece exists at a spot
     * @param side the side the piece is on ("b" or "w")
     * @param piece the type of the piece
     * @param location coordinates of the location ("x,y")
     * @return if the piece of the given side and type exists at the location in the board
     */
    private boolean pieceExists(String side, String piece, String location) {
        try {
            queryPieceView(side, piece, location, STYLE_COMPANION);
            return true;
        }
        catch (EmptyNodeQueryException e) {
            return false;
        }
    }

    /**
     * Utility function for querying a PieceView in the scene
     * @param side the side the piece is on ("b" or "w")
     * @param piece the type of the piece
     * @param location coordinates of the location ("x,y")
     * @param style the style of the piece
     * @return the PieceView with the specified parameters if it exists in the scene
     * @throws EmptyNodeQueryException if the PieceView does not exist in the scene
     */
    private PieceView queryPieceView(String side, String piece, String location, String style) throws EmptyNodeQueryException {
        return lookup(String.format("#pieceView_side(%s)_piece(%s)_location%s_style(%s)", side, piece, location, style)).query();
    }

    /**
     * Utility function for querying a BoardSquare (a Rectangle node) in the scene
     * @param location coordinates of the location ("x,y")
     * @return the Rectangle with the specified parameters if it exists in the scene
     * @throws EmptyNodeQueryException if the BoardSquare does not exist in the scene
     */
    private Rectangle queryBoardSquare(String location) throws EmptyNodeQueryException {
        return lookup(String.format("#square_location%s", location)).query();
    }
}
