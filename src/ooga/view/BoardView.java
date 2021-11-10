package ooga.View;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ooga.Location;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Potential design alternative:
// instead of PieceView holding image and square, what if only held images
// BoardView holds squares and its Location, maybe PieceView moves around separate from squares
// Squares can hold boolean holdingPiece
// PieceView can extend ImageView instead and hold side, piece, image
// BoardView moves piece manually based on square location
public class BoardView extends GridPane implements BoardViewInterface {

    private final String[] CHESS_PIECES = {"pawn", "knight", "bishop", "rook", "queen", "king"};
    private final String[] CHESS_SIDES = {"white", "black"};

    private final Paint LICHESS_COLOR1 = Color.web("#f3dab0");
    private final Paint LICHESS_COLOR2 = Color.web("#bb885b");

    private boolean pieceSelected;
    private PieceView selectedPiece;
    private int row;
    private int col;
    PieceView[][] board;
    Map<PieceView, Location> pieceMap;

    public BoardView(int row, int col) {
        this.pieceSelected = false;
        this.row = row;
        this.col = col;
        board = new PieceView[row][col];
        pieceMap = new HashMap<>();
        initializeBoardView();
    }

    @Override
    public void initializeBoardView() {
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                PieceView pieceView = new PieceView();
                board[i][j] = pieceView;
                pieceView.setOnMouseClicked(e -> clickSquare(pieceView));
                this.add(pieceView, j, i);
            }
        }
        initializeChessPieces();
        changeColors(LICHESS_COLOR1, LICHESS_COLOR2);
    }

    @Override
    public void updateBoardView() {

    }

    @Override
    public void changeColors(Paint color1, Paint color2) {
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < col; j++) {
                Paint color = i%2 == j%2 ? color1 : color2;
                board[i][j].setFill(color);
            }
        }
    }

    @Override
    public void showLegalMoves() {

    }

    // Grunt of the frontend board logic
    private void clickSquare(PieceView clickedSquare) {
        if(!pieceSelected) {
            if(!clickedSquare.isHoldingPiece()) { //clicks on empty space -> do nothing
                return;
            }
            selectedPiece = clickedSquare; //selects piece to move
        } else {
            clickedSquare.movePiece(selectedPiece); // move selected piece to clicked pieceView
        }
        pieceSelected = !pieceSelected;
    }

    private void initializeChessPieces() {
        List<String> orientation = List.of("rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook");
        for(int i=0; i<2; i++) {
            String color = CHESS_SIDES[i];
            int pawnRow = i == 0 ? 6 : 1;
            int pieceRow = i == 0 ? 7 : 0;
            for(int j = 0; j < 8; j++) {
                board[pawnRow][j].setPiece(color, "pawn");
                board[pieceRow][j].setPiece(color, orientation.get(j));
            }
        }

    }
}