package ooga.view;

import javafx.scene.layout.GridPane;
import ooga.controller.Config.PieceViewBuilder;
import ooga.controller.Controller;
import ooga.view.boardview.EditorBoardView;
import ooga.view.boardview.PieceMenuView;
import ooga.view.ui.settingsUI.SettingsUI;

import java.util.ArrayList;
import java.util.List;

public class EditorView extends View {

  private SettingsUI settingsUI; // right
  private PieceMenuView pieceMenu;

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
//        root.add(settingsUI, 2, 1);
    root.add(boardView, 0, 1, 1, 2);
    root.setVgap(10);
    root.add(pieceMenu, 0, 3);
  }

  @Override
  public void resetDisplay(List<PieceViewBuilder> pieceViewList) {
    this.boardView = new EditorBoardView(controller, new ArrayList<>(), 8, 8);
    this.pieceMenu = new PieceMenuView(controller, pieceViewList, 2, 6);
    super.resetDisplay(pieceViewList);
  }
}