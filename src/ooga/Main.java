package ooga;

import javafx.application.Application;
import javafx.stage.Stage;
import ooga.controller.GameController;
import ooga.controller.LoginController;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
//       new LoginController();
//        new EditorController();
        new GameController();
    }
}
