package ooga.model.EndConditionHandler;

import static java.lang.Integer.parseInt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import ooga.controller.InvalidEndGameConfigException;
import ooga.model.GameState;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

public class LocationEndCondition implements EndConditionInterface{
  private Map<ooga.Location, String> targetLocations;
  private Map<String, Integer> minPieceAmounts;
  private Map<String, Integer> currTeamLocations;
  private Set<String> teams;
  private ResourceBundle resourceBundle;
  private String winner;
  public LocationEndCondition(){
    targetLocations = new HashMap<>();
    minPieceAmounts = new HashMap<>();
    teams = new HashSet<>();
    resourceBundle = ResourceBundle.getBundle("JSONMappings");
  }

  @Override
  public GameState isSatisfied(List<PlayerInterface> players) {
    currTeamLocations = new HashMap();
    List<PieceInterface> alivePieces = getAlivePieces(players);

    if (notEnoughPieces(alivePieces)){
      return null; //FIXME
    }
    boolean foundLocation;
    for (ooga.Location l : targetLocations.keySet()){
      foundLocation = false;
      for (PieceInterface p : alivePieces){
        if (p.getLocation().equals(l) && p.getName().equals(targetLocations.get(l))){
          currTeamLocations.putIfAbsent(p.getTeam(),0);
          currTeamLocations.put(p.getTeam(), currTeamLocations.get(p.getTeam())+1);
          foundLocation = true;
          break;
        }
      }
      if (!foundLocation){
        return null;
      }
    }
    for (String team : currTeamLocations.keySet()){
      if (currTeamLocations.get(team).equals(targetLocations.size())){
        winner = team;
        return null; //FIXME
      }
    }
    return null;
  }

  private boolean notEnoughPieces(List<PieceInterface> alivePieces) {
    String loser = null;
    Map<String, Integer> currentAmounts = new HashMap<>();
    for (PieceInterface alive : alivePieces){
      String team = alive.getTeam();
      String type = alive.getName();
      String key = team + "_" + type;
      currentAmounts.putIfAbsent(key, 0);
      currentAmounts.put(key,currentAmounts.get(key)+1);
    }

    for (String piece : currentAmounts.keySet()){
      String pieceType = piece.split("_")[1];
      if (minPieceAmounts.containsKey(pieceType)){
        if (currentAmounts.get(piece) < minPieceAmounts.get(pieceType)){
          System.out.println("here");
          loser = piece.split("_")[0];
          break;
        }
      }
    }
    if (loser != null){
      for (String team : teams){
        if (!team.equals(loser)){
          winner = team;
          return true;
        }
      }
    }
    return false;
  }

  private List<PieceInterface> getAlivePieces(List<PlayerInterface> players) {
    List<PieceInterface> alivePieces = new ArrayList<>();
    for (PlayerInterface player : players){
      for (PieceInterface piece : player.getPieces()){
        alivePieces.add(piece);
      }
    }
    return alivePieces;
  }

  public LocationEndCondition(Map<String, List<String>> properties, List<PieceInterface> allpieces)
      throws InvalidEndGameConfigException {
    String[] keys = resourceBundle.getString("LocationRuleKeys").split(resourceBundle.getString("jsonDelimiter"));
    List<String> pieces = properties.get(keys[0]);
    List<String> locations = properties.get(keys[1]);
    if (pieces.size() != locations.size()){throw new InvalidEndGameConfigException("missing location for pieces");}
    buildTargetLocations(pieces, locations);
    buildMinPieceAmounts();
    buildTeams(allpieces);
  }

  private void buildTeams(List<PieceInterface> allpieces) {
    for (PieceInterface p : allpieces){
      teams.add(p.getTeam());
    }
  }

  private void buildMinPieceAmounts() {
    for (ooga.Location l : targetLocations.keySet()) {
      String pieceType = targetLocations.get(l);
      minPieceAmounts.putIfAbsent(pieceType, 0);
      minPieceAmounts.put(pieceType, minPieceAmounts.get(pieceType)+1);
    }
  }

  private void buildTargetLocations(List<String> pieces, List<String> locations) {
    for (int i =0; i < pieces.size();i++){
      String piece = pieces.get(i);
      String[] rowColValues = locations.get(i).split(resourceBundle.getString("jsonDelimiter"));
      ooga.Location location = new ooga.Location(parseInt(rowColValues[0]),parseInt(rowColValues[1]));
      targetLocations.put(location,piece);
    }
  }
}
