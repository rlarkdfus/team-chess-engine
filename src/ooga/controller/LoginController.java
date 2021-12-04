package ooga.controller;

import java.io.File;
import ooga.view.LoginView;
import org.json.JSONObject;

public class LoginController {
    private LoginView loginView;
    private JsonParser jsonParser;

    public LoginController() {
        loginView = new LoginView(this);
        loginView.initializeDisplay();
    }

    public boolean handleLoginAttempt(String username1, String password1, String username2, String password2) throws Exception {
        if (isValidLogin(username1, password1) && isValidLogin(username2, password2)) {
            new GameController();
            hideLoginView();
            return true;
        }
        return false;
    }

    public void handleGuestLogin() {
        new GameController();
    }

    public void hideLoginView() {
        loginView.hideDisplay();
    }

    private boolean isValidLogin(String username, String password) throws Exception {
        try {
            jsonParser = new JsonParser();
            File userFile = new File(String.format("data/chess/profiles/%s.json", username));
            JSONObject userData = jsonParser.loadFile(userFile);
            String truePassword = userData.getString("password");
            return truePassword.equals(password);
        }
        catch (Exception e) {
            throw new Exception("Invalid username. Please login with an existing username or sign in to create an account");
        }
    }
}
