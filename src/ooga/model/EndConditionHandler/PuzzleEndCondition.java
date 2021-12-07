package ooga.model.EndConditionHandler;

import ooga.model.GameState;
import ooga.model.Moves.MoveUtility;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

import java.util.List;
import java.util.Map;

/**
 * @authors purpose - the purpose of the puzzle end condition is to force the player to find a mate in one move
 * assumptions - it assumes that there should be no draw and that the pieces and players are all valid
 * dependencies - it depends on PieceInterface, PlayerInterface, MoveUtility, and GameState
 * usage - the end condition runner loops through all the end conditions and calls checkmate to see if
 * a checkmate is met
 */
public class PuzzleEndCondition extends CheckmateEndCondition implements EndConditionInterface {
  /**
   * extends from checkmate to hold all pieces
   *
   * @param properties none required
   * @param allpieces  list of all pieces
   */
  public PuzzleEndCondition(Map<String, List<String>> properties, List<PieceInterface> allpieces) {
    super(properties, allpieces);
  }

  /**
   * puzzle requires white to find the move that will check, or lose immediately
   *
   * @param players all players
   * @return winner when a check is played
   */
  @Override
  public GameState isSatisfied(List<PlayerInterface> players) {
    return MoveUtility.inCheck("b", pieces) ? GameState.WHITE : GameState.BLACK;
  }
}
