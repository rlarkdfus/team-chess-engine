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
}
