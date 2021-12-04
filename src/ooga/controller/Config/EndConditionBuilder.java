package ooga.controller.Config;

import static ooga.controller.Config.BoardBuilder.JSON_DELIMITER;
import static ooga.controller.Config.BoardBuilder.PIECE_TYPE;
import static ooga.controller.Config.BoardBuilder.PROPERTIES_FILE;
import static ooga.controller.Config.BoardBuilder.RULE_TYPE;

import java.io.File;
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

  JsonParser jsonParser;
  ResourceBundle mappings;
  List<PlayerInterface> playerList;
  public static final String INVALID_ENDCONDITION = "invalidEndConditionMessage";

  /**
   * This constructor creates a jsonparser that will be used to construct the end condition objects,
   * as well as a resource bundle for getting strings values.
   */
  public EndConditionBuilder() {
    jsonParser = new JsonParser();
    mappings = ResourceBundle.getBundle(PROPERTIES_FILE);
  }

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
  public EndConditionRunner getEndConditions(String ruleJsonFilepath, List<PlayerInterface> playerList)
      throws InvalidEndGameConfigException {
    try {
      EndConditionRunner endConditionsHandler = new EndConditionRunner();
      this.playerList = playerList;

      JSONObject endConditions = jsonParser.loadFile(new File(ruleJsonFilepath));
      for (String key : endConditions.keySet()) {
        JSONObject endConditionsJSONObject = endConditions.getJSONObject(key);
        endConditionsHandler.add(buildEndCondition(playerList, endConditionsJSONObject));
      }
      return endConditionsHandler;
    } catch (Exception e) {
      throw new InvalidEndGameConfigException(e.getClass());
    }
  }

  private EndConditionInterface buildEndCondition(List<PlayerInterface> playerList,
      JSONObject endConditionsJSONObject)
      throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InvalidEndGameConfigException, InvalidGameConfigException {
    EndConditionInterface endCondition;

    String type = endConditionsJSONObject.getString(mappings.getString(RULE_TYPE));

    String[] keys = mappings.getString(type + "RuleKeys")
        .split(mappings.getString(JSON_DELIMITER));

    Map<String, List<String>> endConditionProperties = new HashMap<>();
    for (String key : keys) {
      endConditionProperties.put(key,
          convertJSONArrayOfStrings(endConditionsJSONObject.getJSONArray(key)));
    }
    Class<?> clazz = Class.forName("ooga.model.EndConditionHandler." + type + "EndCondition");
    List<PieceInterface> initialPieces = new ArrayList<>();

    for (PlayerInterface p : playerList) {
      initialPieces.addAll(p.getPieces());
    }

    endCondition = (EndConditionInterface) clazz.getDeclaredConstructor(Map.class,List.class)
        .newInstance(endConditionProperties, initialPieces);

    checkValidEndCondition(endConditionProperties);
    return endCondition;
  }

  private void checkValidEndCondition(Map<String, List<String>> endConditionProperties)
      throws InvalidEndGameConfigException {
    String pieceKey = mappings.getString(PIECE_TYPE);
    List<String> endGamePieces = endConditionProperties.get(pieceKey);
    for (PlayerInterface player : playerList) {
      HashMap<String, Integer> playerPieces = new HashMap<>();
      for (PieceInterface p : player.getPieces()) {
        playerPieces.putIfAbsent(p.getName(), 0);
        playerPieces.put(p.getName(), playerPieces.get(p.getName()) + 1);
      }
      for (String p : endGamePieces) {
        playerPieces.putIfAbsent(p, 0);
        playerPieces.put(p, playerPieces.get(p) - 1);
      }
      for (String key : playerPieces.keySet()) {
        if (playerPieces.get(key) < 0) {
          throw new InvalidEndGameConfigException(mappings.getString(INVALID_ENDCONDITION));
        }
      }
    }
  }

  private List<String> convertJSONArrayOfStrings(JSONArray jsonArray) {
    List<String> ret = new ArrayList<>();
    for (int i = 0; i < jsonArray.length(); i++) {
      ret.add(jsonArray.getString(i));
    }
    return ret;
  }

}
