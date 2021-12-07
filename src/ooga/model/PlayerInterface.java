package ooga.model;

import javafx.beans.property.StringProperty;

import java.util.List;

/**
 * @authors purpose - this is the api for interacting with the players to manipulate their pieces and their timers
 * assumptions - it assumes that the timers and pieces are all valid
 * dependencies - it does not depend on anything
 * usage - the board gets pieces from the players, and can interact with the timers
 */
public interface PlayerInterface {
  /**
   * this method toggles the timer to start and stop on a players turn
   */
  void toggleTimer();

  /**
   * sets the player's time to the value of its timer's initial minutes but does not change its on/off state
   */
  void setToInitialTime();

  /**
   * gets the remaining time a player has
   *
   * @return the remaining time
   */
  StringProperty getTimeLeft();

  /**
   * reset a player's timer to the initial value
   */
  void resetTimer();

  /**
   * this configures the increment and the initial time of the timer
   *
   * @param initialTime initial time
   * @param increment   the amount to increment timer by after move
   */
  void configTimer(int initialTime, int increment);

  /**
   * set the time increment but through the user interface
   */
  void incrementTimeUserInterface();

  /**
   * sets the time increment to the specified value
   *
   * @param specifiedTime time to increment by
   */
  void incrementTime(Integer specifiedTime);

  /**
   * get a list of all the pieces a player holds
   *
   * @return list of all pieces a player holds
   */
  List<PieceInterface> getPieces();

  /**
   * adds a piece to the player's list of pieces
   *
   * @param piece piece to add to the player's list
   */
  void addPiece(PieceInterface piece);

  /**
   * get a players team
   *
   * @return the team a player is on
   */
  String getTeam();

  /**
   * remove a piece from the player's list of pieces
   *
   * @param piece to remove from the player's list
   */
  void removePiece(PieceInterface piece);

  /**
   * reset the player's list of pieces
   */
  void clearPieces();

  /**
   * get a player's score
   *
   * @return the player's score
   */
  int getScore();
}
