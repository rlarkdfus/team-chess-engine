package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import ooga.model.EndConditionHandler.EndConditionRunner;
import ooga.model.PlayerInterface;

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
   * Overridden interface method. First parses the input file then builds 3 things that will be used:
   *  playerList - a list of players that is used in Board
   *  pieceList - a list of PieceViewBuilder objects that is used in View to build PieceView Objects
   *  endCondition - an EndConditionHandler Object that is used by Board to determine if the game is over
   *
   * @param file - the file to be parsed and used to build the json object
   * @throws CsvException - if the game's csv isn't 8x8
   * @throws FileNotFoundException - if any files referenced in the game json aren't existent
   * @throws PlayerNotFoundException - if any pieces in the json are assigned to nonexistent players
   * @throws InvalidPieceConfigException - if any piece configuration jsons are invalid
   * @throws InvalidGameConfigException - if the game json is invalid
   * @throws InvalidEndGameConfigException - if the rule json is invalid
   */
  /**
   *
   * @param file
   * @throws FileNotFoundException
   * @throws PlayerNotFoundException
   * @throws InvalidPieceConfigException
   * @throws InvalidGameConfigException
   * @throws InvalidEndGameConfigException
   * @throws CsvException
   */
  void build(File file)
      throws FileNotFoundException, PlayerNotFoundException, InvalidPieceConfigException, InvalidGameConfigException, InvalidEndGameConfigException, CsvException;

  List<PieceViewBuilder> getInitialPieceViews();

  List<PlayerInterface> getInitialPlayers();

  EndConditionRunner getEndConditionHandler();

//  PieceInterface convertPiece(PieceInterface piece, String pieceType)
//      throws FileNotFoundException, InvalidPieceConfigException;
}
