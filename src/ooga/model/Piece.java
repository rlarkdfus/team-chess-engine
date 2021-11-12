package ooga.model;

import ooga.Location;

import java.util.List;

public interface Piece {
    int getX();
    int getY();
    String getType();
    int getTeam();
    List<Location> getMoves();
    void getLimited();
    void getValue();
}
