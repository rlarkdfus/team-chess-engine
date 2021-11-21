package ooga.model;

import ooga.Location;
import ooga.model.Moves.Move;

import java.util.List;

public interface PieceInterface {
//    String getType();
    String getTeam();
//    List<Location> getMoves(Location location);
//    void getValue();
    boolean isLimited();
    int getScore();
    String getName();
    Location getLocation();
    void moveTo(Location location);
    void tryMove(Location location);
    List<Location> getEndLocations();
    boolean hasMoved();
    boolean isFirstMove();
    Piece copy();
    List<Location> getAllEndLocations();
    Move getMove(Location end);
    void updateMoves(List<PieceInterface> pieces);
}
