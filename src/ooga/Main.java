package ooga;

import javafx.application.Application;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import ooga.controller.Controller;
import ooga.view.util.ViewUtility;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        ViewUtility viewUtility = new ViewUtility();
//        TextInputDialog dlg = viewUtility.makeUncancellableTextInputDialog("header");
//        Set<String> acceptable = Set.of("king", "queen", "rook");
//        viewUtility.setTextInputDialogCloseRestrictions(dlg, acceptable);
//        viewUtility.getTextInputDialogResult(dlg);
        new Controller();
    }
}
