package ooga.model;

/**
 * @authors sam
 * purpose - describes the current state of the game
 * assumptions - assumes that a game can only be in one of these states
 * dependencies - does not depend on anything
 * usage - can call the gamestate methods to get a winner
 */
public enum GameState {
  RUNNING("running"),
  ENDED("ended"),
  BLACK("b"),
  WHITE("w"),
  DRAW("draw");

  private static final String WHITE_TEAM = "w";
  private final String team;

  /**
   * sets the team of each state
   *
   * @param team
   */
  GameState(String team) {
    this.team = team;
  }

  /**
   * prints out the status of a gamestate
   *
   * @return string status
   */
  @Override
  public String toString() {
    return team;
  }

  /**
   * parameter team is the winning team
   *
   * @param team winner
   * @return winning team state
   */
  public GameState getWinner(String team) {
    return team.equals(WHITE_TEAM) ? WHITE : BLACK;
  }

  /**
   * parameter team is the losing team
   *
   * @param team loser
   * @return winning team state
   */
  public GameState getLoser(String team) {
    return team.equals(WHITE_TEAM) ? BLACK : WHITE;
  }
}