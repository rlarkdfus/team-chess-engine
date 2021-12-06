package ooga.model;

import ooga.Location;
import ooga.LogUtils;
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

    protected List<PlayerInterface> players;
    protected List<PieceInterface> pieces;

    protected List<PowerupInterface> powerupInterfaceList;
    protected PlayerInterface currentPlayer;


    public Board(List<PlayerInterface> players) {
        this.players = players;
        pieces = new ArrayList<>();
        for (PlayerInterface player : players) {
            pieces.addAll(player.getPieces());
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
    public void movePiece(Location start, Location end) {
        PieceInterface piece = MoveUtility.pieceAt(start, pieces);
        executeMove(piece, end);
        updateGameRules(piece);
        updateLegalMoves();
    }

    private void executeMove(PieceInterface piece, Location end) {
        Move move = getMove(end, piece);
        move.executeMove(piece, pieces, end);
        updatePlayerPieces();
    }

    protected abstract Move getMove(Location end, PieceInterface piece);

    private void updatePlayerPieces() {
        for (PlayerInterface player : players) {
            player.clearPieces();
            for (PieceInterface piece : pieces) {
                player.addPiece(piece);
            }
        }
    }

    protected abstract void updateGameRules(PieceInterface piece);

    protected void updateLegalMoves() {
        for (PieceInterface piece : pieces) {
            piece.updateMoves(new ArrayList<>(pieces));
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

    @Override
    public void addPiece(String team, String name, Location location) {
        PieceInterface newPiece = new Piece(team, name, null, null, 0);
        for(PlayerInterface player : players) {
            if(newPiece.getTeam().equals(player.getTeam())) {
                player.addPiece(newPiece);
                System.out.println(player.getTeam()+": " + player.getPieces());
                break;
            }
        }
        executeMove(newPiece, location);
        pieces.add(newPiece);
        System.out.println("addPiece\n" + this);

    }

    @Override
    public List<PieceInterface> getPieces() {
        return new ArrayList<>(pieces);
    }

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
