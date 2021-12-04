package ooga.model;

import ooga.Location;
import ooga.model.EndConditionHandler.EndConditionRunner;
import ooga.model.Moves.Move;
import ooga.model.Powerups.PowerupInterface;
import ooga.model.Powerups.TimerPowerup;

import java.util.ArrayList;
import java.util.List;

public class GameBoard extends Board {
    private static final int ROWS = 8;
    private static final int COLS = 8;
    private static final int LAST_ROW = ROWS - 1;
    private static final int FIRST_ROW = 0;
    private static final String QUEEN = "Q";
    private static final String KING = "K";
    private static final String PAWN = "P";

    private EndConditionRunner endCondition;
    private int turnCount;



    public GameBoard(List<PlayerInterface> players) {
        super(players);
        endCondition = new EndConditionRunner();
        turnCount = 0;
        for (PieceInterface piece : allPieces) {
            piece.updateMoves(allPieces);
        }
        updateLegalMoves();


    }

    @Override
    protected Move getMove(Location end, PieceInterface piece) {
        return piece.getMove(end);
    }

    @Override
    protected void updateGameRules() {

        turnCount++;
        toggleTimers();
    }

    /**
     * this method sets the end conditions of the board
     *
     * @param endCondition
     */
    public void setEndCondition(EndConditionRunner endCondition) {
        this.endCondition = endCondition;
    }

    @Override
    public boolean canMovePiece(Location location) {
        String turn = findPlayerTurn(turnCount).getTeam();
        for (PieceInterface piece : allPieces) {
            if (piece.getTeam().equals(turn) && piece.getLocation().equals(location)) {
                return true;
            }
        }
        return false;
    }

    /**
     * return a list of all legal moves for a piece at a location
     *
     * @param location
     * @return
     */
    @Override
    public List<Location> getLegalMoves(Location location) {
        for (PieceInterface piece : allPieces) {
            if (piece.getLocation().equals(location)) {
                return piece.getEndLocations();
            }
        }
        return null;
    }

    /**
     * pause current player timer, add increment, start next player time
     */
    private void toggleTimers() {
        PlayerInterface prevPlayer = findPlayerTurn(turnCount - 1);
        PlayerInterface currPlayer = findPlayerTurn(turnCount);
        prevPlayer.toggleTimer();
        prevPlayer.incrementTimeUserInterface();
        currPlayer.toggleTimer();
    }

    private PlayerInterface findPlayerTurn(int turn) {
        currentPlayer = players.get((turn) % players.size());
        return currentPlayer;
    }


    @Override
    public GameState checkGameState() {
        return endCondition.satisfiedEndCondition(players);
    }
}
