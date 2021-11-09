package ooga.View;

public interface BoardView {
    void updateBoardView();
    void initializeBoardView();
    void changeColors(Paint color1, Paint color2);
    /**
     *
     */
    void showLegalMoves();
}
