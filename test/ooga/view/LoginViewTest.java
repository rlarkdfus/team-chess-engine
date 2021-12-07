package ooga.view;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ooga.controller.LoginController;
import org.junit.jupiter.api.Test;
import org.testfx.service.query.EmptyNodeQueryException;
import util.DukeApplicationTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoginViewTest extends DukeApplicationTest {

    private TextField blackUsernameField;
    private TextField whiteUsernameField;
    private PasswordField blackPasswordField;
    private PasswordField whitePasswordField;
    private Button login;
    private Button register;
    private Button guest;

    @Override
    public void start(Stage stage) {
        new LoginController();
        blackUsernameField = lookup("#black_username_field").query();
        blackPasswordField = lookup("#black_password_field").query();
        whiteUsernameField = lookup("#white_username_field").query();
        whitePasswordField = lookup("#white_password_field").query();
        login = lookup("#login").queryButton();
        register = lookup("#register").queryButton();
        guest = lookup("#guest").queryButton();
    }

    @Test
    void testEnterNothingAndClickingLogin() {
        clickOn(login);
        assertDoesNotThrow(() -> lookup("#login").queryButton());
    }

    @Test
    void testEnterCorrectUsernamesAndPasswords() {
        String user1 = "Richard";
        String user2 = "Luis";
        String pass1 = "123";
        String pass2 = "password";
        clickOn(blackUsernameField).write(user1);
        clickOn(blackPasswordField).write(pass1);
        clickOn(whiteUsernameField).write(user2);
        clickOn(whitePasswordField).write(pass2);
        clickOn(login);
        assertThrows(EmptyNodeQueryException.class, () -> lookup("#login").queryButton());
    }

    @Test
    void testEnterIncorrectUsername() {
        String user1 = "Rich";
        String user2 = "Luis";
        String pass1 = "123";
        String pass2 = "password";
        clickOn(blackUsernameField).write(user1);
        clickOn(blackPasswordField).write(pass1);
        clickOn(whiteUsernameField).write(user2);
        clickOn(whitePasswordField).write(pass2);
        clickOn(login);
        assertDoesNotThrow(() -> lookup("#login").queryButton());
    }

    @Test
    void testEnterIncorrectPassword() {
        String user1 = "Richard";
        String user2 = "Luis";
        String pass1 = "bananas";
        String pass2 = "password";
        clickOn(blackUsernameField).write(user1);
        clickOn(blackPasswordField).write(pass1);
        clickOn(whiteUsernameField).write(user2);
        clickOn(whitePasswordField).write(pass2);
        clickOn(login);
        assertDoesNotThrow(() -> lookup("#login").queryButton());
    }

    @Test
    void testSameUsers() {
        String user1 = "Richard";
        String user2 = "Richard";
        String pass1 = "123";
        String pass2 = "123";
        clickOn(blackUsernameField).write(user1);
        clickOn(blackPasswordField).write(pass1);
        clickOn(whiteUsernameField).write(user2);
        clickOn(whitePasswordField).write(pass2);
        clickOn(login);
        assertDoesNotThrow(() -> lookup("#login").queryButton());
    }

    @Test
    void testGuestLogin() {
        clickOn(guest);
        assertThrows(EmptyNodeQueryException.class, () -> lookup("#login").queryButton());
    }
}
