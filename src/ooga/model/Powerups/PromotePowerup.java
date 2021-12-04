package ooga.model.Powerups;

import ooga.Location;
import ooga.controller.InvalidPieceConfigException;
import ooga.controller.PieceBuilder;
import ooga.model.Board;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PromotePowerup extends Powerup{
    private static String[] availablePieces = new String[]{"Q,R,B,K"};


    public PromotePowerup(List<Location> powerupLocations) {
        super(powerupLocations);
    }
//FIXME: add promotionLogic with piece builder;
    @Override
    void execute(PieceInterface pieceInterface, Location endLocation, PlayerInterface currentPlayer, List<PieceInterface> allPieces) throws FileNotFoundException, InvalidPieceConfigException {
        if(pieceInterface.getName().equals(Board.KING)){
            return;
        }
//        int randomPieceIndex = (int) (Math.random()*availablePieces.length);
//        String randomPieceName = availablePieces[randomPieceIndex-1];
        PieceInterface additionalPiece = PieceBuilder.buildPiece(currentPlayer.getTeam(),Board.QUEEN,endLocation);
        currentPlayer.addPiece(additionalPiece);
        allPieces.add(additionalPiece);
        currentPlayer.removePiece(pieceInterface);
        allPieces.remove(pieceInterface);



    }


}
