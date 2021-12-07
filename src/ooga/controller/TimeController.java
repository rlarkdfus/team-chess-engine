package ooga.controller;

import ooga.model.PlayerInterface;

import java.util.List;

public class TimeController {
  private static int initialTime;
  private static int increment;

  public TimeController(int initialTimeMinutes, int incrementSeconds) {
    initialTime = initialTimeMinutes;
    increment = incrementSeconds;
  }

  /**
   * configures all players' timers with the same initial time (min) and increment (s)
   *
   * @param players the list of players
   */
  public void configTimers(List<PlayerInterface> players) {
    for (PlayerInterface player : players) {
      player.configTimer(initialTime, increment);
    }
  }

  public void setInitialTime(List<PlayerInterface> players, int minutes) {
    initialTime = minutes;
    configTimers(players);
    setToInitialTime(players);
  }

  public void setToInitialTime(List<PlayerInterface> players) {
    for (PlayerInterface player : players) {
      player.setToInitialTime();
    }
  }

  public void setIncrement(List<PlayerInterface> players, int seconds) {
    increment = seconds;
    configTimers(players);
  }

  /**
   * starts the timer of the first player
   *
   * @param players
   */
  public void startPlayer1Timer(List<PlayerInterface> players) {
    if (!players.isEmpty()) {
      players.get(0).toggleTimer();
    }
  }

  /**
   * resets all players' timers
   *
   * @param players
   */
  public void resetTimers(List<PlayerInterface> players) {
    players.forEach(PlayerInterface::resetTimer);
  }
}
