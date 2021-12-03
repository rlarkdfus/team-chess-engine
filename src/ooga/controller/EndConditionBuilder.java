package ooga.controller;

import static ooga.controller.BoardBuilder.PROPERTIES_FILE;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.model.EndConditionHandler.EndConditionInterface;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;
import org.json.JSONArray;
import org.json.JSONObject;

public class EndConditionBuilder {
  JsonParser jsonParser;
  ResourceBundle mappings;
  List<PlayerInterface> playerList;
  public EndConditionBuilder(JsonParser jsonParser){
    this.jsonParser = jsonParser;
    mappings = ResourceBundle.getBundle(PROPERTIES_FILE);
  }
  public EndConditionInterface buildEndConditionHandler(String ruleJsonFile, List<PlayerInterface> playerList)
      throws FileNotFoundException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, InvalidGameConfigException, InvalidEndGameConfigException {
    EndConditionInterface endCondition;
    this.playerList = playerList;

    JSONObject rules = jsonParser.loadFile(new File(ruleJsonFile));
    String type = rules.getString(mappings.getString("gameType"));
    String[] keys = mappings.getString(type+"RuleKeys").split(mappings.getString("jsonDelimiter"));
    Map<String, List<String>> endConditionProperties = new HashMap<>();
    for (String key : keys){
      endConditionProperties.put(key, convertJSONArrayOfStrings(rules.getJSONArray(key)));
    }
    Class<?> clazz = Class.forName("ooga.model.EndConditionHandler." + type + "EndCondition");
    endCondition = (EndConditionInterface) clazz.getDeclaredConstructor().newInstance();
    List<PieceInterface> initialPieces = new ArrayList<>();
    for (PlayerInterface p : playerList){
      initialPieces.addAll(p.getPieces());
    }
    endCondition.setArgs(endConditionProperties, initialPieces);
    checkValidEndCondition(endConditionProperties);
    return endCondition;
  }

  private void checkValidEndCondition(Map<String,List<String>> endConditionProperties)
      throws InvalidEndGameConfigException {
    String pieceKey = mappings.getString("pieceType");
    List<String> endGamePieces = endConditionProperties.get(pieceKey);
    for (PlayerInterface player : playerList){
      HashMap<String, Integer> playerPieces = new HashMap<>();
      for (PieceInterface p : player.getPieces()){
        playerPieces.putIfAbsent(p.getName(),0);
        playerPieces.put(p.getName(),playerPieces.get(p.getName())+1);
      }
      for (String p : endGamePieces){
        playerPieces.put(p,playerPieces.get(p)-1);
      }
      for (String key : playerPieces.keySet()){
        if (playerPieces.get(key) < 0){
          throw new InvalidEndGameConfigException("not enough pieces to be eliminated");
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
