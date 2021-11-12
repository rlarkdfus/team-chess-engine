package ooga.model;

import ooga.Location;

import java.util.ArrayList;
import java.util.List;

public class Board implements Engine {
    private int row;
    private int col;
    private PieceInterface[][] pieceGrid;

    public Board(){
        initializeBoard();
    }

    /**
     * Create default board of pieces
     */
    public void initializeBoard(){
        pieceGrid = new P
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
        PieceInterface movedPiece = pieceGrid[start.getX()][start.getY()];

        // add to removed list if piece exists at destination
        if(pieceGrid[end.getX()][end.getY()] != null) {
            currentTurn.removePiece(end);
        }

        // move piece from start to end
        pieceGrid[end.getX()][end.getY()] = movedPiece;
        pieceGrid[start.getX()][start.getY()] = null;

        // castling

        return currentTurn;
    }

    /**
     * return a list of all legal moves for a selected piece
     * @param location
     * @return
     */
    public List<Location> getPossibleMoves(Location location){
        List<Location> moves = new ArrayList<>();

        // get piece at location


        // get moves from piece


        // check moves are legal


        return moves;
    }

    /**
     * Determine whether the win condition of the game is satisfied, and declare a winner.
     */
    public boolean gameFinished(){
        return false;
    }
}
