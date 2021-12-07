package ooga.view.ui.playerStatsUI;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import ooga.controller.GameControllerInterface;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

public class PlayerStatsUI extends GridPane implements UIInterface {
    public static final int WHITE_INDEX = 0;
    public static final int BLACK_INDEX = 1;

    private ViewUtility viewUtility;
    private GameControllerInterface controller;

    private Label whiteNameScoreLabel;
    private Label blackNameScoreLabel;

    private Label whiteScoreDisplay;
    private Label blackScoreDisplay;

    private Label whiteWinsLabel;
    private Label blackWinsLabel;

    public PlayerStatsUI(GameControllerInterface controller) {
        this.controller = controller;
        this.viewUtility = new ViewUtility();
        this.getStyleClass().add("GameSettingsUI");
        makeScoreDisplayLabels();
        createUI();
    }

    private void makeScoreDisplayLabels() {
        whiteNameScoreLabel = viewUtility.makeLabel("white_score_label");
        blackNameScoreLabel = viewUtility.makeLabel("black_score_label");
        whiteScoreDisplay = viewUtility.makeLabel("white_score_display");
        blackScoreDisplay = viewUtility.makeLabel("black_score_display");
        whiteWinsLabel = viewUtility.makeLabel("white_wins_label");
        blackWinsLabel = viewUtility.makeLabel("black_wins_label");

    }

    @Override
    public void createUI() {
        this.add(blackNameScoreLabel, 0, 0);
        this.add(blackScoreDisplay, 0, 1);
        this.add(blackWinsLabel, 0, 2);
        this.add(viewUtility.makeLabel("black_time_label"), 0, 3);
        this.add(viewUtility.makeText("black_timer_display", controller.getTimeLeft(BLACK_INDEX)), 0, 4);
        this.add(viewUtility.makeButton("download_game", e -> controller.downloadGame(viewUtility.saveJSONPath())), 0, 5);
        this.add(viewUtility.makeLabel("white_time_label"), 0, 6);
        this.add(viewUtility.makeText("white_timer_display", controller.getTimeLeft(WHITE_INDEX)), 0, 7);
        this.add(whiteWinsLabel, 0, 8);
        this.add(whiteNameScoreLabel, 0, 9);
        this.add(whiteScoreDisplay, 0, 10);
    }

    public void initializePlayerNames(String whiteUsername, String blackUsername) {
        whiteNameScoreLabel.setText(String.format("%s score", whiteUsername));
        blackNameScoreLabel.setText(String.format("%s score", blackUsername));
    }

    public void initializePlayerWins(int whiteWins, int blackWins) {
        whiteWinsLabel.setText(String.format("Wins: %d", whiteWins));
        blackWinsLabel.setText(String.format("Wins: %d", blackWins));
    }

    public void updateUI(int whiteScore, int blackScore) {
        whiteScoreDisplay.setText(String.valueOf(whiteScore));
        blackScoreDisplay.setText(String.valueOf(blackScore));
    }
}
