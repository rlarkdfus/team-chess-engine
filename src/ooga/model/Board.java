package ooga.model;

import ooga.Location;
import ooga.view.PieceView;

import java.util.ArrayList;
import java.util.List;

public class Board implements Engine {

    private final String[] CHESS_SIDES = {"white", "black"};

    private PieceInterface[][] pieceGrid;

    public Board(){
        initializeBoard();
    }

    /**
     * Create default board of pieces
     */
    public void initializeBoard(){
        for(int i=0; i<2; i++) {
            int pawnRow = i == 0 ? 6 : 1;
            int pieceRow = i == 0 ? 7 : 0;
            for(int j = 0; j < 8; j++) {
                List<List<Integer>> vectors = new ArrayList<>();
                vectors.add(List.of(1, 0));

                PieceInterface pawn = new Piece(i,vectors, false);
                PieceInterface piece = new Piece(i,vectors, false);

                pieceGrid[pawnRow][j] = pawn;
                pieceGrid[pieceRow][j] = piece;
            }
        }
    }

    /**
     * Moves piece from start to end and updates the board
     * @param start is piece initial location
     * @param end is piece new location
     */
    public Turn movePiece(Location start, Location end){
        Turn currentTurn = new Turn();

        //basic
        currentTurn.movePiece(start, end);
        PieceInterface movedPiece = pieceGrid[start.getRow()][start.getCol()];

        // add to removed list if piece exists at destination
        if(pieceGrid[end.getRow()][end.getCol()] != null) {
            currentTurn.removePiece(end);
        }

        // move piece from start to end
        pieceGrid[end.getRow()][end.getCol()] = movedPiece;
        pieceGrid[start.getRow()][start.getCol()] = null;

        // castling

        return currentTurn;
    }

    /**
     * return a list of all legal moves for a piece at a location
     * @param location
     * @return
     */
    public List<Location> getLegalMoves(Location location){
        List<Location> legalMoves = new ArrayList<>();
        // get piece at location
        PieceInterface piece = pieceGrid[location.getRow()][location.getCol()];

        // get moves from piece
        Piece.MoveVector vectors = piece.getMoves();

        for(int i = 0; i < vectors.getMoveVectors().size(); i++) {
            int pieceRow = location.getRow() + vectors.getRowVector(i) * piece.getTeam();
            int pieceCol = location.getCol() + vectors.getColVector(i) * piece.getTeam();

            // while the new locations are in bounds
            while(inBounds(pieceRow, pieceCol)){
                // same team check
                if(pieceGrid[pieceRow][pieceCol].getTeam() == piece.getTeam()){
                    break;
                }
                legalMoves.add(new Location(pieceRow, pieceCol));
                if (piece.isLimited()){
                    break;
                }

                pieceRow += vectors.getRowVector(i) * piece.getTeam();
                pieceCol += vectors.getColVector(i) * piece.getTeam();
            }
        }
//        // if king is in check remove moves that do not stop check

        return legalMoves;
    }

    private boolean inBounds(int newRow, int newCol) {
        return (newRow < pieceGrid.length && newCol < pieceGrid[0].length && newRow >= 0 && newCol >= 0);
    }

    /**
     * Determine whether the win condition of the game is satisfied, and declare a winner.
     */
    public boolean gameFinished(){
        return false;
    }
}
