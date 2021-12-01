package ooga.view;

import java.util.List;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ooga.Turn;
import ooga.controller.Controller;
import ooga.controller.ControllerInterface;
import ooga.controller.PieceViewBuilder;
import ooga.view.ui.gameInfoUI.GameInfoUI;
import ooga.view.ui.gameSettingsUI.GameSettingsUI;
import ooga.view.ui.settingsUI.SettingsUI;
import ooga.view.ui.timeConfigurationUI.TimeConfigurationUI;
import ooga.view.util.ViewUtility;

public class View implements ViewInterface {

    private static final String DEFAULT_RESOURCE_PACKAGE = View.class.getPackageName() + ".resources.";
    private static final String STYLE_PACKAGE = "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
    private static final String DEFAULT_STYLESHEET = STYLE_PACKAGE + "style.css";

    private final int STAGE_WIDTH = 1000;
    private final int STAGE_HEIGHT = 700;

    private Controller controller;
    private ViewController viewController;
    private ViewUtility viewUtility;
    private Stage stage;

    private GridPane root;
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
        root = new GridPane();
        root.add(settingsUI, 2, 1);
        root.add(timeConfigurationUI, 2, 2, 1, 1);
        root.add(gameSettingsInfoUI, 0 , 1, 1, 2);
        root.add(gameInfoUI, 0, 0, 3, 1);
        root.add(boardView, 1, 1, 1, 2);
//        root.add(viewUtility.makeButton("pause", e -> controller.pauseTimer()), 0,2);
//        root.add(viewUtility.makeButton("resume", e -> controller.resumeTimer()), 1,2);
//        root.add(viewUtility.makeButton("reset", e -> controller.resetTimer()), 1,3);
        Scene scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);
        scene.getStylesheets().add(getClass().getResource(DEFAULT_STYLESHEET).toExternalForm());
        return scene;
    }

    @Override
    public void initializeDisplay(List<PieceViewBuilder> pieceViewList) {
        this.timeConfigurationUI = new TimeConfigurationUI(controller, viewController);
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

    public void changeBoardColor(Color color1, Color color2) {
        boardView.changeColors(color1, color2);
    }

    public void changePieceStyle(String style) {
        boardView.changePieceStyle(style);
    }

    public void showError(String message) {viewUtility.showError(message);}
}