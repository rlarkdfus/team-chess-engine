package ooga.view;

import java.util.List;
import java.util.Objects;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ooga.Turn;
import ooga.controller.Controller;
import ooga.controller.PieceViewBuilder;
import ooga.view.ui.gameInfoUI.GameInfoUI;
import ooga.view.ui.gameSettingsUI.GameSettingsUI;
import ooga.view.ui.settingsUI.SettingsUI;
import ooga.view.ui.timeConfigurationUI.TimeConfigurationUI;
import ooga.view.util.ViewUtility;

public class View implements ViewInterface {

    public static final String DEFAULT_RESOURCE_PACKAGE = View.class.getPackageName() + ".resources.";
    public static final String STYLE_PACKAGE = "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
    public static final String DEFAULT_STYLESHEET = STYLE_PACKAGE + "style.css";

    public static final int STAGE_WIDTH = 1000;
    public static final int STAGE_HEIGHT = 700;

    private Controller controller;
    private ViewController viewController;
    private ViewUtility viewUtility;
    private Stage stage;

    private BoardView boardView;
    private SettingsUI settingsUI; // right
    private GameInfoUI gameInfoUI; // left
    private GameSettingsUI gameSettingsInfoUI; // top
    private TimeConfigurationUI timeConfigurationUI;

    public View(Controller controller) {
        this.controller = controller;
        this.viewController = new ViewController();
        this.viewUtility = new ViewUtility();
        this.stage = new Stage();
        viewController.setView(this);
    }

    private Scene setupDisplay() {
        GridPane root = new GridPane();
        root.add(settingsUI, 2, 1);
        root.add(timeConfigurationUI, 2, 2, 1, 1);
        root.add(gameSettingsInfoUI, 0 , 1, 1, 2);
        root.add(gameInfoUI, 0, 0, 3, 1);
        root.add(boardView, 1, 1, 1, 2);
        Scene scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(DEFAULT_STYLESHEET)).toExternalForm());
        return scene;
    }

    @Override
    public void initializeDisplay(List<PieceViewBuilder> pieceViewList) {
        this.timeConfigurationUI = new TimeConfigurationUI(controller);
        resetDisplay(pieceViewList);
    }

    @Override
    public void resetDisplay(List<PieceViewBuilder> pieceViewList) {
        this.settingsUI = new SettingsUI(controller, viewController);
        this.boardView = new BoardView(controller, pieceViewList, 8, 8);
        this.gameInfoUI = new GameInfoUI();
        this.gameSettingsInfoUI = new GameSettingsUI(controller, viewController);
        stage.setScene(setupDisplay());
        stage.show();
    }

    @Override
    public void updateDisplay(Turn turn) {
        boardView.updateBoardView(turn);
    }

    @Override
    public void changeBoardColor(Color color1, Color color2) {
        boardView.changeColors(color1, color2);
    }

    @Override
    public void changePieceStyle(String style) {
        boardView.changePieceStyle(style);
    }

    @Override
    public void showError(String message) {viewUtility.showError(message);}
}