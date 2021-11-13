package ooga.view.ui.gameInfoUI;

import javafx.scene.layout.GridPane;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

public class GameInfoUI extends GridPane implements UIInterface {

    public GameInfoUI() {
        this.getStyleClass().add("GameInfoUI");
        createUI();
    }

    @Override
    public void createUI() {
        this.add(ViewUtility.makeLabel("time_control"), 0, 0);
        this.add(ViewUtility.makeLabel("variation"), 0, 1);
        this.add(ViewUtility.makeLabel("chess"), 1, 1);

    }
}
