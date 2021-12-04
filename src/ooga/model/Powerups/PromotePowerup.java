package ooga.model.Powerups;

import ooga.Location;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.controller.Config.PieceBuilder;
import ooga.model.Board;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

import java.io.FileNotFoundException;
import java.util.List;

public class PromotePowerup extends Powerup{
    private static String[] availablePieces = new String[]{"Q,R,B,K"};


    public PromotePowerup(List<Location> powerupLocations) {
        super(powerupLocations);
    }
//FIXME: add promotionLogic with piece builder;
    @Override
    void execute(PieceInterface pieceInterface, Location endLocation, PlayerInterface currentPlayer, List<PieceInterface> allPieces) throws FileNotFoundException, InvalidPieceConfigException {
        if(pieceInterface.getName().equals("K")){
            return;
        }
//        int randomPieceIndex = (int) (Math.random()*availablePieces.length);
//        String randomPieceName = availablePieces[randomPieceIndex-1];
        PieceInterface additionalPiece = null;
        try {
            additionalPiece = PieceBuilder.buildPiece(currentPlayer.getTeam(), "Q",endLocation);
        } catch (InvalidPieceConfigException e) {
            e.printStackTrace();
        }
        currentPlayer.addPiece(additionalPiece);
        allPieces.add(additionalPiece);
        currentPlayer.removePiece(pieceInterface);
        allPieces.remove(pieceInterface);



    }


}
