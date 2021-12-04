package ooga.view;

import javafx.scene.layout.GridPane;
import ooga.controller.Controller;
import ooga.controller.Config.PieceViewBuilder;
import ooga.view.boardview.EditorBoardView;
import ooga.view.ui.settingsUI.SettingsUI;

import java.util.List;

public class EditorView extends View {

    private SettingsUI settingsUI; // right

    public EditorView(Controller controller) {
        super(controller);
    }

    @Override
    protected void createStaticUIs() {
    }

    @Override
    protected void createResettableUIs() {
        this.settingsUI = new SettingsUI(controller, viewController);
    }
    
    @Override
    protected void addUIs(GridPane root) {
        root.add(settingsUI, 2, 1);
        root.add(boardView, 1, 1, 1, 2);
    }

    @Override
    public void resetDisplay(List<PieceViewBuilder> pieceViewList) {
        this.boardView = new EditorBoardView(controller, pieceViewList, 8, 8);
        super.resetDisplay(pieceViewList);
    }
}