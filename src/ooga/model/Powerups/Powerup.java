package ooga.model.Powerups;

import ooga.Location;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

import java.io.FileNotFoundException;
import java.util.List;

public abstract class Powerup implements PowerupInterface{

    protected List<Location> powerupLocations;

    /**
     * Initializes powerup class
     * @param powerupLocations list of locations for powerup squares
     */
    public Powerup(List<Location> powerupLocations) {
        this.powerupLocations = powerupLocations;
    }

    @Override
    public void checkPowerUp(PieceInterface pieceInterface, Location endLocation, PlayerInterface currentPlayer, List<PieceInterface> allPieces) {
        if(hitPowerup(endLocation)){
            execute(pieceInterface,endLocation, currentPlayer, allPieces);
//            removeUsedPowerup(endLocation);
        }
    }

    /**
     * Checks if a powerup square was hit
     * @param targetLocation, the location to checked
     * @return true if the target location is within the list of powerup squares
     */
    private boolean hitPowerup(Location targetLocation){
        for(Location powerupLocation: powerupLocations){
            if(powerupLocation.equals(targetLocation)){
                return true;
            }
        }
        return false;
    }

//////FIXME: need to test is removed actually removes the powerup.
//    private void removeUsedPowerup(Location targetLocation) {
//        powerupLocations.remove(targetLocation);
//    }


    /**
     * Executes the powerup ability
     * @param pieceInterface piece that is being moved
     * @param endLocation  end location of the piece being moved
     * @param currentPlayer current player who
     * @param allPieces list of all pieces intialized on the board
     */

     abstract void execute(PieceInterface pieceInterface, Location endLocation, PlayerInterface currentPlayer, List<PieceInterface> allPieces);

}
