package ooga.controller.Config;

import static ooga.controller.Config.BoardBuilder.JSON_DELIMITER;
import static ooga.controller.Config.BoardBuilder.PIECE_TYPE;
import static ooga.controller.Config.BoardBuilder.PROPERTIES_FILE;
import static ooga.controller.Config.BoardBuilder.RULE_TYPE;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.model.EndConditionHandler.EndConditionInterface;
import ooga.model.EndConditionHandler.EndConditionRunner;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @Authors Albert
 * purpose - this class builds EndCondition objects that will be used by the model to determine when
 *  the game ends. We also check that the EndCondition is possible to be fulfilled (ie there are at
 *  least 2 pieces on either team if the goal is to eliminate 2 pieces). All of these endconditions
 *  are then stored in an EndConditionRunner, which holds them and runs all of them.
 * assumptions - we assume that the filepath points to a valid json file and that the player list is
 *  populated with players that have pieces. If any of these assumptions aren't correct, we throw an
 *  InvalidEndGameException.
 * dependencies - this class depends on the JsonParser
 * To use - The user will getEndConditions() with the filepath to a valid rules .json file, and a
 *  list of players, which has lists of pieces. This will return an EndConditionRunner that will be
 *  used to run all the various end conditions and determine a winner.
 */
public class EndConditionBuilder {

  public static final String ENDCONDITION_PREFIX = "ooga.model.EndConditionHandler.";
  public static final String ENDCONDITION_SUFFIX = "EndCondition";
  private static JsonParser jsonParser;
  private static ResourceBundle mappings;
  public static final String INVALID_ENDCONDITION = "invalidEndConditionMessage";
  public static final String RULEKEYS = "RuleKeys";

  /**
   * This return the EndConditionRunner object that holds 1 or many EndCondition Objects. This will
   *  be used by the model to determine when the game is over.
   * @param ruleJsonFilepath - a path to a json file containing information to build the end conditions
   * @param playerList - a list of all the players which each have their own pieces
   * @return - an EndConditionRunner object with all the end conditions specified by the given rule
   *  json file
   * @throws InvalidEndGameConfigException - if anything in the rule json file isn't valid, this
   *  exception is thrown.
   */
  public static EndConditionRunner getEndConditions(String ruleJsonFilepath, List<PlayerInterface> playerList)
      throws InvalidEndGameConfigException {
    jsonParser = new JsonParser();
    mappings = ResourceBundle.getBundle(PROPERTIES_FILE);

    try {
      return buildEndConditionHandler(ruleJsonFilepath, playerList);
    } catch (Exception e) {
      throw new InvalidEndGameConfigException(e.getClass());
    }
  }

  /**
   * parses rule json file, builds the endcondition objects, and adds them to the runner
   */
  private static EndConditionRunner buildEndConditionHandler(String ruleJsonFilepath,
      List<PlayerInterface> playerList)
      throws FileNotFoundException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InvalidEndGameConfigException, InvalidGameConfigException {

    EndConditionRunner endConditionsHandler = new EndConditionRunner();

    JSONObject RulesJSONObject = jsonParser.loadFile(new File(ruleJsonFilepath));

    for (String endCondition : RulesJSONObject.keySet()) {
      JSONObject endConditionsJSONObject = RulesJSONObject.getJSONObject(endCondition);
      endConditionsHandler.add(buildEndCondition(playerList, endConditionsJSONObject));
    }
    return endConditionsHandler;
  }

  /**
   * builds the endcondition object using reflection
   */
  private static EndConditionInterface buildEndCondition(List<PlayerInterface> playerList,
      JSONObject endConditionsJSONObject)
      throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InvalidEndGameConfigException, InvalidGameConfigException {
    EndConditionInterface endCondition;

    String endConditionType = endConditionsJSONObject.getString(mappings.getString(RULE_TYPE));

    String[] endConditionParameters = mappings.getString(endConditionType + RULEKEYS)
        .split(mappings.getString(JSON_DELIMITER));

    Map<String, List<String>> endConditionProperties = makeProperties(
        endConditionsJSONObject, endConditionParameters);
    List<PieceInterface> initialPieces = makeInitialPieces(playerList);

    Class<?> clazz = Class.forName(ENDCONDITION_PREFIX + endConditionType + ENDCONDITION_SUFFIX);
    endCondition = (EndConditionInterface) clazz.getDeclaredConstructor(Map.class,List.class)
        .newInstance(endConditionProperties, initialPieces);

    checkValidEndCondition(endConditionProperties, playerList);
    return endCondition;
  }

  /**
   * creates a list of all pieces from the given players
   */
  private static List<PieceInterface> makeInitialPieces(List<PlayerInterface> playerList) {
    List<PieceInterface> initialPieces = new ArrayList<>();
    for (PlayerInterface p : playerList) {
      initialPieces.addAll(p.getPieces());
    }
    return initialPieces;
  }

  /**
   * creates a map of all the property types to their values
   */
  private static Map<String, List<String>> makeProperties(JSONObject endConditionsJSONObject,
      String[] endConditionParameters) {
    Map<String, List<String>> endConditionProperties = new HashMap<>();
    for (String argument : endConditionParameters) {
      endConditionProperties.put(argument,
          convertJSONArrayOfStrings(endConditionsJSONObject.getJSONArray(argument)));
    }
    return endConditionProperties;
  }

  /**
   * checks that the endcondition is possible
   */
  private static void checkValidEndCondition(Map<String, List<String>> endConditionProperties, List<PlayerInterface> playerList)
      throws InvalidEndGameConfigException {

    List<String> targetPieces = endConditionProperties.get(mappings.getString(PIECE_TYPE));
    for (PlayerInterface player : playerList) {
      HashMap<String, Integer> playerPieces = getNumberOfPlayerPieces(player);
      tryEliminatingTargetPieces(targetPieces, playerPieces);
      testNotEnoughPlayerPieces(playerPieces);
    }
  }

  /**
   * checks that the number of player pieces is greater or equal to the number of target pieces.
   * throws an exception if this is not the case
   */
  private static void testNotEnoughPlayerPieces(HashMap<String, Integer> playerPieces)
      throws InvalidEndGameConfigException {
    for (String key : playerPieces.keySet()) {
      if (playerPieces.get(key) < 0) {
        throw new InvalidEndGameConfigException(mappings.getString(INVALID_ENDCONDITION));
      }
    }
  }

  /**
   * simulates eliminating all the target pieces. this is used to figure out if there are more
   * target pieces than player pieces (the value of playerpieces will be negative if so)
   */
  private static void tryEliminatingTargetPieces(List<String> endGamePieces, HashMap<String, Integer> playerPieces) {
    for (String p : endGamePieces) {
      playerPieces.putIfAbsent(p, 0);
      playerPieces.put(p, playerPieces.get(p) - 1);
    }
  }

  /**
   * creates a map of the players' pieces to the amount of each piece
   */
  private static HashMap<String, Integer> getNumberOfPlayerPieces(PlayerInterface player) {
    HashMap<String, Integer> playerPieces = new HashMap<>();
    for (PieceInterface p : player.getPieces()) {
      playerPieces.putIfAbsent(p.getName(), 0);
      playerPieces.put(p.getName(), playerPieces.get(p.getName()) + 1);
    }
    return playerPieces;
  }

  /**
   * converts jsonarrays to a list of strings
   */
  private static List<String> convertJSONArrayOfStrings(JSONArray jsonArray) {
    List<String> ret = new ArrayList<>();
    for (int i = 0; i < jsonArray.length(); i++) {
      ret.add(jsonArray.getString(i));
    }
    return ret;
  }

}
