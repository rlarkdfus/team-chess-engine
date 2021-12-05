package ooga.view.ui.loginUI;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import ooga.controller.LoginController;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

import java.util.List;
import java.util.Optional;

public class LoginUI extends GridPane implements UIInterface {

    LoginController loginController;
    ViewUtility viewUtility;
    Label incorrectPassword;

    public LoginUI(LoginController loginController) {
        this.loginController = loginController;
        this.viewUtility = new ViewUtility();
        this.getStyleClass().add("LoginUI");
        createUI();
    }

    @Override
    public void createUI() {
        this.add(viewUtility.makeLabel("welcome"), 0, 0, 1, 1);
        this.add(viewUtility.makeLabel("blackTeam"), 0, 1, 1, 1);
        this.add(viewUtility.makeLabel("username"), 0, 2, 1, 1);
        this.add(viewUtility.makeTextField("black_username_field",  e -> handleKeyPressed(e)), 0, 3, 3, 1);
        this.add(viewUtility.makeLabel("password"), 0, 4, 3, 1);
        this.add(viewUtility.makePasswordField("black_password_field", e -> handleKeyPressed(e)), 0, 5, 3, 1);
        this.add(viewUtility.makeLabel("whiteTeam"), 0, 6, 1, 1);
        this.add(viewUtility.makeLabel("username"), 0, 7, 1, 1);
        this.add(viewUtility.makeTextField("white_username_field",  e -> handleKeyPressed(e)), 0, 8, 3, 1);
        this.add(viewUtility.makeLabel("password"), 0, 9, 1, 1);
        this.add(viewUtility.makePasswordField("white_password_field", e -> handleKeyPressed(e)), 0, 10, 3, 1);
        this.add(viewUtility.makeButton("login", e -> handleLoginAction()), 0, 11, 1, 1);
        this.add(viewUtility.makeButton("register", e -> handleRegisterAction()), 1, 11, 1, 1);
        this.add(viewUtility.makeButton("guest", e -> handleGuestKeyPressed()), 2, 11, 1, 1);
    }

    private void handleKeyPressed(KeyEvent e) {
        if (this.getChildren().contains(incorrectPassword)) {
            this.getChildren().remove(incorrectPassword);
        }
        if (e.getCode() == KeyCode.ENTER) {
            handleLoginAction();
        }
    }

    private void handleGuestKeyPressed() {
        loginController.handleGuestLogin();
    }

    private void handleLoginAction() {
        String whiteUsername = ((TextField) lookup("#white_username_field")).getText();
        String whitePassword = ((PasswordField) lookup("#white_password_field")).getText();
        String blackUsername = ((TextField) lookup("#black_username_field")).getText();
        String blackPassword = ((PasswordField) lookup("#black_password_field")).getText();
        boolean login = false;
        try {
            login = loginController.handleLoginAttempt(blackUsername, blackPassword, whiteUsername, whitePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(login);
        if (!login) {
            incorrectPassword = viewUtility.makeLabel("incorrectPassword");
            this.add(incorrectPassword, 1, 4, 1, 1);

        }
    }

    private void handleRegisterAction() {
        Optional<String[]> strArr = viewUtility.makeDialog(List.of(new Label("Username"), new Label("Password")), List.of(new TextField(), new TextField())).showAndWait();
        for (String s : strArr.get()) {
            System.out.println(s);
        }
    }
}
