package ooga.view.ui.playerStatsUI;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import ooga.controller.Controller;
import ooga.view.ViewController;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

public class playerStatsUI extends GridPane implements UIInterface {
    public static final int WHITE_INDEX = 0;
    public static final int BLACK_INDEX = 1;

    Controller controller;
    ViewController viewController;
    ViewUtility viewUtility;

    private Label whiteScoreDisplay;
    private Label blackScoreDisplay;

    public playerStatsUI(Controller controller, ViewController viewController) {
        this.controller = controller;
        this.viewController = viewController;
        this.viewUtility = new ViewUtility();
        this.getStyleClass().add("GameSettingsUI");
        makeScoreDisplayLabels();
        createUI();
    }

    private void makeScoreDisplayLabels() {
        whiteScoreDisplay = viewUtility.makeLabel("white_score_display");
        blackScoreDisplay = viewUtility.makeLabel("black_score_display");
    }

    @Override
    public void createUI() {
        this.add(blackScoreDisplay, 0, 0);
        this.add(viewUtility.makeLabel("black_score_display"), 0, 1);
        this.add(viewUtility.makeLabel("black_time_label"), 0, 2);
        this.add(viewUtility.makeText("black_timer_display", controller.getTimeLeft(BLACK_INDEX)), 0, 3);
        this.add(viewUtility.makeLabel("move_history_label"), 0, 4);
        this.add(viewUtility.makeGridPane("move_history_gridpane"), 0, 5);
        this.add(viewUtility.makeButton("download_game", e -> controller.downloadGame(viewUtility.saveJSONPath())), 0, 6);
        this.add(viewUtility.makeLabel("white_time_label"), 0, 7);
        this.add(viewUtility.makeText("white_timer_display", controller.getTimeLeft(WHITE_INDEX)), 0, 8);
        this.add(viewUtility.makeLabel("white_score_label"), 0, 9);
        this.add(whiteScoreDisplay, 0, 10);
    }

    public void updateUI(int whiteScore, int blackScore) {
        whiteScoreDisplay.setText(String.valueOf(whiteScore));
        blackScoreDisplay.setText(String.valueOf(blackScore));
    }
}
