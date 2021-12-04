package ooga.model.EndConditionHandler;

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
  public static final String PIECE_TYPE = "pieceType";
  public static final String AMOUNT = "amount";
  public static final String UNDERSCORE = "_";
  public static final String PIECE_TEAM_TYPE_FORMAT = "%s_%s";
  private final String NO_WINNER = "noWinner";

  private List<PieceInterface> previousTurnPieces;
  private Map<String, Integer> piecesToEliminate;
  private String winner;

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
    if(eliminatedAllTargets()){
      return null; //FIXME
    }
    return null;
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

  private boolean eliminatedAllTargets() {
    HashMap<String, Integer> targetPiecesRemaining = getTargetPiecesRemaining();
    String loser = NO_WINNER;
    for (String team : targetPiecesRemaining.keySet()){
      if (targetPiecesRemaining.get(team) <= 0){
        loser = team;
        break;
      }
    }
    if (loser != NO_WINNER){
      for (String team : targetPiecesRemaining.keySet()){
        if (!team.equals(loser)){
          winner = team;
          return true;
        }
      }
    }
    return false;
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
    System.out.println(targetPiecesRemaining);
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
