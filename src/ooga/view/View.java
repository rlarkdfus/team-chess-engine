package ooga.view;

import java.util.List;
import java.util.Objects;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.PieceViewBuilder;
import ooga.view.boardview.BoardView;
import ooga.view.boardview.GameBoardView;
import ooga.view.ui.gameInfoUI.GameInfoUI;
import ooga.view.ui.gameSettingsUI.GameSettingsUI;
import ooga.view.ui.settingsUI.SettingsUI;
import ooga.view.ui.timeConfigurationUI.TimeConfigurationUI;
import ooga.view.util.ViewUtility;

public abstract class View implements ViewInterface {

    public static final String DEFAULT_RESOURCE_PACKAGE = View.class.getPackageName() + ".resources.";
    public static final String STYLE_PACKAGE = "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
    public static final String DEFAULT_STYLESHEET = STYLE_PACKAGE + "style.css";

    public static final int STAGE_WIDTH = 1000;
    public static final int STAGE_HEIGHT = 700;

    protected Controller controller;
    protected ViewController viewController;
    protected ViewUtility viewUtility;
    private Stage stage;

    //TODO: change protected
    protected BoardView boardView;
    
    public View(Controller controller) {
        this.controller = controller;
        this.viewController = new ViewController();
        this.viewUtility = new ViewUtility();
        this.stage = new Stage();
        viewController.setView(this);
    }
    
    protected Scene setupDisplay() {
        GridPane root = new GridPane();
        addUIs(root);
        Scene scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(DEFAULT_STYLESHEET)).toExternalForm());
        return scene;
    }
    
    protected abstract void createResettableUIs();

    protected abstract void createStaticUIs();
    
    protected abstract void addUIs(GridPane root);
    
    @Override
    public void initializeDisplay(List<PieceViewBuilder> pieceViewList) {
        createStaticUIs();
        resetDisplay(pieceViewList);
    }
    
    @Override
    public void resetDisplay(List<PieceViewBuilder> pieceViewList) {
        this.boardView = new GameBoardView(controller, pieceViewList, 8, 8);
        createResettableUIs();
        stage.setScene(setupDisplay());
        stage.show();
    }
    
    @Override
    public void updateDisplay(List<PieceViewBuilder> pieceViewList) {
        boardView.updateBoardView(pieceViewList);
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