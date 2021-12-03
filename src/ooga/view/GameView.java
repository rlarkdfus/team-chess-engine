package ooga.view;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import ooga.controller.Controller;
import ooga.controller.ControllerInterface;
import ooga.controller.PieceViewBuilder;
import ooga.view.boardview.GameBoardView;
import ooga.view.ui.gameInfoUI.GameInfoUI;
import ooga.view.ui.gameSettingsUI.GameSettingsUI;
import ooga.view.ui.settingsUI.SettingsUI;
import ooga.view.ui.timeConfigurationUI.TimeConfigurationUI;

import java.util.List;
import java.util.Objects;

public class GameView extends View {

    private SettingsUI settingsUI; // right
    private GameInfoUI gameInfoUI; // left
    private GameSettingsUI gameSettingsInfoUI; // top
    private TimeConfigurationUI timeConfigurationUI;
    
    public GameView(Controller controller) {
        super(controller);
    }

    @Override
    protected void createStaticUIs() {
        this.timeConfigurationUI = new TimeConfigurationUI(controller);
    }
    
    @Override
    protected void createResettableUIs() {
        this.settingsUI = new SettingsUI(controller, viewController);
        this.gameSettingsInfoUI = new GameSettingsUI(controller, viewController);
        this.gameInfoUI = new GameInfoUI();
    }
    
    @Override
    protected void addUIs(GridPane root) {
        root.add(settingsUI, 2, 1);
        root.add(gameInfoUI, 0, 0, 3, 1);
        root.add(boardView, 1, 1, 1, 2);
        root.add(gameSettingsInfoUI, 0 , 1, 1, 2);
        root.add(timeConfigurationUI, 2, 2, 1, 1);
    }
}

