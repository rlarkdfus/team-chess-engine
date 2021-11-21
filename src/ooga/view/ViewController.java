package ooga.view;

import javafx.scene.paint.Color;

/**
 * Handles changes in the view that do not involve the model
 */
public class ViewController {
    ViewInterface view;

    public ViewController() {
    }

    public void setView(View view) {
        this.view = view;
    }

    public void handleChangeBoardColor(Color color1, Color color2) {
        view.changeBoardColor(color1, color2);
    }

    public void handleChangePieceStyle(String style) {
        view.changePieceStyle(style);
    }

    public void handleNewGame() {
        view.resetBoard();
    }

    public void handleChangeTurnMinutes(Number time) {
        System.out.println(time);
    }

    public void handleChangeTimeIncrement(Number increment) {
        System.out.println(increment);
    }
}
