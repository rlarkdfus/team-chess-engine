package ooga.model.EndConditionHandler;

import ooga.model.GameState;
import ooga.model.Moves.MoveUtility;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

import java.util.List;
import java.util.Map;

public class PuzzleEndCondition extends CheckmateEndCondition implements EndConditionInterface {
    public PuzzleEndCondition(Map<String, List<String>> properties, List<PieceInterface> allpieces) {
        super(properties, allpieces);
    }

    @Override
    public GameState isSatisfied(List<PlayerInterface> players) {
        List<PieceInterface> pieces = getAllPieces(players);
        return MoveUtility.inCheck("b", pieces) && getTotalLegalMoves(players.get(1)) == 0 ? GameState.WHITE : GameState.BLACK;
    }
}
