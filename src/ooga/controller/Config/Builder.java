package ooga.controller.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import ooga.Location;
import ooga.model.EndConditionHandler.EndConditionRunner;
import ooga.model.PlayerInterface;
import ooga.model.Powerups.PowerupInterface;

/**
 * @Authors Albert
 * purpose - this class builds objects that will be used by the model to run the game. It builds all
 *  the different pieces, and it builds the endConditions that determine when the game ends.
 * assumptions - we assume that the file is a json and formatted with the proper keys. If any of this
 *  isn't there, then we throw exceptions that get caught and sent to the view.
 * dependencies - this class depends on the EndConditionBuilder, the PieceBuilder, and the JsonParser,
 *  and the LocationParser (aka the csv parser)
 * To use - The user will call build() with a File object. Then to get the objects, the user will call the
 *  3 getter methods.
 */
public interface Builder {

  /**
   * Method that accepts an input file then builds things that will be used to run the game:
   *  1) all the pieces in the game (type, location, mobility), which get assigned to each player
   *  2) builds players that hold the pieces, and sets their timer values
   *  2) an endcondition runner which contains 1 or multiple endCondition objects that determine how the game ends
   *
   * @param file - the file to be parsed and used to build the json object
   * @throws CsvException - if the game's csv isn't 8x8 or if square contains invalid piece data
   * @throws FileNotFoundException - if any files referenced in the game json aren't existent
   * @throws PlayerNotFoundException - if any pieces in the json are assigned to nonexistent players
   * @throws InvalidPieceConfigException - if any piece configuration jsons are invalid
   * @throws InvalidGameConfigException - if the game json is invalid
   * @throws InvalidEndGameConfigException - if the rule json is invalid
   * @throws InvalidPowerupsConfigException - if the powerups json is invalid
   */
  void build(File file)
      throws FileNotFoundException, PlayerNotFoundException, InvalidPieceConfigException, InvalidGameConfigException, InvalidEndGameConfigException, CsvException, InvalidPowerupsConfigException;

  /**
   * Getter method that is used in view.initializeDisplay() to build pieceView objects that are displayed
   * in the view.
   * @return a list of pieceViewBuilder which is a data class that is used to build PieceView objects in the view
   */
  List<PieceViewBuilder> getInitialPieceViews();

  public List<Location> getPowerupLocations();

  /**
   * Getter method that is used to send the board dimensions to the board. This is used when pieces
   * are built so when promotion happens, this is a necessary piece of information for keeping piece
   * movements inbounds
   * @return a Location object where getRows() returns the row limit, and getCol() returns the col limit
   */
  Location getBoardSize();

  /**
   * Getter method that is used to construct the Board. This gives the board all the pieces that are in the
   * game, which each have their own location, team, movement abilities.
   * @return a list of players which contain the pieces
   */
  List<PlayerInterface> getInitialPlayers();

  /**
   * Getter method that is used in board.setEndCondition() to help the board determine how the game ends
   * and who wins.
   * @return an EndConditionRunner object which holds 1 or multiple EndCondition objects and if any of them
   * detect a winner, then it will return a GameState.Winner.
   * */
  EndConditionRunner getEndConditionHandler();

  /**
   * Getter method that is used in the board constructor to help the board determine when and what
   * powerups are to be used.
   * @return a list of powerups objects
   * */
  List<PowerupInterface> getPowerupsHandler();
}
