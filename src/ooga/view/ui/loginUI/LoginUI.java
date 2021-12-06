package ooga.view.ui.loginUI;

import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyEvent;
import ooga.controller.LoginController;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;
import java.util.List;

/**
 * Purpose: LoginUI creates the interactive elements of the LoginView which allows players to sign
 * in to their profiles, sign up for new profiles, or play as a guest if they choose.
 * Assumptions: It is assumed that the elements of the LoginController that are called by LoginUI
 * will accomplish their designed function so that the user data will be stored correctly in their
 * profiles.
 * Dependencies: LoginUI depends on ViewUtility and LoginController
 *
 * @author Richard Deng and Luis Pereda
 */
public class LoginUI extends GridPane implements UIInterface {
    public static final String LOGIN_UI = "LoginUI";
    public static final String BLACK_USERNAME_FIELD = "black_username_field";
    public static final String BLACK_PASSWORD_FIELD = "black_password_field";
    public static final String WHITE_USERNAME_FIELD = "white_username_field";
    public static final String WHITE_PASSWORD_FIELD = "white_password_field";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String WELCOME = "welcome";
    public static final String BLACK_TEAM= "blackTeam";
    public static final String WHITE_TEAM = "whiteTeam";
    public static final String LOGIN = "login";
    public static final String REGISTER = "register";
    public static final String GUEST = "guest";
    public static final String INCORRECT_PASSWORD = "incorrectPassword";
    public static final String REGISTER_USERNAME_FIELD = "register_username_field";
    public static final String REGISTER_PASSWORD_FIELD = "register_password_field";

    private LoginController loginController;
    private ViewUtility viewUtility;
    private Label incorrectPassword;
    private TextField whiteUsernameField;
    private TextField whitePasswordField;
    private TextField blackUsernameField;
    private TextField blackPasswordField;

    /**
     * Creates a new LoginUI
     * @param loginController LoginController to handle the user inputs and convert them to usable
     *                        data
     */
    public LoginUI(LoginController loginController) {
        this.loginController = loginController;
        this.viewUtility = new ViewUtility();
        this.getStyleClass().add(LOGIN_UI);
        initializeTextFields();
        createUI();
    }

    private void initializeTextFields() {
        blackUsernameField = viewUtility.makeTextField(BLACK_USERNAME_FIELD,  e -> handleKeyPressed(e));
        blackPasswordField = viewUtility.makePasswordField(BLACK_PASSWORD_FIELD, e -> handleKeyPressed(e));
        whiteUsernameField = viewUtility.makeTextField(WHITE_USERNAME_FIELD,  e -> handleKeyPressed(e));
        whitePasswordField = viewUtility.makePasswordField(WHITE_PASSWORD_FIELD, e -> handleKeyPressed(e));
    }

    /**
     * Create the buttons, labels, and text fields that will take in the login information of the
     * users.
     */
    @Override
    public void createUI() {
        this.add(viewUtility.makeLabel(WELCOME), 0, 0, 1, 1);
        this.add(viewUtility.makeLabel(BLACK_TEAM), 0, 1, 1, 1);
        this.add(viewUtility.makeLabel(USERNAME), 0, 2, 1, 1);
        this.add(blackUsernameField, 0, 3, 3, 1);
        this.add(viewUtility.makeLabel(PASSWORD), 0, 4, 3, 1);
        this.add(blackPasswordField, 0, 5, 3, 1);
        this.add(viewUtility.makeLabel(WHITE_TEAM), 0, 6, 1, 1);
        this.add(viewUtility.makeLabel(USERNAME), 0, 7, 1, 1);
        this.add(whiteUsernameField, 0, 8, 3, 1);
        this.add(viewUtility.makeLabel(PASSWORD), 0, 9, 1, 1);
        this.add(whitePasswordField, 0, 10, 3, 1);
        this.add(viewUtility.makeButton(LOGIN, e -> handleLoginAction()), 0, 11, 1, 1);
        this.add(viewUtility.makeButton(REGISTER, e -> handleRegisterAction()), 1, 11, 1, 1);
        this.add(viewUtility.makeButton(GUEST, e -> handleGuestKeyPressed()), 2, 11, 1, 1);
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
        String whiteUsername = whiteUsernameField.getText();
        String whitePassword = whitePasswordField.getText();
        String blackUsername = blackUsernameField.getText();
        String blackPassword = blackPasswordField.getText();
        boolean login = loginController.handleLoginAttempt(blackUsername, blackPassword, whiteUsername, whitePassword);
        if (!login) {
            incorrectPassword = viewUtility.makeLabel(INCORRECT_PASSWORD);
            this.add(incorrectPassword, 1, 4, 1, 1);
        }
    }

    private void handleRegisterAction() {
        Dialog dlg = viewUtility.makeDialog(List.of(viewUtility.makeLabel(USERNAME), viewUtility.makeLabel(PASSWORD)),
                List.of(viewUtility.makeTextField(REGISTER_USERNAME_FIELD), viewUtility.makeTextField(REGISTER_PASSWORD_FIELD)));
        loginController.handleSignUp(viewUtility.getDialogResults(dlg).get(0), viewUtility.getDialogResults(dlg).get(1));
    }
}
