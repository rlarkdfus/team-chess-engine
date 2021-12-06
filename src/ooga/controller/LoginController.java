package ooga.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.controller.Config.JSONWriter;
import ooga.controller.Config.JsonParser;
import ooga.model.GameState;
import ooga.view.LoginView;
import ooga.view.util.ViewUtility;
import org.json.JSONObject;

public class LoginController {
    public static final String USER_PROFILES = "data/chess/profiles/profiles.json";
    public static final String RESOURCE_BUNDLE_PATH = "ooga/controller/resources/English";
    public static final String JSON_WRITER_FILE_PATH = "data/chess/profiles/profiles";
    public static final String USER_PROFILE_ERROR = "userProfileError";
    public static final String USERNAME_ERROR = "usernameError";
    public static final String ACCOUNT_EXISTS_ERROR = "accountExistsError";
    public static final String SAME_PROFILE_ERROR = "sameProfileError";
    public static final String PASSWORD = "password";
    public static final String WINS = "wins";
    public static final int STARTING_WINS = 0;

    private final File userProfiles = new File(USER_PROFILES);
    private final ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_PATH);

    private LoginView loginView;
    private JSONObject userProfilesJSON;

    public LoginController() {
        loginView = new LoginView(this);
        loginView.initializeDisplay();
        userProfilesJSON = JsonParser.loadFile(userProfiles);
    }

    public boolean handleLoginAttempt (String username1, String password1, String username2, String password2) {
        if (username1.equals(username2)) {
            ViewUtility.showError(resourceBundle.getString(SAME_PROFILE_ERROR));
        } else {
            if (isValidLogin(username1, password1) && isValidLogin(username2, password2)) {
                Map<Enum, JSONObject> players = new HashMap<>();
                Map<Enum, String> usernames = new HashMap<>();
                players.put(GameState.BLACK, userProfilesJSON.getJSONObject(username1));
                players.put(GameState.WHITE, userProfilesJSON.getJSONObject(username2));
                usernames.put(GameState.BLACK, username1);
                usernames.put(GameState.WHITE, username2);
                new GameController().setPlayers(usernames, players);

                hideLoginView();
                return true;
            }
        }
        return false;
    }

    public void handleGuestLogin() {
        new GameController();
    }

    public void handleSignUp(String username, String password) {
        if (checkUsername(username) != null) {
            ViewUtility.showError(resourceBundle.getString(ACCOUNT_EXISTS_ERROR));
        } else {
            JSONObject newProfile = new JSONObject();
            newProfile.put(PASSWORD, password);
            newProfile.put(WINS, STARTING_WINS);
            userProfilesJSON.put(username, newProfile);
            try {
                JSONWriter.saveFile(userProfilesJSON, JSON_WRITER_FILE_PATH);
            } catch (IOException e) {
                ViewUtility.showError(resourceBundle.getString(USER_PROFILE_ERROR));
            }
        }
    }

    private void hideLoginView() {
        loginView.hideDisplay();
    }

    private boolean isValidLogin(String username, String password) {
        try {
            JSONObject userData = checkUsername(username);
            String truePassword = userData.getString(PASSWORD);
            return truePassword.equals(password);
        }
        catch (Exception e) {
            ViewUtility.showError(resourceBundle.getString(USERNAME_ERROR));
        }
        return false;
    }

    private JSONObject checkUsername(String username) {
        JSONObject userData = userProfilesJSON.getJSONObject(username);
        return userData;
    }
}
