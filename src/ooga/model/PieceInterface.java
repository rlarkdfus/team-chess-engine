package ooga.model;

import java.util.List;

import ooga.Location;
import ooga.model.Moves.Move;

/**
 * @authors
 * purpose - this is the pieceinterface api which provides all the methods for interacting with
 * the piece
 * assumptions - it assumes that a piece exists and has valid attributes
 * dependencies - it does not depend on any other classes
 * usage - moves and other classes use this api to move the pieces and update their locations
 */
public interface PieceInterface {
    /**
     * gets the team of a piece
     * @return the team of a piece
     */
    String getTeam();

    /**
     * get the score of a piece
     * @return score of a piece
     */
    int getScore();

    /**
     * get the name of a piece
     * @return name of a piece
     */
    String getName();

    /**
     * get the location of a piece
     * @return location of a piece
     */
    Location getLocation();

    /**
     * move a piece to a location
     * @param location location to move to
     */
    void moveTo(Location location);

    /**
     * test out a move to a location, without updating the move and firstmove booleans
     * @param location location to move to
     */
    void tryMove(Location location);

    /**
     * get all the end locations a piece can move to
     * @return list of all the end locations
     */
    List<Location> getEndLocations();

    /**
     * has a piece been moved before
     * @return whether a piece has been moved before
     */
    boolean hasMoved();

    /**
     * is the previous move a piece's first move
     * @return whether a piece has just done its first move
     */
    boolean isFirstMove();

    /**
     * transform a piece into the newpiece and take on its attributes
     * @param newPiece piece to transform into
     */
    void transform(PieceInterface newPiece);

    /**
     * get the move that moves a piece to end location
     * @param end location that the move goes to
     * @return move that goes to the location
     */
    Move getMove(Location end);

    /**
     * updates the legal moves of a piece after moving
     * @param pieces all the pieces
     */
    void updateMoves(List<PieceInterface> pieces);

    /**
     * gets the list of all possible moves for a piece
     * @return the list of all possible moves for a piece
     */
    List<Move> getMoves();

    /**
     * determine whether the piece and another piece are on the same team
     * @param piece the other piece to compare to
     * @return whether pieces are on the same team
     */
    boolean isSameTeam(PieceInterface piece);
}
