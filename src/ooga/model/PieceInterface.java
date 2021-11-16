package ooga.model;

import ooga.Location;

import java.util.List;

public interface PieceInterface {
//    String getType();
    String getTeam();
//    List<Location> getMoves(Location location);
    List<Vector> getMoves();
//    void getValue();
    boolean isLimited();
    int getScore();
    String getName();
    Location getLocation();
    void moveTo(Location location);
    void tryMove(Location location);
}
