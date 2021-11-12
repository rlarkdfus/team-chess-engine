package ooga.view.ui;

import javafx.scene.layout.GridPane;
import ooga.view.util.ViewUtility;

public class GameSettingsUI extends GridPane implements UIInterface {
    public GameSettingsUI() {
        createUI();
    }

    @Override
    public void createUI() {
        this.add(ViewUtility.makeLabel("black_time_label"), 0, 0);
        this.add(ViewUtility.makeText("black_timer_display"), 0, 1);
        this.add(ViewUtility.makeLabel("move_history_label"), 0, 2);
        this.add(ViewUtility.makeGridPane("move_history_gridpane"), 0, 3);
        this.add(ViewUtility.makeButton("download_game", e -> System.out.println("download game")), 0, 4);
        this.add(ViewUtility.makeLabel("white_time_label"), 0, 5);
        this.add(ViewUtility.makeText("white_timer_display"), 0, 6);
    }
}
