package util;

import javafx.scene.shape.Rectangle;
import ooga.Location;
import ooga.view.PieceView;
import org.testfx.service.query.EmptyNodeQueryException;

public class ViewTestUtility extends DukeApplicationTest {

    public static final String BLACK = "b";
    public static final String WHITE = "w";
    public static final String ROOK = "R";
    public static final String PAWN = "P";
    public static final String KNIGHT = "N";
    public static final String KING = "K";
    public static final String QUEEN = "Q";
    public static final String BISHOP = "B";

    public static final String STYLE_COMPANION = "companion";

    /**
     * Utility function for testing moving a piece to a spot
     * @param side the side the piece is on ("b" or "w")
     * @param start coordinates of start location "(x,y)"
     * @param end coordinates of end location "(x,y)"
     * @return if the movement was successful
     */
    public boolean testMovePiece(String side, String piece, Location start, Location end) {
        PieceView testPiece = queryPieceView(side, piece, start, STYLE_COMPANION);
        Rectangle destinationSpot = queryBoardSquare(end);
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
    public boolean pieceExists(String side, String piece, Location location) {
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
    public PieceView queryPieceView(String side, String piece, Location location, String style) throws EmptyNodeQueryException {
        return lookup(String.format("#pieceView_side(%s)_piece(%s)_location(%d,%d)_style(%s)", side, piece, location.getRow(), location.getCol(), style)).query();
    }

    /**
     * Utility function for querying a BoardSquare (a Rectangle node) in the scene
     * @param location coordinates of the location ("x,y")
     * @return the Rectangle with the specified parameters if it exists in the scene
     * @throws EmptyNodeQueryException if the BoardSquare does not exist in the scene
     */
    public Rectangle queryBoardSquare(Location location) throws EmptyNodeQueryException {
        return lookup(String.format("#square_location(%d,%d)", location.getRow(), location.getCol())).query();
    }
}
