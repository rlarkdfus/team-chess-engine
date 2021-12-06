package ooga.model;

import ooga.Location;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.model.EndConditionHandler.EndConditionRunner;
import ooga.model.Moves.Move;
import ooga.model.Powerups.PowerupInterface;

import java.io.FileNotFoundException;
import java.util.List;

public class GameBoard extends Board {

    private EndConditionRunner endCondition;
    private int turnCount;

    public GameBoard(List<PlayerInterface> players, EndConditionRunner endCondition, List<PowerupInterface> powerups) {
        super(players);
        this.endCondition = endCondition;
        this.powerupInterfaceList = powerups;
        turnCount = 0;
        for (PieceInterface piece : pieces) {
            piece.updateMoves(pieces);
        }
        updateLegalMoves();


    }

    @Override
    protected Move getMove(Location end, PieceInterface piece) {
        return piece.getMove(end);
    }

    @Override
    protected void updateGameRules(PieceInterface piece) {
        for(PowerupInterface powerupInterface: powerupInterfaceList){
            powerupInterface.checkPowerUp(piece, piece.getLocation(), currentPlayer, pieces);
        }
        turnCount++;
        toggleTimers();
    }

    @Override
    public boolean canMovePiece(Location location) {
        String turn = findPlayerTurn(turnCount).getTeam();
        for (PieceInterface piece : pieces) {
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
        for (PieceInterface piece : pieces) {
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
        currentPlayer = players.get((turn+1) % players.size());
        return players.get((turn) % players.size());
    }


    @Override
    public GameState checkGameState() {
        return endCondition.satisfiedEndCondition(players);
    }
}
