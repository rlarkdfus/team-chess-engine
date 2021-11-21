package ooga.model;

import ooga.Location;

import java.util.List;
import java.util.Map;

public interface PlayerInterface {
    List<PieceInterface> getPieces();
    void addPiece(PieceInterface piece);
    String getTeam();
    PieceInterface getPiece(Location location);
    List<Location> getLegalMoves(Location location);
    void setLegalMoves(PieceInterface piece, List<Location> legalMoves);
}
