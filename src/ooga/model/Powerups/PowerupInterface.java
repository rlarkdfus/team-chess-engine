package ooga.model.Powerups;

import ooga.Location;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

import java.util.List;

public interface PowerupInterface {

     void checkPowerUp(PieceInterface pieceInterface, Location endLocation, PlayerInterface currentPlayer, List<PieceInterface> allPieces);
}
