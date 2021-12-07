package ooga.controller;

import java.util.List;
import ooga.model.PlayerInterface;

/**
 * @author richard
 *
 * This class is used by the game controller to handle any game time and time increment changes.
 * We assume that all players have been created.
 * To use, instantiate with an intitial time and time increment and the use the following methods to
 *  change the players' timers
 */
public class TimeController {
    private int initialTime;
    private int increment;

    /**
     * this constructor initiates the initial time and increments variables
     * @param initialTimeMinutes - initial time as defined by the statically defined variable in Controller
     * @param incrementSeconds - initial time increment as defined by the statically defined variable in Controller
     */
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

    /**
     * setter method for changing how much time each player will have to play
     * @param minutes - amount of time each player will have in minutes
     */
    public void setInitialTime(int minutes) {
        initialTime = minutes;
    }

    /**
     * setter method for changing how much time each player gain when they make a move
     * @param seconds - amount of time each player will gain per move in seconds
     */
    public void setIncrement(int seconds) {
        increment = seconds;
    }

    /**
     * this method initiates the first player's timer to start counting down. The first player is the
     * first player that is in the inputted player list. This list's order is based on the order of the
     * "players" field in the game json
     * @param players - a list of all the players in the game
     */
    public void startPlayer1Timer(List<PlayerInterface> players) {
        if (!players.isEmpty()) {
            players.get(0).toggleTimer();
        }
    }

    /**
     * This method resets each player's timer. The value that is reset to is based on the time used
     * during the most recent call of configTimers()
     * @param players
     */
    public void resetTimers(List<PlayerInterface> players) {
        players.forEach(PlayerInterface::resetTimer);
    }
}
