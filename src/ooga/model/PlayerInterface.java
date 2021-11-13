package ooga.model;

import java.util.List;

public interface PlayerInterface {

   void removePiece(Piece piece);
   List<PieceInterface> getPieces();
   void addPiece(PieceInterface piece);
   String getTeam();
}
