package ooga.model;

import ooga.Location;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface PieceInterface {
//    String getType();
    String getTeam();
//    List<Location> getMoves(Location location);
    List<Vector> getMoveVectors();
    List<Vector> getTakeVectors();
//    void getValue();
    boolean isLimited();
    int getScore();
    String getName();
    Location getLocation();
    void moveTo(Location location);
    void tryMove(Location location);
    int getUniqueId();
    void setEliminated(boolean state);
    boolean getEliminatedState();
    boolean getEndState() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
