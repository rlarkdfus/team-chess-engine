package ooga.model.Powerups;

import ooga.Location;
import ooga.controller.InvalidPieceConfigException;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;

import java.io.FileNotFoundException;
import java.util.List;

public interface PowerupInterface {

     void checkPowerUp(PieceInterface pieceInterface, Location endLocation, PlayerInterface currentPlayer, List<PieceInterface> allPieces) throws FileNotFoundException, InvalidPieceConfigException;
}
