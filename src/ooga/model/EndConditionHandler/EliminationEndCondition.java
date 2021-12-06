package ooga.model.EndConditionHandler;

import static ooga.controller.Config.BoardBuilder.PIECE_TYPE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ooga.model.GameState;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

public class EliminationEndCondition implements EndConditionInterface {
  public static final String UNDERSCORE = "_";
  public static final String PIECE_TEAM_TYPE_FORMAT = "%s_%s";

  private List<PieceInterface> previousTurnPieces;
  private Map<String, Integer> piecesToEliminate;

  /**
   * this builds the map of the pieces that needs to be eliminated
   * @param propertiesMap the pieces that need to be eliminated
   * @param allpieces all pieces, used to determine pieces eliminated
   */
  public EliminationEndCondition(Map<String, List<String>> propertiesMap, List<PieceInterface> allpieces) {
    Set<String> teams = new HashSet<>();
    previousTurnPieces = new ArrayList<>();
    for(PieceInterface piece : allpieces) {
      teams.add(piece.getTeam());
      previousTurnPieces.add(piece);
    }
    piecesToEliminate = new HashMap<>();
    Iterator<String> pieceIter = propertiesMap.get(PIECE_TYPE).iterator();
    while(pieceIter.hasNext()) {
      String pieceType = pieceIter.next();
      for (String team : teams){
        String key = team + "_" + pieceType;
        piecesToEliminate.putIfAbsent(key, 0);
        piecesToEliminate.put(key, piecesToEliminate.get(key)+1);
      }
    }
  }

  /**
   * elimination requires a certain number of pieces to be eliminated
   * @param players all players
   * @return the winner if conditions are met
   */
  @Override
  public GameState isSatisfied(List<PlayerInterface> players) {
    List<PieceInterface> alivePieces = new ArrayList<>();
    for (PlayerInterface player : players){
      for (PieceInterface piece : player.getPieces()){
        alivePieces.add(piece);
      }
    }
    if (previousTurnPieces.size() == alivePieces.size()){
      return null;
    }
    findMissingPiece(alivePieces);
    previousTurnPieces = alivePieces;
    return eliminatedAllTargets();
  }

  private void findMissingPiece(List<PieceInterface> alivePieces) {
    boolean found;
    for (PieceInterface p : previousTurnPieces){
      found = false;
      for (PieceInterface alive : alivePieces){
        if (p.equals(alive)){
          found = true;
          break;
        }
      }
      if (!found){
        logMissing(p);
      }
    }

  }

  private GameState eliminatedAllTargets() {
    HashMap<String, Integer> targetPiecesRemaining = getTargetPiecesRemaining();
    GameState winner = GameState.RUNNING;
    for (String team : targetPiecesRemaining.keySet()){
      if (targetPiecesRemaining.get(team) <= 0){
        winner = GameState.ENDED.getLoser(team);
        break;
      }
    }
    return (winner != GameState.RUNNING) ? winner : null;
  }

  private HashMap<String, Integer> getTargetPiecesRemaining() {
    HashMap<String, Integer> targetPiecesRemaining = new HashMap<>();
    for (String pieceString : piecesToEliminate.keySet()){
      String[] pieceStringInfo = pieceString.split(UNDERSCORE);
      String team = pieceStringInfo[0];
      int piecesLeft = piecesToEliminate.get(pieceString);
      targetPiecesRemaining.putIfAbsent(team, 0);
      targetPiecesRemaining.put(team, targetPiecesRemaining.get(team) + piecesLeft);
    }
//    System.out.println(targetPiecesRemaining);
    return targetPiecesRemaining;
  }

  private void logMissing(PieceInterface missing) {
    String key = String.format(PIECE_TEAM_TYPE_FORMAT, missing.getTeam(), missing.getName());
    if (!piecesToEliminate.containsKey(key)){
      return;
    }
    piecesToEliminate.put(key,piecesToEliminate.get(key)-1);
  }
}
