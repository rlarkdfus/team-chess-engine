package ooga.model;

import java.util.List;
import ooga.Location;
import ooga.model.EndConditionHandler.EndConditionRunner;

public interface Engine {
    /**
     * Create default board of pieces
     */
//    void initializePlayers(List<PlayerInterface> players);

    /**
     * Moves piece from start to end and updates the board
     * @param start is piece initial location
     * @param end is piece new location
     */
    List<PieceInterface> movePiece(Location start, Location end);

    /**
     * Determine whether the win condition of the game is satisfied, and declare a winner.
     */
    Board.GameState checkGameState();

    List<Location> getLegalMoves(Location location);

    List<PlayerInterface> getPlayers();

    void setEndCondition(EndConditionRunner endCondition);

  String getWinner();

  PieceInterface getCheckedKing();

  boolean canMovePiece(Location location);
}
