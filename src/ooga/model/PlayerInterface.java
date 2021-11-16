package ooga.model;

import ooga.Location;

import java.util.List;
import java.util.Map;

public interface PlayerInterface {

   void removePiece(Location location);
   List<PieceInterface> getPieces();
   void addPiece(PieceInterface piece);
   String getTeam();
    PieceInterface getKing();
    void movePiece(PieceInterface piece, Location end);
    void tryMove(PieceInterface piece, Location end);
    PieceInterface getPiece(Location location);

    List<Location> getLegalMoves(Location location);
    void setLegalMoves(PieceInterface piece, List<Location> legalMoves);
}
