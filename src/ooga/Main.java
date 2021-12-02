package ooga;

import javafx.application.Application;
import javafx.stage.Stage;
import ooga.controller.Controller;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        new Controller();
    }
}
