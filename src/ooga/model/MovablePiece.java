package ooga.model;

import java.util.List;

public class MovablePiece {
  private List<List<Integer>> myMoves;
  private List<List<Integer>> myTakeMoves;
  private List<List<Integer>> myInitialMoves;
  public MovablePiece (List<List<Integer>> moves, List<List<Integer>> takeMoves, List<List<Integer>> initialMoves) {
    myMoves = moves;
    myTakeMoves = takeMoves;
    myInitialMoves = initialMoves;
  }

}
