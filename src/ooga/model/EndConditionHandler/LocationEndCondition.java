package ooga.model.EndConditionHandler;

import static java.lang.Integer.parseInt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.controller.InvalidGameConfigException;
import ooga.model.PieceInterface;

public class LocationEndCondition implements EndConditionInterface{
  private Map<ooga.Location, String> targetLocations;
  private Map<String, Integer> teams;
  private ResourceBundle resourceBundle;
  private String winner;
  public LocationEndCondition(){
    targetLocations = new HashMap<>();
    resourceBundle = ResourceBundle.getBundle("JSONMappings");
    teams = new HashMap();
  }

  @Override
  public boolean isGameOver(List<PieceInterface> alivePieces){
    boolean foundLocation;
    for (ooga.Location l : targetLocations.keySet()){
      foundLocation = false;
      for (PieceInterface p : alivePieces){
        if (p.getLocation().equals(l) && p.getName().equals(targetLocations.get(l))){
          teams.putIfAbsent(p.getTeam(),0);
          teams.put(p.getTeam(),teams.get(p.getTeam())+1);
          foundLocation = true;
          break;
        }
      }
      if (!foundLocation){
        return false;
      }
    }
    for (String team : teams.keySet()){
      if (teams.get(team).equals(targetLocations.size())){
        winner = team;
        return true;
      }
    }
    return false;
  }

  public void setArgs(Map<String, List<String>> properties, List<PieceInterface> allpieces)
      throws InvalidGameConfigException {
    String[] keys = resourceBundle.getString("LocationRuleKeys").split(resourceBundle.getString("jsonDelimiter"));
    List<String> pieces = properties.get(keys[0]);
    List<String> locations = properties.get(keys[1]);
    if (pieces.size() != locations.size()){throw new InvalidGameConfigException();}
    for (int i =0; i < pieces.size();i++){
      String piece = pieces.get(i);
      String[] rowColValues = locations.get(i).split(resourceBundle.getString("jsonDelimiter"));
      ooga.Location location = new ooga.Location(parseInt(rowColValues[0]),parseInt(rowColValues[1]));
      targetLocations.put(location,piece);
    }
  }

  @Override
  public String getWinner() {
    return winner;
  }
}
