package ooga.model.EndConditionHandler;

import ooga.controller.Config.InvalidEndGameConfigException;
import ooga.model.GameState;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * @authors purpose - the purpose of this file is to determine the location end condition which requires pieces
 * to be on certain squares in order to win
 * assumptions - it assumes that there are enough pieces to meet the condition as well as that the pieces
 * and players are valid
 * dependencies - it depends on PieceInterface, PlayerInterface, and GameState
 * usage - the end condition runner loops through all the end conditions and calls checkmate to see if
 * a checkmate is met
 */
public class LocationEndCondition implements EndConditionInterface {
  private final Map<ooga.Location, String> targetLocations;
  private final Map<String, Integer> minPieceAmounts;
  private Map<String, Integer> currTeamLocations;
  private final Set<String> teams;
  private final ResourceBundle resourceBundle;

  /**
   * this builds the map of piece locations
   *
   * @param properties map of locations of pieces that need to be in place to win
   * @param allpieces  all pieces
   * @throws InvalidEndGameConfigException if a win condition is not possible
   */
  public LocationEndCondition(Map<String, List<String>> properties, List<PieceInterface> allpieces)
          throws InvalidEndGameConfigException {
    targetLocations = new HashMap<>();
    minPieceAmounts = new HashMap<>();
    teams = new HashSet<>();
    resourceBundle = ResourceBundle.getBundle("JSONMappings");

    String[] keys = resourceBundle.getString("LocationRuleKeys").split(resourceBundle.getString("jsonDelimiter"));
    List<String> pieces = properties.get(keys[0]);
    List<String> locations = properties.get(keys[1]);
    if (pieces.size() != locations.size()) {
      throw new InvalidEndGameConfigException("missing location for pieces");
    }
    buildTargetLocations(pieces, locations);
    buildMinPieceAmounts();
    buildTeams(allpieces);
  }

  /**
   * location end condition is met if a player has enough pieces in the specified locations
   *
   * @param players all players
   * @return whether any winner has all their pieces in the right location
   */
  @Override
  public GameState isSatisfied(List<PlayerInterface> players) {
    currTeamLocations = new HashMap();
    List<PieceInterface> alivePieces = getAlivePieces(players);

    if (notEnoughPieces(alivePieces) != GameState.RUNNING) {
      return notEnoughPieces(alivePieces);
    }
    boolean foundLocation;
    for (ooga.Location l : targetLocations.keySet()) {
      foundLocation = false;
      for (PieceInterface p : alivePieces) {
        if (p.getLocation().equals(l) && p.getName().equals(targetLocations.get(l))) {
          currTeamLocations.putIfAbsent(p.getTeam(), 0);
          currTeamLocations.put(p.getTeam(), currTeamLocations.get(p.getTeam()) + 1);
          foundLocation = true;
          break;
        }
      }
      if (!foundLocation) {
        return null;
      }
    }
    for (String team : currTeamLocations.keySet()) {
      if (currTeamLocations.get(team).equals(targetLocations.size())) {
        return GameState.ENDED.getWinner(team);
      }
    }
    return null;
  }

  private GameState notEnoughPieces(List<PieceInterface> alivePieces) {
    Map<String, Integer> currentAmounts = new HashMap<>();
    for (PieceInterface alive : alivePieces) {
      String team = alive.getTeam();
      String type = alive.getName();
      String key = team + "_" + type;
      currentAmounts.putIfAbsent(key, 0);
      currentAmounts.put(key, currentAmounts.get(key) + 1);
    }

    GameState loser = GameState.RUNNING;
    for (String piece : currentAmounts.keySet()) {
      String pieceType = piece.split("_")[1];
      if (minPieceAmounts.containsKey(pieceType)) {
        if (currentAmounts.get(piece) < minPieceAmounts.get(pieceType)) {
          System.out.println("here");
          loser = GameState.ENDED.getLoser(piece.split("_")[0]);
          break;
        }
      }
    }
    return loser;
  }

  private List<PieceInterface> getAlivePieces(List<PlayerInterface> players) {
    List<PieceInterface> alivePieces = new ArrayList<>();
    for (PlayerInterface player : players) {
      for (PieceInterface piece : player.getPieces()) {
        alivePieces.add(piece);
      }
    }
    return alivePieces;
  }

  private void buildTeams(List<PieceInterface> allpieces) {
    for (PieceInterface p : allpieces) {
      teams.add(p.getTeam());
    }
  }

  private void buildMinPieceAmounts() {
    for (ooga.Location l : targetLocations.keySet()) {
      String pieceType = targetLocations.get(l);
      minPieceAmounts.putIfAbsent(pieceType, 0);
      minPieceAmounts.put(pieceType, minPieceAmounts.get(pieceType) + 1);
    }
  }

  private void buildTargetLocations(List<String> pieces, List<String> locations) {
    for (int i = 0; i < pieces.size(); i++) {
      String piece = pieces.get(i);
      String[] rowColValues = locations.get(i).split(resourceBundle.getString("jsonDelimiter"));
      ooga.Location location = new ooga.Location(parseInt(rowColValues[0]), parseInt(rowColValues[1]));
      targetLocations.put(location, piece);
    }
  }
}
