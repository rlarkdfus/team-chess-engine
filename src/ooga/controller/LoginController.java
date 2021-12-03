package ooga.controller;

import java.io.File;
import ooga.view.LoginView;
import org.json.JSONObject;

public class LoginController {
//    private LoginView loginView;
//    private Controller controller;
    private JsonParser jsonParser;


//    public void hideLoginView() {
//        loginView.hideDisplay();
//    }

    public boolean isValidLogin(String username, String password) throws Exception {
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
