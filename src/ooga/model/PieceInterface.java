package ooga.model;

import ooga.Location;

import java.util.List;

public interface PieceInterface {
//    String getType();
    int getTeam();
//    List<Location> getMoves(Location location);
    Piece.MoveVector getMoves();
//    void getValue();
    boolean isLimited();
}
