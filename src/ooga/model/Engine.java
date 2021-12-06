package ooga.model;

import ooga.Location;
import ooga.controller.Config.InvalidPieceConfigException;

import java.io.FileNotFoundException;
import java.util.List;

public interface Engine {
  /**
   * Create default board of pieces
   */
//    void initializePlayers(List<PlayerInterface> players);

  /**
   * Moves piece from start to end and updates the board
   *
   * @param start is piece initial location
   * @param end   is piece new location
   */
  void movePiece(Location start, Location end);

  /**
   * Determine whether the win condition of the game is satisfied, and declare a winner.
   */
  GameState checkGameState();

  List<Location> getLegalMoves(Location location);

  List<PlayerInterface> getPlayers();

  List<PieceInterface> getPieces();

  boolean canMovePiece(Location location);

  void addPiece(String team, String name, Location location);
}
