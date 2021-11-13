package ooga.model;

import ooga.model.Piece.MoveVector;

public interface PieceInterface {
//    String getType();
    String getTeam();
//    List<Location> getMoves(Location location);
    MoveVector getMoves();
//    void getValue();
    boolean isLimited();

    int getScore();

    String getPieceName();

}
