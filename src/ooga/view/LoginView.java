package ooga.view;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.view.ui.loginUI.LoginUI;
import ooga.view.util.ViewUtility;

import java.util.Objects;

public class LoginView {

    public static final String DEFAULT_RESOURCE_PACKAGE = View.class.getPackageName() + ".resources.";
    public static final String STYLE_PACKAGE = "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
    public static final String DEFAULT_STYLESHEET = STYLE_PACKAGE + "style.css";

    public static final int STAGE_WIDTH = 345;
    public static final int STAGE_HEIGHT = 220;

    private Controller controller;
    private Stage stage;
    private ViewUtility viewUtility;

    public LoginView(Controller controller) {
        this.controller = controller;
        this.stage = new Stage();
        this.viewUtility = new ViewUtility();
    }

    private Scene setupDisplay() {
        GridPane root = new GridPane();
        root.add(new LoginUI(controller), 0, 0);
        Scene scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(DEFAULT_STYLESHEET)).toExternalForm());
        return scene;
    }

    public void initializeDisplay() {
        stage.setScene(setupDisplay());
        stage.show();
    }

    public void hideDisplay() {
        stage.hide();
    }

    public void showError(String message) {
        viewUtility.showError(message);
    }
}
