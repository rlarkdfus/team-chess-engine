package ooga.model.Powerups;

import ooga.Location;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

import java.util.List;

public abstract class Powerup implements PowerupInterface {
    private List<Location> powerupLocations;
    private Location bounds;

    /**
     * Initializes powerup class
     *
     * @param powerupLocations list of locations for powerup squares
     * @param bounds           location object representing the bounds of the board
     */
    public Powerup(List<Location> powerupLocations, Location bounds) {
        this.powerupLocations = powerupLocations;
        this.bounds = bounds;
    }

    /**
     *
     * @param pieceInterface piece that is being moved
     * @param endLocation  end location of the piece being moved
     * @param currentPlayer current player who
     * @param allPieces list of all pieces initialized on the board
     */
    @Override
    public void checkPowerUp(PieceInterface pieceInterface, Location endLocation, PlayerInterface currentPlayer, List<PieceInterface> allPieces) {
        if (hitPowerup(endLocation)) {
            execute(pieceInterface, endLocation, currentPlayer, allPieces);
            for (Location location : powerupLocations) {
                if (location.equals(endLocation)) {
                    powerupLocations.remove(location);
                    break;
                }
            }
        }
    }

    /**
     * Checks if a powerup square was hit
     *
     * @param targetLocation, the location to checked
     * @return true if the target location is within the list of powerup squares
     */
    private boolean hitPowerup(Location targetLocation) {
        for (Location powerupLocation : powerupLocations) {
            if (powerupLocation.equals(targetLocation)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Executes the powerup ability
     *
     * @param pieceInterface piece that is being moved
     * @param endLocation    end location of the piece being moved
     * @param currentPlayer  current player who
     * @param allPieces      list of all pieces intialized on the board
     */
    abstract void execute(PieceInterface pieceInterface, Location endLocation, PlayerInterface currentPlayer, List<PieceInterface> allPieces);

    protected Location getBounds() {
        return bounds;
    }

    @Override
    public List<Location> getPowerupLocations() {
        return powerupLocations;
    }
}
