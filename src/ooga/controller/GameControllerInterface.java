package ooga.controller;

import java.util.List;
import java.util.Map;
import javafx.beans.property.StringProperty;

/**
 * @Authors gordon albert luis richard tarun sam
 *
 * purpose - this class defines the methods that are specific to the GameController object.
 * assumptions - that all the methods below are defined
 * dependencies - this class depends on the game model and view classes, and the boardbuilder classes.
 * To use - The user create a new game controller object and a default view and model will be created. If a file
 *  is selected, the file is sent to boardbuilder and then new view and model objects are created and
 *  the game is remade.
 */
public interface GameControllerInterface extends ControllerInterface{

  /**
   * this is called by the slider in view that allows the user to change the amount of time per game
   * upon resetting the game, the new game will its timer start at this time.
   * @param minutes - the new time per game
   */
  void setInitialTime(int minutes);

  /**
   * this is called by the slider in view that allows the user to change the amount of time gained per
   * move upon resetting the game, the new game will increment the timer by this amount.
   * @param seconds - the new time bonus per played move
   */
  void setIncrement(int seconds);


  /**
   * this method finds all the players' usernames mapped to their respective team
   * @return - a map of team to player username
   */
  Map<Enum, String> getUsernames();

  /**
   * this method finds the amount of times the players have won a game
   * @return - a map of team to number of wins
   */
  Map<Enum, Integer> getWins();

  /**
   * gets the amount of time left on a player's timer
   * @param side the side of the player
   * @return a StringProperty ("mm:ss") representing the amount of time left
   */
  StringProperty getTimeLeft(int side);

  List<Integer> getUpdatedScores();

  /**
   * This calls methods in the gameboard to perform cheats.
   * @param cheat the name of the cheat defined in Cheat.properties
   */
  void handleCheat(String cheat);
}
