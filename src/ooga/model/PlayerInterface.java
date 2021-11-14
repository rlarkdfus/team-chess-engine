package ooga.model;

import ooga.Location;

import java.util.List;

public interface PlayerInterface {

   void removePiece(Piece piece);
   List<PieceInterface> getPieces();
   void addPiece(PieceInterface piece);
   String getTeam();

    Location getKingLocation();
    void setInCheck(boolean inCheck);

}
