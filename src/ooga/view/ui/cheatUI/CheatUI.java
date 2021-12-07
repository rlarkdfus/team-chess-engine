package ooga.view.ui.cheatUI;

import javafx.scene.layout.GridPane;
import ooga.controller.ControllerInterface;
import ooga.controller.GameController;
import ooga.controller.GameControllerInterface;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class CheatUI extends GridPane implements UIInterface {

  private final ControllerInterface controller;
  private final ViewUtility viewUtility;
  private final List<String> cheats;

  public CheatUI(ControllerInterface controller) {
    cheats = new ArrayList<>();
    this.controller = controller;
    this.viewUtility = new ViewUtility();

    TreeSet<String> orderedCheats = new TreeSet<>(GameController.CHEAT_NAMES.keySet());
    cheats.addAll(orderedCheats);
    System.out.println(cheats);

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
      ((GameControllerInterface) controller).handleCheat(cheat);
    } catch (Throwable ignored) {
    }
  }
}
