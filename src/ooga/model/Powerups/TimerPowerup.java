package ooga.model.Powerups;

import java.util.List;
import ooga.Location;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

public class TimerPowerup extends Powerup{
    private static int MINUTE = 60;
    public TimerPowerup(List<Location> powerupLocations, Location bounds) {
        super(powerupLocations, bounds);
    }


    /**
     * Adds 60 seconds of time to the player's timer if they move a piece on a timer powerup square
     * @param pieceInterface piece that is being moved
     * @param endLocation  end location of the piece being moved
     * @param currentPlayer current player who
     * @param allPieces list of all pieces intialized on the board
     */

    @Override
    void execute(PieceInterface pieceInterface, Location endLocation, PlayerInterface currentPlayer, List<PieceInterface> allPieces) {
        currentPlayer.incrementTime(MINUTE);
    }
}
