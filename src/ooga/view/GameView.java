package ooga.view;

import java.util.Map;

import javafx.scene.layout.GridPane;
import ooga.Location;
import ooga.controller.Config.PieceViewBuilder;
import ooga.controller.GameControllerInterface;
import ooga.model.GameState;
import ooga.view.boardview.BoardView;
import ooga.view.boardview.GameBoardView;
import ooga.view.ui.playerStatsUI.PlayerStatsUI;
import ooga.view.ui.settingsUI.SettingsUI;
import ooga.view.ui.timeConfigurationUI.TimeConfigurationUI;

import java.util.List;

public class GameView extends View {

    private BoardView boardView;
    private SettingsUI settingsUI;
    private PlayerStatsUI playerStatsUI;
    private TimeConfigurationUI timeConfigurationUI;
    private GameControllerInterface controller;

    private final int WHITE_SCORE_INDEX = 0;
    private final int BLACK_SCORE_INDEX = 1;

    /**
     * creates the GameView UI
     *
     * @param controller the GameControllerInterface responsible for handling actions in the GameView
     */
    public GameView(GameControllerInterface controller) {
        super();
        this.controller = controller;
    }

    /**
     * initializes the display of the GameView with appropriate images and values
     *
     * @param pieceViewList    the list of pieceViews representing images of the pieces
     * @param specialLocations the locations of special items (such as powerups)
     * @param bounds           the dimensions of the boardView
     */
    @Override
    public void initializeDisplay(List<PieceViewBuilder> pieceViewList, List<Location> specialLocations, Location bounds) {
        super.initializeDisplay(pieceViewList, specialLocations, bounds);
        updatePlayerStatsUI(controller.getUpdatedScores());
    }


    /**
     * initializes the player names and wins
     *
     * @param names the usernames for both sides
     * @param wins  the number of wins for both sides
     */
    private void initializePlayers(Map<Enum, String> names, Map<Enum, Integer> wins) {
        playerStatsUI.initializePlayerNames(names.get(GameState.WHITE), names.get(GameState.BLACK));
        playerStatsUI.initializePlayerWins(wins.get(GameState.WHITE), wins.get(GameState.BLACK));
    }

    /**
     * updates the playerStatsUI with the scores of the players
     *
     * @param scores the list of scores
     */
    private void updatePlayerStatsUI(List<Integer> scores) {
        playerStatsUI.updateUI(scores.get(WHITE_SCORE_INDEX), scores.get(BLACK_SCORE_INDEX));
    }

    /**
     * initializes the BoardView with a list of piece images and special locations, as well as the bounds
     *
     * @param pieceViewList
     * @param specialLocations special
     * @param bounds
     * @return
     */
    protected BoardView initializeBoardView(List<PieceViewBuilder> pieceViewList, List<Location> specialLocations, Location bounds) {
        boardView = new GameBoardView(controller, pieceViewList, bounds.getRow(), bounds.getCol());
        boardView.markInitialSpecialLocation(specialLocations);
        return boardView;
    }

    /**
     * initializes the UI of the GameView
     *
     * @param viewController the viewController responsible for handling actions in the UI
     */
    @Override
    protected void initializeUI(ViewController viewController) {
        this.timeConfigurationUI = new TimeConfigurationUI(controller);
        this.settingsUI = new SettingsUI(controller, viewController);
        this.playerStatsUI = new PlayerStatsUI(controller);
    }

    /**
     * adds the UIs to the GridPane
     *
     * @return the GridPane created
     */
    @Override
    protected GridPane addUIs() {
        GridPane root = new GridPane();
        root.add(settingsUI, 2, 1);
        root.add(boardView, 1, 1, 1, 2);
        root.add(playerStatsUI, 0, 1, 1, 2);
        root.add(timeConfigurationUI, 2, 2, 1, 1);
        return root;
    }

    /**
     * updates the GameView dislay
     *
     * @param pieceViewList the new pieceViewList representing the next game state used to update the display
     */
    @Override
    public void updateDisplay(List<PieceViewBuilder> pieceViewList) {
        super.updateDisplay(pieceViewList);
        updatePlayerStatsUI(controller.getUpdatedScores());
        initializePlayers(controller.getUsernames(), controller.getWins());
    }
}

