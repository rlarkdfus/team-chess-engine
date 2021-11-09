package ooga.Model;

import java.util.List;

public interface Piece {
    void getX();
    void getY();
    void getType();
    List<Location> getMoves();
    void getLimited();
    void getValue();
}
