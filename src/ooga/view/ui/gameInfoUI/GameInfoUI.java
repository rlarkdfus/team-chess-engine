package ooga.view.ui.gameInfoUI;

import javafx.scene.layout.GridPane;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

public class GameInfoUI extends GridPane implements UIInterface {

    private ViewUtility viewUtility;

    public GameInfoUI() {
        this.viewUtility = new ViewUtility();
        this.getStyleClass().add("GameInfoUI");
        createUI();
    }

    @Override
    public void createUI() {
        this.add(viewUtility.makeLabel("time_control"), 0, 0);
        this.add(viewUtility.makeLabel("variation"), 0, 1);
        this.add(viewUtility.makeLabel("chess"), 1, 1);
    }
}
