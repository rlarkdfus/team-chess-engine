package ooga.view;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ooga.view.ui.GameInfoUI;
import ooga.view.ui.GameSettingsUI;
import ooga.view.ui.SettingsUI;

public class View implements ViewInterface {
    private final int STAGE_WIDTH = 1000;
    private final int STAGE_HEIGHT = 700;

    private GridPane root;
    private BoardView boardView;
    private SettingsUI settingsUI; // right
    private GameInfoUI gameInfoUI; // left
    private GameSettingsUI gameSettingsInfoUI; // top

    public View() {
        this.boardView = new BoardView(8, 8);

        this.settingsUI = new SettingsUI();
        this.gameInfoUI = new GameInfoUI();
        this.gameSettingsInfoUI = new GameSettingsUI();

        Stage stage = new Stage();
        stage.setScene(setupDisplay());
        stage.show();
    }

    private Scene setupDisplay() {
        root = new GridPane();
        root.add(settingsUI, 2, 1);
        root.add(gameSettingsInfoUI, 0 , 1);
        root.add(boardView, 1, 1);
        return new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);
    }

    @Override
    public void updateDisplay() {
        boardView.updateBoardView();
    }
}