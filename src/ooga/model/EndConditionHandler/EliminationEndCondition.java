package ooga.model.EndConditionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ooga.model.Board.GameState;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

public class EliminationEndCondition implements EndConditionInterface {
  public static final String PIECE_TYPE = "pieceType";
  public static final String AMOUNT = "amount";
  public static final String UNDERSCORE = "_";
  public static final String PIECE_TEAM_TYPE_FORMAT = "%s_%s";

  private List<PieceInterface> previousTurnPieces;
  private Map<String, Integer> piecesToEliminate;
  private String winner;

  @Override
  public void setArgs(Map<String, List<String>> propertiesMap, List<PieceInterface> allpieces) {

    Set<String> teams = new HashSet<>();
    for(PieceInterface piece : allpieces) {
      teams.add(piece.getTeam());
    }
    previousTurnPieces = new ArrayList<>(allpieces);
    piecesToEliminate = new HashMap<>();
    Iterator<String> pieceIter = propertiesMap.get(PIECE_TYPE).iterator();
    Iterator<String> amountIter = propertiesMap.get((AMOUNT)).iterator();
    while(pieceIter.hasNext() && amountIter.hasNext()) {
      String pieceType = pieceIter.next();
      int amount = Integer.parseInt(amountIter.next());
      for (String team : teams){
        String key = team+"_"+pieceType;
        piecesToEliminate.putIfAbsent(key, amount);
      }
    }
  }

  @Override
  public GameState isGameOver(List<PlayerInterface> players) {
    List<PieceInterface> alivePieces = new ArrayList<>();
    for (PlayerInterface player : players){
      for (PieceInterface piece : player.getPieces()){
        alivePieces.add(piece);
      }
    }
    if (previousTurnPieces.size() == alivePieces.size()){
      return GameState.RUNNING;
    }
    findMissingPiece(alivePieces);
    return checkEndConditions();
  }

  @Override
  public String getWinner(){
    return winner;
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

  private GameState checkEndConditions() {
    HashMap<String, Integer> targetPiecesRemaining = getTargetPiecesRemaining();
    String loser = null;
    for (String team : targetPiecesRemaining.keySet()){
      if (targetPiecesRemaining.get(team) == 0){
        loser = team;
        break;
      }
    }
    if (loser != null){
      for (String team : targetPiecesRemaining.keySet()){
        if (!team.equals(loser)){
          winner = team;
          return GameState.CHECKMATE;
        }
      }
    }
    return GameState.RUNNING;
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
    return targetPiecesRemaining;
  }

  private void logMissing(PieceInterface missing) {
//    String key = missing.getTeam() + UNDERSCORE + missing.getName();
    String key = String.format(PIECE_TEAM_TYPE_FORMAT, missing.getTeam(), missing.getName());
    if (!piecesToEliminate.containsKey(key)){
      return;
    }
    piecesToEliminate.put(key,piecesToEliminate.get(key)-1);
  }


}
