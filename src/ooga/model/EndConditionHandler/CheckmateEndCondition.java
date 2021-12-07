package ooga.model.EndConditionHandler;

import ooga.model.GameState;
import ooga.model.Moves.MoveUtility;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

import java.util.List;
import java.util.Map;

/**
 * @authors purpose - this class handles checkmate end conditions, and returns the winner if a checkmate is found
 * assumptions - it assumes that all the pieces and players are valid
 * dependencies - it depends on PieceInterface, PlayerInterface, and GameState
 * usage - the end condition runner loops through all the end conditions and calls checkmate to see if
 * a checkmate is met
 */
public class CheckmateEndCondition implements EndConditionInterface {
  protected List<PieceInterface> pieces;

  /**
   * stores the list of all pieces
   *
   * @param properties none required for checkmate
   * @param allpieces  list of all pieces
   */
  public CheckmateEndCondition(Map<String, List<String>> properties, List<PieceInterface> allpieces) {
    this.pieces = allpieces;
  }

  /**
   * checkmate is satisfied when a player is in check and has no legal moves
   *
   * @param players all players
   * @return winning player if checkmate
   */
  @Override
  public GameState isSatisfied(List<PlayerInterface> players) {
    for (PlayerInterface player : players) {
      if (getTotalLegalMoves(player) == 0) {
        return MoveUtility.inCheck(player.getTeam(), pieces) ? GameState.ENDED.getLoser(player.getTeam()) : GameState.DRAW;
      }
    }
    return null;
  }

  /**
   * this gets the number of legal moves a player may make
   *
   * @param player the player in check
   * @return number of legal moves
   */
  private int getTotalLegalMoves(PlayerInterface player) {
    int totalLegalMoves = 0;
    for (PieceInterface piece : player.getPieces()) {
      totalLegalMoves += piece.getEndLocations().size();
    }
    return totalLegalMoves;
  }
}
