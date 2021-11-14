package ooga.model;

import ooga.Location;

import java.util.List;
import java.util.Map;

public interface PlayerInterface {

   void removePiece(Location location);
   List<PieceInterface> getPieces();
   void addPiece(PieceInterface piece);
   String getTeam();

    Location getKingLocation();
    void movePiece(PieceInterface piece, Location end);
    PieceInterface getPiece(Location location);
}
