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

    public void handleLoginAttempt(String username, String password) throws Exception {
        if (isValidLogin(username, password)) {
            new Controller();

        }
    }

    public void handleGuestLogin() {
        new Controller();
    }

    public void hideLoginView() {
        loginView.hideDisplay();
    }

    private boolean isValidLogin(String username, String password) throws Exception {
        System.out.println("validating login");
        try {
            jsonParser = new JsonParser();
            File userFile = new File(String.format("data/chess/profiles/%s.json", username));
            JSONObject userData = jsonParser.loadFile(userFile);
            String truePassword = userData.getString("password");
            return truePassword.equals(password);
        }
        catch (Exception e) {
            System.out.println("invalid login");
            throw new Exception("Invalid username. Please login with an existing username or sign in to create an account");
        }
    }
}
