package ooga.model.EndConditionHandler;

import ooga.model.GameState;
import ooga.model.Moves.MoveUtility;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

import java.util.List;
import java.util.Map;

public class PuzzleEndCondition extends CheckmateEndCondition implements EndConditionInterface {
    /**
     * extends from checkmate to hold all pieces
     * @param properties none required
     * @param allpieces list of all pieces
     */
    public PuzzleEndCondition(Map<String, List<String>> properties, List<PieceInterface> allpieces) {
        super(properties, allpieces);
    }

    /**
     * puzzle requires white to find the move that will check, or lose immediately
     * @param players all players
     * @return winner when a check is played
     */
    @Override
    public GameState isSatisfied(List<PlayerInterface> players) {
        return MoveUtility.inCheck("b", pieces) ? GameState.WHITE : GameState.BLACK;
    }
}
