package ooga.model;

import ooga.Location;
import ooga.Turn;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.model.Moves.Move;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import ooga.model.Moves.MoveUtility;
import ooga.model.Powerups.PowerupInterface;

public abstract class Board implements Engine {

    public static final String QUEEN = "Q";
    public static final String KING = "K";
    private static final int FIRST_ROW = 0;
    private static final int ROWS = 8;
    private static final int LAST_ROW = ROWS - 1;
    private static final int COLS = 8;
    protected List<PlayerInterface> players;
    protected List<PieceInterface> allPieces;
    protected List<PowerupInterface> powerupInterfaceList;
    protected PlayerInterface currentPlayer;

    public Board(List<PlayerInterface> players) {
        this.players = players;
        allPieces = new ArrayList<>();
        for (PlayerInterface player : players) {
            allPieces.addAll(player.getPieces());
        }
        powerupInterfaceList = new ArrayList<>();
    }

    /**
     * this method returns the list of all players
     *
     * @return
     */
    public List<PlayerInterface> getPlayers() {
        return players;
    }

    /**
     * Moves piece from start to end and updates the board
     *
     * @param start is piece initial location
     * @param end   is piece new location
     */
    public List<PieceInterface> movePiece(Location start, Location end) throws FileNotFoundException, InvalidPieceConfigException {
        PieceInterface piece = MoveUtility.pieceAt(start, allPieces);
        Move move = getMove(end, piece);
        move.executeMove(piece, allPieces, end);
        updatePlayerPieces(piece, move.getTurn());
        updateGameRules();
        for(PowerupInterface powerupInterface: powerupInterfaceList){
            powerupInterface.checkPowerUp(piece,end,currentPlayer,allPieces);
        }
        updateLegalMoves();
        return allPieces;
    }

    protected abstract Move getMove(Location end, PieceInterface piece);

    private void updatePlayerPieces(PieceInterface piece, Turn turn) {
        for (Location removeLocation : turn.getRemoved()) {
            for (PlayerInterface player : players) {
                for (PieceInterface p : player.getPieces()) {
                    if (p.getLocation().equals(removeLocation) && !p.equals(piece)) {
                        player.removePiece(p);
                    }
                }
            }
        }
    }

    protected abstract void updateGameRules();

    protected void updateLegalMoves() {
        for (PieceInterface piece : allPieces) {
            piece.updateMoves(new ArrayList<>(allPieces));
        }
    }

    /**
     * see if the game is still running or if its over
     *
     * @return
     */
    @Override
    public abstract GameState checkGameState();

    /**
     * determine whether player selects their own piece on their turn
     *
     * @param location
     * @return
     */
    public abstract boolean canMovePiece(Location location);

    /**
     * return a list of all legal moves for a piece at a location
     *
     * @param location
     * @return
     */
    public abstract List<Location> getLegalMoves(Location location);

    /**
     * this method overrides toString and prints out the current board state in an easily digestable
     * format
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("\t 0\t 1\t 2\t 3\t 4\t 5\t 6\t 7\n");
        for (int i = 0; i < 8; i++) {
            str.append(i + "\t|");
            for (int j = 0; j < 8; j++) {
                Location location = new Location(i, j);
                boolean found = false;

                for (PieceInterface piece : allPieces) {
                    if (piece.getLocation().equals(location)) {
                        str.append(piece.toString() + "\t");
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
