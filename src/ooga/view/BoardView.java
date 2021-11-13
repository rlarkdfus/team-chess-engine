package ooga.view;

import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import ooga.Location;

import java.util.List;

public class BoardView extends Group implements BoardViewInterface {

    private final String[] CHESS_PIECES = {"pawn", "knight", "bishop", "rook", "queen", "king"};
    private final String[] CHESS_SIDES = {"white", "black"};

    private final Color LICHESS_COLOR1 = Color.web("#f3dab0");
    private final Color LICHESS_COLOR2 = Color.web("#bb885b");

    private Location startLocation;
    private BoardSquare[][] background;
    private PieceView[][] pieceGrid;

    public BoardView(int row, int col) {
        startLocation = null;
        pieceGrid = new PieceView[row][col];
        initializeBoardView(row, col);
    }

    @Override
    public void initializeBoardView(int row, int col) {
        renderBackground(row, col);
        renderChessPieces();
        this.setOnMouseClicked(e -> clickBoard(e));
    }

    //TODO: maybe refactor this cuz getters are bad
    public BoardSquare[][] getBackground() {
        return background;
    }

    private void clickBoard(MouseEvent mouse) {
        Location clickLocation = new Location((int)mouse.getY()/60, (int)mouse.getX()/60);
        //user doesn't have piece selected and clicks on new piece
        if(startLocation == null) {
            if(pieceGrid[clickLocation.getX()][clickLocation.getY()] != null) {
                selectPiece(clickLocation);
            }
        } else { //user selects new location with piece
            if (!clickLocation.equals(startLocation) && isLegalMove(clickLocation)) { //user clicks new location
                movePiece(startLocation, clickLocation);
            }
            unselectPiece(); // if user clicks the same piece then selection is reset
        }

    }

    private void selectPiece(Location location) {
        startLocation = location;

        showLegalMoves(location);
    }

    private void unselectPiece() {
        for(int i = 0; i < background.length; i++) {
            for(int j = 0; j < background[0].length; j++) {
                background[i][j].resetBoardSquare();
            }
        }
        startLocation = null;
    }

    private void movePiece(Location start, Location end) {
        removePiece(end);

        PieceView movedPiece = pieceGrid[start.getX()][start.getY()];
        pieceGrid[end.getX()][end.getY()] = movedPiece;
        pieceGrid[start.getX()][start.getY()] = null;
        movedPiece.moveTo(end);
    }

    private void removePiece(Location location) {
        this.getChildren().remove(pieceGrid[location.getX()][location.getY()]);
        pieceGrid[location.getX()][location.getY()] = null;
    }

    private void renderBackground(int row, int col) {
        background = new BoardSquare[row][col];
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                Location location = new Location(i, j);
                BoardSquare boardSquare = new BoardSquare(location, null);
                background[i][j] = boardSquare;
                this.getChildren().add(boardSquare);
            }
        }
        changeColors(LICHESS_COLOR1, LICHESS_COLOR2);
    }

    private void renderChessPieces() {
        String[] orientation = new String[]{"rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook"};
        for(int i=0; i<2; i++) {
            String side = CHESS_SIDES[i];
            int pawnRow = i == 0 ? 6 : 1;
            int pieceRow = i == 0 ? 7 : 0;
            for(int j = 0; j < 8; j++) {
                Location pawnLoc = new Location(pawnRow, j);
                PieceView pawn = new PieceView(side, "pawn", pawnLoc);

                Location pieceLoc = new Location(pieceRow, j);
                PieceView piece = new PieceView(side, orientation[j], pieceLoc);

                pieceGrid[pawnRow][j] = pawn;
                pieceGrid[pieceRow][j] = piece;

                this.getChildren().addAll(pawn, piece);
            }
        }
    }

    @Override
    public void changeColors(Color color1, Color color2) {
        for(int i = 0; i < background.length; i++) {
            for(int j = 0; j < background[0].length; j++) {
                Color color = (i+j)%2 == 0 ? color1 : color2;
                background[i][j].setColor(color);
            }
        }
    }

    private boolean isLegalMove(Location location) {
        return true;
    }

    @Override
    public void updateBoardView() {

    }

    @Override
    public void showLegalMoves(Location location) {
        List<Location> legalMoves = List.of(
                new Location(location.getX() - 1, location.getY()),
                new Location(location.getX() - 2, location.getY()));
        background[location.getX()][location.getY()].highlightSelected();
        for(Location squareLoc : legalMoves) {
            background[squareLoc.getX()][squareLoc.getY()].highlightLegalMove();
        }
    }
}