package ooga.model.Powerups;

import ooga.Location;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

import java.util.List;

public class TimerPowerup extends Powerup{
    public TimerPowerup(List<Location> powerupLocations) {
        super(powerupLocations);
    }

    @Override
    void execute(PieceInterface pieceInterface, Location endLocation, PlayerInterface currentPlayer, List<PieceInterface> allPieces) {
        currentPlayer.incrementTime(1000);
    }
}
