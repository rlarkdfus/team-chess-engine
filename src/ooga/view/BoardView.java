package ooga.view;

import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import ooga.Location;
import ooga.controller.Controller;
import ooga.Turn;
import ooga.controller.ControllerInterface;

import java.util.List;

public class BoardView extends Group implements BoardViewInterface {

    private final String[] CHESS_PIECES = {"pawn", "knight", "bishop", "rook", "queen", "king"};
    private final String[] CHESS_SIDES = {"w", "b"};

    private final Color LICHESS_COLOR1 = Color.web("#f3dab0");
    private final Color LICHESS_COLOR2 = Color.web("#bb885b");

    private ControllerInterface controller;
    private Location startLocation;
    private BoardSquare[][] background;
    private PieceView[][] pieceGrid;

    public BoardView(Controller controller, int row, int col) {
        this.controller = controller;
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

        if(mouse.getButton() == MouseButton.SECONDARY) {
            background[clickLocation.getRow()][clickLocation.getCol()].annotate();
            return;
        }

        //user doesn't have piece selected and clicks on new piece
        //And add logic and is the same team

        if(startLocation == null) {
            if(controller.canMovePiece(clickLocation)) {
                selectPiece(clickLocation);
            } else {
                unselectPiece();
            }
        } else { //user selects new location with piece
            if (isLegalMove(clickLocation)) { //user clicks new location
                controller.movePiece(startLocation, clickLocation);
            }
            unselectPiece(); // if user clicks an illegal move
        }
    }

    private void selectPiece(Location location) {
        startLocation = location;
        background[location.getRow()][location.getCol()].select();
        showLegalMoves(location);
    }

    private void unselectPiece() {
        startLocation = null;
        for(int i = 0; i < background.length; i++) {
            for(int j = 0; j < background[0].length; j++) {
                background[i][j].resetBoardSquare();
            }
        }
    }

    private void movePiece(Location start, Location end) {
        PieceView movedPiece = pieceGrid[start.getRow()][start.getCol()];
        pieceGrid[end.getRow()][end.getCol()] = movedPiece;
        pieceGrid[start.getRow()][start.getCol()] = null;
        movedPiece.moveTo(end);
    }

    private void removePiece(Location location) {
        this.getChildren().remove(pieceGrid[location.getRow()][location.getCol()]);
        pieceGrid[location.getRow()][location.getCol()] = null;
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
        String[] orientation = new String[]{"R", "N", "B", "Q", "K", "B", "N", "R"};
        for(int i=0; i<2; i++) {
            String side = CHESS_SIDES[i];
            int pawnRow = i == 0 ? 6 : 1;
            int pieceRow = i == 0 ? 7 : 0;
            for(int j = 0; j < 8; j++) {
                Location pawnLoc = new Location(pawnRow, j);
                PieceView pawn = new PieceView(side, "P", "companion", pawnLoc);

                Location pieceLoc = new Location(pieceRow, j);
                PieceView piece = new PieceView(side, orientation[j], "companion", pieceLoc);

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
                Color color = (i + j) % 2 == 0 ? color1 : color2;
                background[i][j].setColor(color);
            }
        }
    }

    private boolean isLegalMove(Location clickLocation) {
        for(Location legalSquare : controller.getLegalMoves(startLocation)) {
            if(clickLocation.equals(legalSquare)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateBoardView(Turn turn) {
        for(Location removed : turn.getRemoved()){
            removePiece(removed);
        }

        for(Turn.PieceMove move : turn.getMoves()) {
            movePiece(move.getStartLocation(), move.getEndLocation());
        }
    }

    @Override
    public void showLegalMoves(Location location) {
        List<Location> legalMoves = controller.getLegalMoves(location);
        for(Location squareLoc : legalMoves) {
            BoardSquare square = background[squareLoc.getRow()][squareLoc.getCol()];
            square.highlight();
        }
    }
}