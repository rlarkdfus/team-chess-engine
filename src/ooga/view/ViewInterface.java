package ooga.view;

import ooga.Turn;

public interface ViewInterface {
    /**
     * Updates position of pieces on the board
     */
    void updateDisplay(Turn turn);

    void initializeDisplay();
}
