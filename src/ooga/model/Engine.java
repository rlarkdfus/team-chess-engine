package ooga.model;

import ooga.Location;
import java.util.List;

/**
 * @authors
 * purpose - this is the api that allows a person to interact with the board
 * assumptions - it assumes that there are players, pieces, etc. for the board to access
 * dependencies - it does not depend on any other methods.
 * usage - interact with the engine api through the defined methods which modify and return
 * the players and pieces making up the board
 */
public interface Engine {
  /**
   * Moves piece from start to end and updates the board
   * @param start is piece initial location
   * @param end is piece new location
   */
  List<PieceInterface> movePiece(Location start, Location end);

  /**
   * Determine whether the win condition of the game is satisfied, and declare a winner.
   * @return the current gamestate
   */
  GameState checkGameState();

  /**
   * get the legal end locations of a piece at location
   * @param location of a piece to get end locations from
   * @return the end locations of the piece at location
   */
  List<Location> getLegalMoves(Location location);

  /**
   * get the list of all players
   * @return list of all players
   */
  List<PlayerInterface> getPlayers();

  /**
   * get the list of all pieces
   * @return list of all pieces
   */
  List<PieceInterface> getPieces();

  /**
   * determine whether a piece at location can move
   * @param location of a piece to move
   * @return whether the piece can move
   */
  boolean canMovePiece(Location location);

  /**
   * add a piece to the board
   * @param team team of the piece
   * @param name name of the piece
   * @param location location of the piece
   */
  void addPiece(String team, String name, Location location);
}
