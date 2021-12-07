package ooga.model.Powerups;

import ooga.Location;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

import java.io.FileNotFoundException;
import java.util.List;

public interface PowerupInterface {

    /**
     * Checks if a powerup is hit and executes the powerup ability if a piece lands on one of the powerup locations
     * @param pieceInterface piece that is being moved
     * @param endLocation  end location of the piece being moved
     * @param currentPlayer current player who
     * @param allPieces list of all pieces intialized on the board
     * @exception FileNotFoundException if file for transformed piece does not exist
     * @exception InvalidPieceConfigException if transformed piece does not exist
     */
    void checkPowerUp(PieceInterface pieceInterface, Location endLocation, PlayerInterface currentPlayer, List<PieceInterface> allPieces);

    /**
     *
     * @returns the list of powerup locaitons
     */
    List<Location> getPowerupLocations();

}
