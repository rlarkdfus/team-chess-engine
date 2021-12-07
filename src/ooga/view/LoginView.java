package ooga.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ooga.controller.LoginController;
import ooga.view.ui.loginUI.LoginUI;
import ooga.view.util.ViewUtility;

import java.util.Objects;

public class LoginView {

  public static final String DEFAULT_RESOURCE_PACKAGE = View.class.getPackageName() + ".resources.";
  public static final String STYLE_PACKAGE = "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  public static final String DEFAULT_STYLESHEET = STYLE_PACKAGE + "style.css";
  public static final String CSS_ID = "login-window";
  public static final int STAGE_WIDTH = 600;
  public static final int STAGE_HEIGHT = 500;

  private final LoginController loginController;
  private final Stage stage;

  /**
   * creates the login UI
   *
   * @param loginController the loginController responsible for handling the LoginView's behaviors
   */
  public LoginView(LoginController loginController) {
    this.loginController = loginController;
    this.stage = new Stage();
    this.stage.setResizable(false);
  }

  /**
   * sets up the display
   *
   * @return the Scene created
   */
  private Scene setupDisplay() {
    GridPane root = new GridPane();
    root.getStyleClass().add(CSS_ID);
    root.setAlignment(Pos.CENTER);
    root.add(new LoginUI(loginController), 0, 3, 5, 5);
    Scene scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);
    scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource(DEFAULT_STYLESHEET)).toExternalForm());
    return scene;
  }

  /**
   * shows the login screen
   */
  public void initializeDisplay() {
    stage.setScene(setupDisplay());
    stage.show();
  }

  /**
   * closes the login screen
   */
  public void hideDisplay() {
    stage.close();
  }

  /**
   * shows an error in the view
   *
   * @param message the error message
   */
  public void showError(String message) {
    ViewUtility.showError(message);
  }
}
