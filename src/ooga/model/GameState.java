package ooga.model;

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

    public GameState getWinner(String team){
        return team.equals("w") ? BLACK : WHITE;
    }
}