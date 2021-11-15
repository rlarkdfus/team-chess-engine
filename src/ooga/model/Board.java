package ooga.model;

import java.util.Map;
import ooga.Location;
import ooga.Turn;

import java.util.ArrayList;
import java.util.List;

public class Board implements Engine {

    private final String[] CHESS_SIDES = {"w", "b"};
    private int turnCount;
    private List<PlayerInterface> players;

    public Board() {

    }

    @Override
    public void initializePlayers(List<PlayerInterface> players) {
        turnCount = 0;
        this.players = players;
        // loop through players passed by controller and add them to players
    }

    /**
     * Moves piece from start to end and updates the board
     * @param start is piece initial location
     * @param end is piece new location
     */
    public Turn movePiece(Location start, Location end){
        Turn currentTurn = new Turn();
        PlayerInterface currentPlayer = findPlayerTurn(turnCount);
        PlayerInterface otherPlayer = findPlayerTurn(turnCount + 1);


        // check for castling

        // add to removed list if piece exists at destination
        if(otherPlayer.getPiece(end) != null) {
            currentTurn.removePiece(end);
            otherPlayer.removePiece(end);
        }

        //basic move piece
        currentTurn.movePiece(start, end);
        currentPlayer.movePiece(pieceAt(start), end);

        // increment turn
        turnCount++;

        return currentTurn;
    }


    /**
     * Limits moves based on checks
     * @param player is the current player moving the piece
     * @param piece is the piece player is attempting to move
     * @return List of legal locations for @param piece
     */
    private List<Location> findLegalMoves(PlayerInterface player, PieceInterface piece) {
        List<Location> legalMoves = new ArrayList<>();

        // try all moves see if king in check
        for(Location location : findAllMoves(piece)) {
            if(tryMove(player, piece, location)) {
                legalMoves.add(location);
            }
        }

        return legalMoves;
    }

    /**
     * Helper function to see if potential move is legal
     * @param player is current player moving the piece
     * @param piece is the piece player is attempting to move
     * @param location is the location the player is attempting to move the piece to
     * @return if the move is legal or not
     */
    private boolean tryMove(PlayerInterface player, PieceInterface piece, Location location) {
        PlayerInterface otherPlayer = findNextPlayer(player);
        PieceInterface king = pieceAt(player.getKingLocation());
        Location pieceLocation = new Location(piece.getLocation().getRow(), piece.getLocation().getCol());
        PieceInterface takenPiece = null;

        // theoretically move piece to location
        player.movePiece(piece, location);
        if(pieceAt(location) != null) { //take piece if exists
            takenPiece = otherPlayer.getPiece(location);
            otherPlayer.removePiece(location);
        }

        // if the king is in check, undo move and return false
        if(inCheck(king, otherPlayer.getPieces())) {
            undoTryMove(player, piece, pieceLocation, takenPiece);
            return false;
        }

        //otherwise undo the move and return true
        undoTryMove(player, piece, pieceLocation, takenPiece);
        return true;
    }


    /**
     * Undo the tried move after trying the move
     * @param player is current player moving the piece
     * @param piece is the piece player moved
     * @param pieceLocation is the original location the player is attempting to move the piece to
     * @param takenPiece is the piece that was taken during the turn, if a piece was taken
     */
    private void undoTryMove(PlayerInterface player, PieceInterface piece, Location pieceLocation, PieceInterface takenPiece) {
        player.movePiece(piece, pieceLocation);
        if (takenPiece != null) {
            findNextPlayer(player).addPiece(takenPiece);
        }
    }

    /**
     * Returns list of all possible locations of a given piece based on other pieces disregarding checks
     * @param piece
     * @return
     */
    private List<Location> findAllMoves(PieceInterface piece) {
        List<Location> moves = new ArrayList<>();
        Location location = piece.getLocation();

        // get moves from piece
        MoveVector vectors = piece.getMoves();

        for(int i = 0; i < vectors.getMoveVectors().size(); i++) {
            int pieceRow = location.getRow() + vectors.getRowVector(i);
            int pieceCol = location.getCol() + vectors.getColVector(i);

            // while the new locations are in bounds
            while(inBounds(pieceRow, pieceCol)){
                Location potentialLocation = new Location(pieceRow, pieceCol);
                PieceInterface potentialPiece = pieceAt(potentialLocation);

                // new spot has same team
                if(potentialPiece != null && potentialPiece.getTeam().equals(piece.getTeam())){
                    break;
                }

                moves.add(new Location(pieceRow, pieceCol));
                if(potentialPiece != null && !potentialPiece.getTeam().equals(piece.getTeam())) {
                    break;
                }
                if (piece.isLimited()){
                    break;
                }

                pieceRow += vectors.getRowVector(i);
                pieceCol += vectors.getColVector(i);
            }
        }
        return moves;
    }

    /**
     * Checks if the king is under attack from enemy pieces
     * @param king is the piece under sttack
     * @param attackingPieces is the list of pieces attacking the king
     * @return true if the king is under attack from list of pieces
     */
    private boolean inCheck(PieceInterface king, List<PieceInterface> attackingPieces) {
        Location kingLocation = king.getLocation();

        for(PieceInterface piece : attackingPieces) {
            for(Location attackLocation : findAllMoves(piece)) {
                if(kingLocation.equals(attackLocation)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * return a list of all legal moves for a piece at a location
     * @param location
     * @return
     */
    public List<Location> getLegalMoves(Location location){
        PieceInterface piece = pieceAt(location);
        return (piece != null) ? findLegalMoves(findPlayerTurn(turnCount), piece) : null;
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
        PieceInterface piece = pieceAt(location);
        return (piece != null && piece.getTeam().equals(findPlayerTurn(turnCount).getTeam()));
    }

    private PieceInterface pieceAt(Location location) {
        for(PlayerInterface player : players) {
            if(player.getPiece(location) != null) {
                return player.getPiece(location);
            }
        }
        return null;
    }

    private PlayerInterface findPlayerTurn(int turn) {
        return players.get(turn % players.size());
    }

    private PlayerInterface findNextPlayer(PlayerInterface currentPlayer) {
        return players.get((players.indexOf(currentPlayer) + 1)% players.size());
    }

    private boolean inBounds(int newRow, int newCol) {
        return (newRow < 8 && newCol < 8 && newRow >= 0 && newCol >= 0); //FIXME: hardcoded row col
    }
}
