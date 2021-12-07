package ooga.model.Powerups;

import ooga.Location;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.controller.Config.PieceBuilder;
import ooga.model.*;

import java.io.FileNotFoundException;
import java.util.List;

public class PromotePowerup extends Powerup{

    public PromotePowerup(List<Location> powerupLocations, Location bounds) {
        super(powerupLocations, bounds);
    }

    /**
     * Promotes the piece at the powerup location to a Queen. Removes the existing piece and adds the new Queen to the player's pieces
     * and to allPieces
     * @param pieceInterface piece that is being moved
     * @param endLocation  end location of the piece being moved
     * @param currentPlayer current player who
     * @param allPieces list of all pieces intialized on the board
     * @throws FileNotFoundException ? - if the piece's json file is unable to be found
     * @throws InvalidPieceConfigException ? - if the piece's json file is not valid (ie. missing key)
     */
    @Override
    void execute(PieceInterface pieceInterface, Location endLocation, PlayerInterface currentPlayer, List<PieceInterface> allPieces) {
        if(pieceInterface.getName().equals(Board.PIECES.getString("KING"))){
            return;
        }
        PieceInterface additionalPiece = PieceBuilder.buildPiece(currentPlayer.getTeam(),Board.PIECES.getString("DefaultPromotionPiece"),endLocation,getBounds());
        pieceInterface.transform(additionalPiece);



    }


}
