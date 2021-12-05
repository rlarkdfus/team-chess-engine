package ooga.model;

import java.util.List;
import java.util.Map;

import ooga.Location;
import ooga.model.Moves.Move;

public interface PieceInterface {
//    String getType();
    String getTeam();
//    List<Location> getMoves(Location location);
//    void getValue();
    int getScore();
    String getName();
    Location getLocation();
    void moveTo(Location location);
    void tryMove(Location location);
//    int getUniqueId();
//    void setEliminated(boolean state);
    List<Location> getEndLocations();
    boolean hasMoved();
    boolean isFirstMove();
    void transform(PieceInterface newPiece);
//    List<Location> getAllEndLocations();
    Move getMove(Location end);
    void updateMoves(List<PieceInterface> pieces);
    List<Move> getMoves();
    boolean isSameTeam(PieceInterface piece);
}
