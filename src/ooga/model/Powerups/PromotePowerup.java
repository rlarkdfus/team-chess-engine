package ooga.model.Powerups;

import ooga.Location;
import ooga.model.Board;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

import java.util.List;

public class PromotePowerup extends Powerup{

    public PromotePowerup(List<Location> powerupLocations) {
        super(powerupLocations);
    }
//FIXME: add promotionLogic with piece builder;
    @Override
    void execute(PieceInterface pieceInterface, Location endLocation, PlayerInterface currentPlayer, List<PieceInterface> allPieces) {
        if(pieceInterface.getName()!= Board.KING){
        }
    }


}
