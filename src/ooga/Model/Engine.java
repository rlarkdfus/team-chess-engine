package ooga.Model;

public interface Engine {
    void initializeBoard();
    void updateBoard();
    void clearBoard();
    boolean gameFinished();
}
