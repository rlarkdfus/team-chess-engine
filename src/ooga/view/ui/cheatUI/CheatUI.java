package ooga.view.ui.cheatUI;

import java.util.List;

import javafx.scene.layout.GridPane;
import ooga.controller.ControllerInterface;
import ooga.controller.GameControllerInterface;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

public class CheatUI extends GridPane implements UIInterface {

    private ControllerInterface controller;
    private ViewUtility viewUtility;
    private final List<String> cheats = List.of("cheat1", "cheat2", "cheat3", "cheat4", "cheat5", "cheat6");

    public CheatUI(ControllerInterface controller) {
        this.controller = controller;
        this.viewUtility = new ViewUtility();
        this.getStyleClass().add("SettingsUI");
        createUI();
    }

    @Override
    public void createUI() {
        this.add(viewUtility.makeLabel("cheats"), 0, 0);
        this.add(viewUtility.makeMenu("cheat_variation", cheats, e -> handleLaunchController(e)), 1, 0);
    }

    private void handleLaunchController(String cheat) {
        try {
            ((GameControllerInterface)controller).handleCheat(cheat);
        } catch (Throwable ignored) {
        }
    }
}
