package ooga.view;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.controller.LoginController;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class LoginViewTest extends DukeApplicationTest {

    private TextField usernameField;
    private PasswordField passwordField;
    private Button login;

    @Override
    public void start(Stage stage) {
        new LoginController();
        usernameField = lookup("#username_field").query();
        passwordField = lookup("#password_field").query();
        login = lookup("#login").queryButton();
    }

    @Test
    void testEnterUsername() {
        String expected = "test_user";
        clickOn(usernameField);
        write(expected);
        clickOn(login);
    }

    @Test
    void testEnterPassword() {
        String expected = "test_password";
        clickOn(passwordField);
        write(expected);
        clickOn(login);
    }
}
