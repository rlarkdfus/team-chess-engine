package ooga.model;

import static java.lang.Integer.parseInt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.Location;
import ooga.controller.InvalidGameConfigException;

public class LocationEndConditionHandler {
  private Map<Location, String> targetLocations;
  private ResourceBundle resourceBundle;
  public LocationEndConditionHandler(){
    targetLocations = new HashMap<>();
    resourceBundle = ResourceBundle.getBundle("JSONMappings");
  }
  public boolean isGameOver(List<PieceInterface> alivePieces){
    boolean foundLocation;
    for (Location l : targetLocations.keySet()){
      foundLocation = false;
      for (PieceInterface p : alivePieces){
        if (p.getLocation().equals(l) && p.getName().equals(targetLocations.get(l))){
          foundLocation = true;
          break;
        }
      }
      if (!foundLocation){
        return false;
      }
    }
    return true;
  }
  public void setArgs(Map<String, List<String>> properties, List<PlayerInterface> players)
      throws InvalidGameConfigException {
    String[] keys = resourceBundle.getString("eliminationRuleKeys").split(resourceBundle.getString("jsonDelimiter"));
    List<String> pieces = properties.get(keys[0]);
    List<String> locations = properties.get(keys[1]);
    if (pieces.size() != locations.size()){throw new InvalidGameConfigException();}
    for (int i =0; i < pieces.size();i++){
      String piece = pieces.get(i);
      String[] rowColValues = locations.get(i).split(resourceBundle.getString("jsonDelimiter"));
      Location location = new Location(parseInt(rowColValues[0]),parseInt(rowColValues[1]));
      targetLocations.put(location,piece);
    }
  }
}
