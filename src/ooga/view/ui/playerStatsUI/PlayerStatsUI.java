package ooga.view.ui.playerStatsUI;

import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import ooga.controller.GameControllerInterface;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

import java.util.Map;
import java.util.ResourceBundle;

public class PlayerStatsUI extends GridPane implements UIInterface {
    public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("ooga/view/ui/playerStatsUI/PlayerStatsUI");

    public static final int WHITE_INDEX = Integer.parseInt(RESOURCE_BUNDLE.getString("white_index"));
    public static final int BLACK_INDEX = Integer.parseInt(RESOURCE_BUNDLE.getString("black_index"));
    
    private static final String WHITE_SCORE_LABEL = "white_score_label";
    private static final String BLACK_SCORE_LABEL = "black_score_label";
    private static final String WHITE_SCORE_DISPLAY = "white_score_display";
    private static final String BLACK_SCORE_DISPLAY = "black_score_display";

    private static final String WHITE_TIME_LABEL = "white_time_label";
    private static final String BLACK_TIME_LABEL = "black_time_label";

    private static final String WHITE_TIME_DISPLAY = "white_time_display";
    private static final String BLACK_TIME_DISPLAY = "black_time_display";
    private final Map<String, Integer> TEAM_MAP = Map.of(
            WHITE_TIME_DISPLAY, WHITE_INDEX,
            BLACK_TIME_DISPLAY, BLACK_INDEX
    );

    private static final String WHITE_WINS_LABEL = "white_wins_label";
    private static final String BLACK_WINS_LABEL = "black_wins_label";
    
    private ViewUtility viewUtility;
    private GameControllerInterface controller;

    private Label whiteNameScoreLabel;
    private Label blackNameScoreLabel;

    private Label whiteScoreDisplay;
    private Label blackScoreDisplay;

    private Label whiteWinsLabel;
    private Label blackWinsLabel;

    private Label whiteTimeLabel;
    private Label blackTimeLabel;

    public PlayerStatsUI(GameControllerInterface controller) {
        this.controller = controller;
        this.viewUtility = new ViewUtility();
        this.getStyleClass().add("GameSettingsUI");
        initializeLabels();
        createUI();
    }

    private void initializeLabels() {
        whiteNameScoreLabel = viewUtility.makeLabel(WHITE_SCORE_LABEL);
        blackNameScoreLabel = viewUtility.makeLabel(BLACK_SCORE_LABEL);
        whiteScoreDisplay = viewUtility.makeLabel(WHITE_SCORE_DISPLAY);
        blackScoreDisplay = viewUtility.makeLabel(BLACK_SCORE_DISPLAY);
        whiteWinsLabel = viewUtility.makeLabel(WHITE_WINS_LABEL);
        blackWinsLabel = viewUtility.makeLabel(BLACK_WINS_LABEL);
        whiteTimeLabel = viewUtility.makeLabel(WHITE_TIME_LABEL);
        blackTimeLabel = viewUtility.makeLabel(BLACK_TIME_LABEL);
    }

    @Override
    public void createUI() {
      this.add(blackNameScoreLabel, 0, 0);
      this.add(blackScoreDisplay, 0, 1);
      this.add(blackWinsLabel, 0, 2);
      this.add(blackTimeLabel, 0, 3);
      this.add(viewUtility.makeText(BLACK_TIME_DISPLAY, getTimeLeft(TEAM_MAP.get(BLACK_TIME_DISPLAY))), 0, 4);
      this.add(whiteTimeLabel, 0, 5);
      this.add(viewUtility.makeText(WHITE_TIME_DISPLAY, getTimeLeft(TEAM_MAP.get(WHITE_TIME_DISPLAY))), 0, 6);
      this.add(whiteNameScoreLabel, 0, 7);
      this.add(whiteScoreDisplay, 0, 8);
      this.add(whiteWinsLabel, 0, 9);
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

    private StringProperty getTimeLeft(int team) {
        return controller.getTimeLeft(team);
    }
}
