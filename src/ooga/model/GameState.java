package ooga.model;

/**
 * describes the current state of the game
 */
public enum GameState {
    RUNNING ("running"),
    ENDED ("ended"),
    BLACK ("b"),
    WHITE ("w"),
    DRAW ("draw");

    private final String team;

    GameState(String team){
        this.team = team;
    }

    @Override
    public String toString() {
        return team;
    }

    /**
     * parameter team is the winning team
     * @param team winner
     * @return winning team state
     */
    public GameState getWinner(String team){
        return team.equals("w") ? WHITE : BLACK;
    }

    /**
     * parameter team is the losing team
     * @param team loser
     * @return winning team state
     */
    public GameState getLoser(String team) {
        return team.equals("w") ? BLACK : WHITE;
    }
}