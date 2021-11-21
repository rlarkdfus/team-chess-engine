package ooga.view;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ooga.Turn;
import ooga.controller.BoardBuilder;
import ooga.controller.Controller;
import ooga.view.ui.gameInfoUI.GameInfoUI;
import ooga.view.ui.gameSettingsUI.GameSettingsUI;
import ooga.view.ui.settingsUI.SettingsUI;
import ooga.view.util.ViewUtility;

import java.util.List;

public class View implements ViewInterface {

    private static final String DEFAULT_RESOURCE_PACKAGE = View.class.getPackageName() + ".resources.";
    private static final String STYLE_PACKAGE = "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
    private static final String DEFAULT_STYLESHEET = STYLE_PACKAGE + "style.css";

    private final int STAGE_WIDTH = 1000;
    private final int STAGE_HEIGHT = 700;

    private Controller controller;
    private ViewController viewController;
    private Stage stage;

    private GridPane root;
    private BoardView boardView;
    private SettingsUI settingsUI; // right
    private GameInfoUI gameInfoUI; // left
    private GameSettingsUI gameSettingsInfoUI; // top

    public View(Controller controller) {
        this.controller = controller;
        this.viewController = new ViewController();
        this.stage = new Stage();
        //TODO: this is probably bad design idk
        viewController.setView(this);
        //this.viewController = controller.getViewController();
    }

    private Scene setupDisplay() {
        root = new GridPane();
        root.add(settingsUI, 2, 1);
        root.add(gameSettingsInfoUI, 0 , 1);
        root.add(gameInfoUI, 0, 0, 3, 1);
        root.add(boardView, 1, 1);
        Scene scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);
        scene.getStylesheets().add(getClass().getResource(DEFAULT_STYLESHEET).toExternalForm());
        return scene;
    }

    @Override
    public void initializeDisplay(List<BoardBuilder.PieceViewBuilder> pieceViewList) {
        this.boardView = new BoardView(controller, pieceViewList, 8, 8);
        this.settingsUI = new SettingsUI(controller, viewController);
        this.gameInfoUI = new GameInfoUI();
        this.gameSettingsInfoUI = new GameSettingsUI(controller, viewController);
        stage.setScene(setupDisplay());
        stage.show();
    }

    @Override
    public void updateDisplay(Turn turn) {
        boardView.updateBoardView(turn);
    }

    public void changeBoardColor(Color color1, Color color2) {
        boardView.changeColors(color1, color2);
    }

    public void changePieceStyle(String style) {
        boardView.changePieceStyle(style);
    }

    public void resetBoard() {
        boardView.resetBoard();
    }
}