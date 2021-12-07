package ooga.view;

import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ooga.Location;
import ooga.controller.Config.PieceViewBuilder;
import ooga.view.boardview.BoardView;
import ooga.view.util.ViewUtility;

public abstract class View implements ViewInterface {
    public static final String DEFAULT_RESOURCE_PACKAGE = View.class.getPackageName() + ".resources.";
    public static final String STYLE_PACKAGE = "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
    public static final ResourceBundle STYLE_BUNDLE = ResourceBundle.getBundle("ooga/view/resources/Style");

    public static final String STYLE_EXTENSION = STYLE_BUNDLE.getString("css_extension");
    public static final String DEFAULT_STYLESHEET = STYLE_BUNDLE.getString("default_css");
    public static final String DEFAULT_THEME = STYLE_BUNDLE.getString("default_theme");

    public static final int STAGE_WIDTH = Integer.parseInt(STYLE_BUNDLE.getString("stage_width"));
    public static final int STAGE_HEIGHT = Integer.parseInt(STYLE_BUNDLE.getString("stage_height"));

    private ViewController viewController;
    private Stage stage;
    private Scene scene;
    private BoardView boardView;

    public View() {
        this.viewController = new ViewController(this);
        this.stage = new Stage();
    }

    @Override
    public void initializeDisplay(List<PieceViewBuilder> pieceViewList, List<Location> specialLocations, Location bounds) {
        this.boardView = initializeBoardView(pieceViewList, specialLocations, bounds);
        initializeUI(viewController);
        scene = initializeScene();
        stage.setScene(scene);
        stage.show();
    }

    protected abstract void initializeUI(ViewController viewController);

    protected abstract GridPane addUIs();

    protected abstract BoardView initializeBoardView(List<PieceViewBuilder> pieceViewList, List<Location> specialLocations, Location bounds);

    private Scene initializeScene() {
        GridPane root = addUIs();
        Scene scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);
        applyStyleSheet(scene, DEFAULT_STYLESHEET);
        applyStyleSheet(scene, DEFAULT_THEME);
        return scene;
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
        ViewUtility.changeLanguage(language);
    }

    @Override
    public void changeTheme(String theme) {
        scene.getStylesheets().remove(scene.getStylesheets().size() - 1);
        applyStyleSheet(scene, theme);
    }

    private void applyStyleSheet(Scene scene, String name) {
        scene.getStylesheets().add(getClass().getResource(STYLE_PACKAGE + name + STYLE_EXTENSION).toExternalForm());
    }
}