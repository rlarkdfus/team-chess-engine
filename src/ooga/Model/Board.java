package ooga.Model;

public interface Board {
    int getWidth();
    int getHeight();
    Piece getPiece();
    void clear();
}
