package ooga.View;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class View implements ViewInterface{
    private final int STAGE_WIDTH = 1000;
    private final int STAGE_HEIGHT = 700;

    private GridPane root;
    private BoardView boardView;

    public View() {
        this.boardView = new BoardView(8, 8);
        Stage stage = new Stage();
        stage.setScene(setupDisplay());
        stage.show();
    }

    private Scene setupDisplay() {
        root = new GridPane();
        root.add(boardView, 0, 0);
        return new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);
    }

    @Override
    public void updateDisplay() {
        boardView.updateBoardView();
    }
}