package ooga.view.ui.loginUI;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import ooga.controller.LoginController;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

public class LoginUI extends GridPane implements UIInterface {

    LoginController loginController;
    ViewUtility viewUtility;

    public LoginUI(LoginController loginController) {
        this.loginController = loginController;
        this.viewUtility = new ViewUtility();
        this.getStyleClass().add("LoginUI");
        createUI();
    }

    @Override
    public void createUI() {
        this.add(viewUtility.makeLabel("welcome"), 0, 0, 1, 1);
        this.add(viewUtility.makeLabel("username"), 0, 1, 1, 1);
        this.add(viewUtility.makeTextField("username_field", this::handleKeyPressed), 0, 2, 1, 1);
        this.add(viewUtility.makeLabel("password"), 0, 3, 1, 1);
        this.add(viewUtility.makePasswordField("password_field", this::handleKeyPressed), 0, 4, 1, 1);
        this.add(viewUtility.makeButton("login", e -> handleLoginAction()), 0, 5, 1, 1);
    }

    private void handleKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            handleLoginAction();
        }
    }

    private void handleLoginAction() {
        String username = ((TextField) lookup("#username_field")).getText();
        String password = ((PasswordField) lookup("#password_field")).getText();
        loginController.handleLoginAttempt(username, password);
    }
}
