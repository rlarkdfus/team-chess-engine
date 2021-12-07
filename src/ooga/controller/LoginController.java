package ooga.controller;

import ooga.controller.Config.JSONWriter;
import ooga.controller.Config.JsonParser;
import ooga.model.GameState;
import ooga.view.LoginView;
import ooga.view.util.ViewUtility;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author luis
 * <p>
 * The purpose of this class is to handle the login screen. When Main is run, this controller is used
 * to initiate a login view and upon a successful login, a game or editor controller will be created,
 * allowing the actual game to run.
 * This class depends on the login view and its subclasses, as well as the jsonparser.
 * We assume that there are either 2 players playing or 1 player playing as guest
 * To use, just create a new instance of a Login Controller.
 */
public class LoginController {
  public static final String USER_PROFILES = "data/chess/profiles/profiles.json";
  public static final String JSON_WRITER_FILE_PATH = "data/chess/profiles/profiles";
  public static final String USER_PROFILE_ERROR = "userProfileError";
  public static final String USERNAME_ERROR = "usernameError";
  public static final String ACCOUNT_EXISTS_ERROR = "accountExistsError";
  public static final String SAME_PROFILE_ERROR = "sameProfileError";
  public static final String PASSWORD = "password";
  public static final String WINS = "wins";
  public static final int STARTING_WINS = 0;

  private final File userProfiles = new File(USER_PROFILES);

  private final LoginView loginView;
  private final JSONObject userProfilesJSON;

  /**
   * This constructor creates the login view and initializes it by setting up the proper display UI
   * objects. it also loads a profiles.json which holds every player's username, password, and win count
   */
  public LoginController() {
    loginView = new LoginView(this);
    loginView.initializeDisplay();
    userProfilesJSON = JsonParser.loadFile(userProfiles);
  }

  /**
   * this mmethod is used to determine if the login attempt is correct or invalid. We also check
   * that the same player isn't logging in as both player 1 and 2. upon a successful login, the players
   * are assigned to a color which is used later to update the win counter
   *
   * @param username1 - username of player 1
   * @param password1 - password of player 1
   * @param username2 - username of player 2
   * @param password2 - password of player 2
   * @return - true false depending on if the login attempt was valid
   */
  public boolean handleLoginAttempt(String username1, String password1, String username2, String password2) {
    if (username1.equals(username2)) {
      ViewUtility.showError(SAME_PROFILE_ERROR);
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

  /**
   * if the user decides to play as a guest, no login is handled, and a gamecontroller is initiated
   * to begin gameplay
   */
  public void handleGuestLogin() {
    new GameController();
    loginView.hideDisplay();
  }

  /**
   * this method is used to create a new player profile. we check that the username isn't already
   * taken. if any errors happen, we show an error on the view
   *
   * @param username - username of the new player
   * @param password - password of the new player
   */
  public void handleSignUp(String username, String password) {
    if (checkUsername(username) != null) {
      ViewUtility.showError(ACCOUNT_EXISTS_ERROR);
    } else {
      JSONObject newProfile = new JSONObject();
      newProfile.put(PASSWORD, password);
      newProfile.put(WINS, STARTING_WINS);
      userProfilesJSON.put(username, newProfile);
      try {
        JSONWriter.saveFile(userProfilesJSON, JSON_WRITER_FILE_PATH);
      } catch (IOException e) {
        ViewUtility.showError(USER_PROFILE_ERROR);
      }
    }
  }

  /**
   * this method is used to hide the login view when the game proceeds to the editor or game controller
   */
  private void hideLoginView() {
    loginView.hideDisplay();
  }

  /**
   * this helper method checks that the username and password match
   */
  private boolean isValidLogin(String username, String password) {
    try {
      JSONObject userData = checkUsername(username);
      String truePassword = userData.getString(PASSWORD);
      return truePassword.equals(password);
    } catch (Exception e) {
      ViewUtility.showError(USERNAME_ERROR);
    }
    return false;
  }

  /**
   * this method checks that the username given exists or doesn't
   */
  private JSONObject checkUsername(String username) {
    try {
      JSONObject userData = userProfilesJSON.getJSONObject(username);
      return userData;
    } catch (Exception e) {
      return null;
    }
  }
}
