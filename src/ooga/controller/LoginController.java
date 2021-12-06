package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import ooga.controller.Config.JSONWriter;
import ooga.controller.Config.JsonParser;
import ooga.model.GameState;
import ooga.view.LoginView;
import ooga.view.util.ViewUtility;
import org.json.JSONObject;

public class LoginController {
    private final File userProfiles = new File("data/chess/profiles/profiles.json");
    private final ViewUtility viewUtility = new ViewUtility();

    private LoginView loginView;
    private JsonParser jsonParser;
    private JSONObject userProfilesJSON;

    public LoginController() {
        loginView = new LoginView(this);
        loginView.initializeDisplay();
        jsonParser = new JsonParser();
        try {
            userProfilesJSON = jsonParser.loadFile(userProfiles);
        } catch (FileNotFoundException e) {
            viewUtility.showError("Error finding user profiles. Please play as guest ");
        }
    }

    public boolean handleLoginAttempt (String username1, String password1, String username2, String password2) {
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
        return false;
    }

    public void handleGuestLogin() {
        new GameController();
    }

    public void handleSignUp(String username, String password) {
        JSONObject newProfile =  new JSONObject();
        newProfile.put("password", password);
        newProfile.put("wins", 0);
        userProfilesJSON.put(username, newProfile);
        try {
            JSONWriter.saveFile(userProfilesJSON, "data/chess/profiles/profiles");
        } catch (IOException e) {
            viewUtility.showError("Error in accessing user profiles, please play as guest");
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
            viewUtility.showError("Invalid username. Please login with an existing username or sign in to create an account");
        }
        return false;
    }
}
