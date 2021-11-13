package ooga.model;

import ooga.Location;

import java.util.List;

public interface PieceInterface {
//    String getType();
    String getTeam();
//    List<Location> getMoves(Location location);
    Piece.MoveVector getMoves();
//    void getValue();
    boolean isLimited();

    int getScore();

    String getPieceName();

}
