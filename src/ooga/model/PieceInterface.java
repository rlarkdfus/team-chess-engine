package ooga.model;

import ooga.model.Piece.MoveVectors;

public interface PieceInterface {
//    String getType();
    String getTeam();
//    List<Location> getMoves(Location location);
    MoveVectors getMoves();
//    void getValue();
    boolean isLimited();
}
