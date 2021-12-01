package ooga.controller;

import ooga.view.LoginView;

public class LoginController {
    private LoginView loginView;
    private Controller controller;

    public LoginController(Controller controller) {
        this.controller = controller;
        loginView = new LoginView(this);
        loginView.initializeDisplay();
    }

    public void handleLoginAttempt(String username, String password) {
        if (isValidAttempt(username, password)) {
            controller.startGame();
        }
    }

    public void hideLoginView() {
        loginView.hideDisplay();
    }

    private boolean isValidAttempt(String username, String password) {
        return true;
    }

}
