package ooga.controller;

import ooga.model.PlayerInterface;

import java.util.List;

public class TimeController {
    private int initialTime;
    private int increment;

    public TimeController(int initialTimeMinutes, int incrementSeconds) {
        setInitialTime(initialTimeMinutes);
        setIncrement(incrementSeconds);
    }

    /**
     * configures all players' timers with the same initial time (min) and increment (s)
     * @param players the list of players
     */
    public void configTimers(List<PlayerInterface> players) {
        for (PlayerInterface player : players) {
            player.configTimer(initialTime, increment);
        }
    }

    public void setInitialTime(int minutes) {
        this.initialTime = minutes;
    }

    public void setIncrement(int seconds) {
        this.increment = seconds;
    }

    public void startPlayer1Timer(List<PlayerInterface> players) {
        players.get(0).toggleTimer();
    }

    public void resetTimers(List<PlayerInterface> players) {
        for (PlayerInterface player : players) {
            player.resetTimer();
        }
    }
}
