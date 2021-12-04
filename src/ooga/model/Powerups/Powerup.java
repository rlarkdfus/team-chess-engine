package ooga.model.Powerups;

import ooga.Location;
import ooga.controller.InvalidPieceConfigException;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

import java.io.FileNotFoundException;
import java.util.List;

public abstract class Powerup implements PowerupInterface{
    protected List<Location> powerupLocations;

    public Powerup(List<Location> powerupLocations) {
        this.powerupLocations = powerupLocations;
    }

    @Override
    public void checkPowerUp(PieceInterface pieceInterface, Location endLocation, PlayerInterface currentPlayer, List<PieceInterface> allPieces) throws FileNotFoundException, InvalidPieceConfigException {
        if(hitPowerup(endLocation)){
            execute(pieceInterface,endLocation, currentPlayer, allPieces);
            removeUsedPowerup(endLocation);
        }
    }

    private boolean hitPowerup(Location targetLocation){
        for(Location powerupLocation: powerupLocations){
            if(powerupLocation.equals(targetLocation)){
                return true;
            }
        }
        return false;
    }
//FIXME: need to test is removed actually removes the powerup.
    private void removeUsedPowerup(Location targetLocation) {
        System.out.println(powerupLocations.remove(targetLocation));
    }

     abstract void execute(PieceInterface pieceInterface, Location endLocation, PlayerInterface currentPlayer, List<PieceInterface> allPieces) throws FileNotFoundException, InvalidPieceConfigException;

}
