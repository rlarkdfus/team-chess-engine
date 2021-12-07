package ooga.model;

public interface GameEngine extends Engine {
  /**
   * Determine whether the win condition of the game is satisfied, and declare a winner.
   *
   * @return the current gamestate
   */
  GameState checkGameState();
}
