package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import ooga.controller.Config.JSONWriter;
import ooga.controller.Config.JsonParser;
import ooga.view.LoginView;
import ooga.view.util.ViewUtility;
import org.json.JSONObject;

public class LoginController {
    private final File userProfiles = new File("data/chess/profiles/profiles.json");
    private final ViewUtility viewUtility = new ViewUtility();

    private LoginView loginView;
    private JSONObject userProfilesJSON;

    public LoginController() {
        loginView = new LoginView(this);
        loginView.initializeDisplay();
        userProfilesJSON = JsonParser.loadFile(userProfiles);
    }

    public boolean handleLoginAttempt (String username1, String password1, String username2, String password2) {
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

    public void handleSignUp(String username, String password) {
        JSONObject newProfile =  new JSONObject();
        newProfile.append("password", password);
        newProfile.append("wins", 0);
        userProfilesJSON.append(username, newProfile);
        JSONWriter jsonWriter = new JSONWriter();
        try {
            jsonWriter.saveFile(userProfilesJSON, "data/chess/profiles/profiles.json");
        } catch (IOException e) {
            ViewUtility.showError("Error in accessing user profiles, please play as guest");
        }
    }

    private void hideLoginView() {
        loginView.hideDisplay();
    }

    private boolean isValidLogin(String username, String password) {
        try {
            JSONObject userData = userProfilesJSON.getJSONObject(username);
            String truePassword = userData.getString("password");
            return truePassword.equals(password);
        }
        catch (Exception e) {
            ViewUtility.showError("Invalid username. Please login with an existing username or sign in to create an account");
        }
        return false;
    }
}
