package ooga.model;

import ooga.Location;
import ooga.model.Moves.Move;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import ooga.model.Moves.MoveUtility;
import ooga.model.Powerups.PowerupInterface;

/**
 * @authors
 * purpose - the purpose of this class is to manage the board that the controller interacts with.
 * it provides methods for moving pieces and keeping track of game state
 * assumptions - this class assumes that all the pieces are valid, and that the game states are valid.
 * it requires a king for each player.
 * dependencies - this class depends on the PlayerInterface, PieceInterface, Location, and PowerupInterface
 * usage - a board is constructed with a list of players and bounds, and its methods are called
 * such as movePiece and executeMove to update the board's pieces.
 */
public abstract class Board implements Engine {
    public static ResourceBundle PIECES = ResourceBundle.getBundle("ooga/model/StandardBoard");
    private Location bounds;
    protected List<PlayerInterface> players;
    protected List<PieceInterface> pieces;
    protected List<PowerupInterface> powerupInterfaceList;
    protected PlayerInterface currentPlayer;

    /**
     * the board is constructed by passing in a list of players and bounds. the players each hold
     * their pieces, and bounds are used to validate locations
     * @param players list of players
     * @param bounds boundaries of the board
     */
    public Board(List<PlayerInterface> players, Location bounds) {
        this.bounds = bounds;
        this.players = players;
        pieces = new ArrayList<>();
        for (PlayerInterface player : players) {
            pieces.addAll(player.getPieces());
        }
        powerupInterfaceList = new ArrayList<>();
    }

    /**
     * this method returns the list of all players
     * @return list of all players
     */
    public List<PlayerInterface> getPlayers() {
        return players;
    }

    public List<PowerupInterface> getPowerUps() {
        return powerupInterfaceList;
    }

    /**
     * Moves piece from start to end and updates the board
     * @param start is piece initial location
     * @param end is piece new location
     */
    public void movePiece(Location start, Location end) {
        PieceInterface piece = MoveUtility.pieceAt(start, pieces);
        executeMove(piece, end);
        updateGameRules(piece);
        updateLegalMoves();
    }

    protected void executeMove(PieceInterface piece, Location end) {
        Move move = getMove(end, piece);
        move.executeMove(piece, pieces, end);
        updatePlayerPieces();
    }

    /**
     * This class gets the move that results in a piece moving to the specified end location
     * @param end end location for a move
     * @param piece the piece that is being moved
     * @return the move that moves a piece to the end location
     */
    protected abstract Move getMove(Location end, PieceInterface piece);

    private void updatePlayerPieces() {
        for (PlayerInterface player : players) {
            player.clearPieces();
            for (PieceInterface piece : pieces) {
                if(piece.getTeam().equals(player.getTeam())) {
                    player.addPiece(piece);
                }
            }
        }

    }

    /**
     * this method updates the game rules for a piece
     * @param piece piece to update rules
     */
    protected abstract void updateGameRules(PieceInterface piece);

    /**
     * this method returns the bounds of the board
     * @return the bounds of the board
     */
    protected Location getBounds() {
        return bounds;
    }

    /**
     * this method updates the legal moves of each piece on the board
     */
    protected void updateLegalMoves() {
        for (PieceInterface piece : pieces) {
            piece.updateMoves(new ArrayList<>(pieces));
        }
    }

    /**
     * determine whether player selects their own piece on their turn
     * @param location location that a player selects
     * @return whether that location has a piece that the player can move
     */
    public abstract boolean canMovePiece(Location location);

    /**
     * return a list of all legal moves for a piece at a location
     * @param location that a player selects
     * @return the list of legal end locations of a piece at that location making a move
     */
    public abstract List<Location> getLegalMoves(Location location);

    /**
     * this method returns a list of all the pieces
     * @return list of all pieces
     */
    @Override
    public List<PieceInterface> getPieces() {
        return new ArrayList<>(pieces);
    }

    /**
     * this method overrides toString and prints out the current board state in an easily digestable
     * format
     * @return string representation of the board
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\t 0\t 1\t 2\t 3\t 4\t 5\t 6\t 7\n");
        for (int i = 0; i < 8; i++) {
            str.append(i).append("\t|");
            for (int j = 0; j < 8; j++) {
                Location location = new Location(i, j);
                boolean found = false;

                for (PieceInterface piece : pieces) {
                    if (piece.getLocation().equals(location)) {
                        str.append(piece).append("\t");
                        found = true;
                    }
                }
                if (!found) {
                    str.append("\t");
                }
                str.append("|");
            }
            str.append("\n");
        }
        str.append("____________________________________\n");
        return str.toString();
    }
}
