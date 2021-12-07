package ooga.model;

import java.util.ArrayList;
import java.util.List;
import ooga.Location;
import ooga.Main;
import ooga.model.Moves.Move;
import ooga.model.Moves.NoRestrictionMove;

/**
 * @authors
 * purpose - the editor board is used to create the gui editor, where users can visually
 * select and drag pieces around to create a game
 * assumptions - the editor assumes that the pieces are all valid and players are all valid
 * dependencies - the editor depends on PlayerInterface, Location, and PieceInterface
 * usage - the usage is similar to board, and several methods are overridden from board in
 * order to implement moves with no restrictions which allow a user to drag a piece anywhere.
 */
public class EditorBoard extends Board implements EditorEngine {
    /**
     * a board requires a list of players and the bounds to be input, and builds a list of pieces
     * @param players all players on the board
     * @param bounds the bounds of the board
     */
    public EditorBoard(List<PlayerInterface> players, Location bounds) {
        super(players, new Location(Main.DEFAULT_ROW, Main.DEFAULT_COL));
        for(PlayerInterface player : players) {
            for(PieceInterface piece : player.getPieces()) {
                player.removePiece(piece);
            }
        }
        pieces = new ArrayList<>();
    }

    /**
     * returns the move for a piece to get to any square
     * @param end end location for a move
     * @param piece the piece that is being moved
     * @return the move corresponding to moving a piece to end location
     */
    @Override
    protected Move getMove(Location end, PieceInterface piece) {
        Move move = new NoRestrictionMove(0, 0, false, false, getBounds());
        move.updateMoveLocations(piece, new ArrayList<>());
        return move;
    }

    /**
     * get all the locations that a piece can move to from a location
     * @param location that a player selects
     * @return list of all locations
     */
    @Override
    public List<Location> getLegalMoves(Location location) {
        return new NoRestrictionMove(0, 0, false, false, getBounds()).findAllEndLocations(null, null);
    }

    /**
     * transforms a piece
     * @param piece to transform
     */
    @Override
    protected void updateGameRules(PieceInterface piece) {
        PieceInterface newPiece = new Piece(piece.getTeam(), piece.getName(), piece.getLocation(), new ArrayList<>(), piece.getScore());
        piece.transform(newPiece);
    }

    /**
     * this method adds a piece to the board
     * @param team the piece's team
     * @param name the piece's name
     * @param location the location of the piece
     */
    @Override
    public void addPiece(String team, String name, Location location) {
        PieceInterface newPiece = new Piece(team, name, null, null, 0);
        for(PlayerInterface player : players) {
            if(newPiece.getTeam().equals(player.getTeam())) {
                player.addPiece(newPiece);
                break;
            }
        }
        executeMove(newPiece, location);
        pieces.add(newPiece);
    }

    /**
     * return whether a location clicked is a piece
     * @param location location that a player selects
     * @return whether a location clicked is a piece
     */
    @Override
    public boolean canMovePiece(Location location) {
        List<Location> pieceLocations = new ArrayList<>();
        for (PieceInterface piece : pieces) {
            pieceLocations.add(piece.getLocation());
        }
        return location.inList(pieceLocations);
    }
}
