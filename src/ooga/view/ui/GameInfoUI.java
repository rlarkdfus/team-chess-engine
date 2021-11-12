package ooga.view.ui;

import javafx.scene.layout.GridPane;
import ooga.view.util.ViewUtility;

public class GameInfoUI extends GridPane implements UIInterface {

    public GameInfoUI() {
        createUI();
    }

    @Override
    public void createUI() {
        this.add(ViewUtility.makeLabel("time_control"), 0, 0);

        this.add(ViewUtility.makeLabel("variation"), 0, 1);
        this.add(ViewUtility.makeLabel("chess"), 1, 1);

    }
}
