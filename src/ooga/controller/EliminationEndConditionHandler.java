package ooga.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import ooga.model.Piece;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

public class EliminationEndConditionHandler {
  private Map<String, Integer> pieceEndAmounts;
  private List<String> pieceTypes;
  private Map<String, Integer> initialPieceAmounts;

  public void setArgs(Map<String, List<String>> propertiesMap, List<PlayerInterface> players) {
    pieceEndAmounts = new HashMap<>();
    Iterator<String> pieceIter = propertiesMap.get("pieceType").iterator();
    Iterator<String> amountIter = propertiesMap.get(("amount")).iterator();
    while(pieceIter.hasNext() && amountIter.hasNext()) {
      pieceEndAmounts.putIfAbsent(pieceIter.next(), Integer.parseInt(amountIter.next()));
    }
    /**
     * Set piece end amounts based on prop file
     */

    initialPieceAmounts = new HashMap<>();
    propertiesMap.get("pieceType").forEach(key -> initialPieceAmounts.putIfAbsent(key, 0));

    for(PieceInterface piece : players.get(0).getPieces()) {
      if(initialPieceAmounts.containsKey(piece.getName())) {
        initialPieceAmounts.put(piece.getName(), initialPieceAmounts.get(piece.getName()) + 1);
      }
    }
  }

  public boolean isGameOver(List<PlayerInterface> players) {
    Map<String, Integer> pieceAmountMap = new HashMap<>();
    Map<String, Integer> pieceRemovedMap = new HashMap<>();

    for(PlayerInterface player : players) {
      pieceTypes.forEach(pieceType -> {
        pieceAmountMap.putIfAbsent(pieceType, 0);
        pieceRemovedMap.putIfAbsent(pieceType, 0);}
      );
      setAmountsOfEachPieceOnBoard(pieceAmountMap, player);
      if (checkIfEndConditionsMet(pieceAmountMap)) {
        return true; // GAME OVER
      }
    }
    return false;
  }

  private boolean checkIfEndConditionsMet(Map<String, Integer> pieceAmountMap) {
    for(String pieceType : pieceAmountMap.keySet()) {
      int piecesRemoved = initialPieceAmounts.get(pieceType) - pieceAmountMap.get(pieceType);
      if (piecesRemoved >= pieceEndAmounts.get(pieceType)) {
        return true;
      }
    }
    return false;
  }

  private void setAmountsOfEachPieceOnBoard(Map<String, Integer> pieceAmountMap, PlayerInterface player) {
    for(PieceInterface piece : player.getPieces()) {
      if(pieceAmountMap.keySet().contains(piece.getName())) {
        pieceAmountMap.put(piece.getName(), pieceAmountMap.get(piece.getName()) + 1);
      }
    }
  }
}
