package ooga.model;

import ooga.Location;
import ooga.Turn;

import java.util.ArrayList;
import java.util.List;

public class Board implements Engine {

    private final String[] CHESS_SIDES = {"white", "black"};
    private PieceInterface[][] pieceGrid;
    private int totalTurns;
    private List<PlayerInterface> players;

    public Board() {
        initializePlayers();
        initializeBoard();
    }

    public Board(PieceInterface[][] pieceGrid) {
        this.pieceGrid = pieceGrid;
    }

    private void initializePlayers() {
        totalTurns = 0;
        players = new ArrayList<>();
        players.add(new Player("white"));
        players.add(new Player("black"));
        // loop through players passed by controller and add them to players
    }

    /**
     * Create default board of pieces
     */
    public void initializeBoard(){
        pieceGrid = new PieceInterface[8][8];
        for(int i=0; i<2; i++) {
            int pawnRow = i == 0 ? 6 : 1;
            int pieceRow = i == 0 ? 7 : 0;
            for(int j = 0; j < 8; j++) {
                List<Piece.Vector> vectors = new ArrayList<>();

                vectors.add(new Piece.Vector(-1, 0));
                vectors.add(new Piece.Vector(1, 0));
                vectors.add(new Piece.Vector(0, 1));
                vectors.add(new Piece.Vector(0, -1));

                PieceInterface pawn = new Piece(CHESS_SIDES[i], vectors, false);
                PieceInterface piece = new Piece(CHESS_SIDES[i],vectors, false);

                for(PlayerInterface player : players) {
                   if(player.getTeam().equals(piece.getTeam())) {
                       player.addPiece(piece);
                       player.addPiece(pawn);
                   }
                }

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

        // increment whose turn it is
        totalTurns++;

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

        System.out.println("board received location " + location.getRow() + " " + location.getCol());
        // get moves from piece
        Piece.MoveVector vectors = piece.getMoves();

        for(int i = 0; i < vectors.getMoveVectors().size(); i++) {
            int pieceRow = location.getRow() + vectors.getRowVector(i);
            int pieceCol = location.getCol() + vectors.getColVector(i);
            System.out.println("potential location: " + pieceRow + " " + pieceCol);
            // while the new locations are in bounds
            while(inBounds(pieceRow, pieceCol)){
                // same team check
                if(pieceGrid[pieceRow][pieceCol] != null && pieceGrid[pieceRow][pieceCol].getTeam() == piece.getTeam()){
                    break;
                }
                legalMoves.add(new Location(pieceRow, pieceCol));
                if(pieceGrid[pieceRow][pieceCol] != null && pieceGrid[pieceRow][pieceCol].getTeam() != piece.getTeam()) {
                    break;
                }
                if (piece.isLimited()){
                    break;
                }

                pieceRow += vectors.getRowVector(i);
                pieceCol += vectors.getColVector(i);
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

    /**
     * determine whether player selects their own piece on their turn
     * @param location
     * @return
     */
    public boolean canMovePiece(Location location) {
        PieceInterface piece = pieceGrid[location.getRow()][location.getCol()];
        int index = totalTurns % players.size();
        return (piece != null && piece.getTeam().equals(players.get(index).getTeam()));
    }
}
