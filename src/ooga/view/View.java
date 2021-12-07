package ooga.view;

import java.util.List;
import java.util.Objects;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ooga.Location;
import ooga.controller.Config.PieceViewBuilder;
import ooga.controller.ControllerInterface;
import ooga.view.boardview.BoardView;
import ooga.view.util.ViewUtility;

public abstract class View implements ViewInterface {

    public static final String DEFAULT_RESOURCE_PACKAGE = View.class.getPackageName() + ".resources.";
    public static final String STYLE_PACKAGE = "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
    public static final String STYLE_EXTENSION = ".css";
    public static final String DEFAULT_STYLESHEET = "style";
    public static final String DEFAULT_THEME = "light";

    public static final int STAGE_WIDTH = 1000;
    public static final int STAGE_HEIGHT = 700;

    protected ControllerInterface controller;
    protected ViewController viewController;
    protected ViewUtility viewUtility;
    private Stage stage;
    private Scene scene;

    //TODO: change protected
    protected BoardView boardView;

    public View() {
        this.viewController = new ViewController(this);
        this.viewUtility = new ViewUtility();
        this.stage = new Stage();
    }

    protected Scene setupDisplay() {
        GridPane root = new GridPane();
        addUIs(root);
        scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);
        applyStyleSheet(DEFAULT_STYLESHEET);
        applyStyleSheet(DEFAULT_THEME);
        return scene;
    }

    protected abstract void createResettableUIs();

    protected abstract void createStaticUIs();

    protected abstract void addUIs(GridPane root);

    @Override
    public void initializeDisplay(List<PieceViewBuilder> pieceViewList, List<Location> specialPieceLocations, Location bounds) {
        createStaticUIs();
        resetDisplay(pieceViewList, bounds);
    }

    @Override
    public void resetDisplay(List<PieceViewBuilder> pieceViewList, Location bounds) {
        System.out.println("Reset display");
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

    public void changeLanguage(String language) {
        viewUtility.changeLanguage(language);
    }

    @Override
    public void changeTheme(String theme) {
        scene.getStylesheets().remove(1);
        applyStyleSheet(theme);
    }

    private void applyStyleSheet(String name) {
        scene.getStylesheets().add(getClass().getResource(STYLE_PACKAGE + name + STYLE_EXTENSION).toExternalForm());
    }
}