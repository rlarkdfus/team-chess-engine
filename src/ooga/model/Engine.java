package ooga.model;

import java.io.FileNotFoundException;
import java.util.List;
import ooga.Location;
import ooga.controller.Config.InvalidPieceConfigException;
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
    List<PieceInterface> movePiece(Location start, Location end) throws FileNotFoundException, InvalidPieceConfigException;

    /**
     * Determine whether the win condition of the game is satisfied, and declare a winner.
     */
    GameState checkGameState();

    List<Location> getLegalMoves(Location location);

    List<PlayerInterface> getPlayers();

    void setEndCondition(EndConditionRunner endCondition);

  boolean canMovePiece(Location location);
}
