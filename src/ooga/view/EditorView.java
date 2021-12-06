package ooga.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.GridPane;
import ooga.Location;
import ooga.Main;
import ooga.controller.Config.PieceViewBuilder;
import ooga.controller.EditorControllerInterface;
import ooga.view.boardview.EditorBoardView;
import ooga.view.boardview.PieceMenuView;
import ooga.view.ui.settingsUI.SettingsUI;

public class EditorView extends View {

  private SettingsUI settingsUI; // right
  private PieceMenuView pieceMenu;
  private EditorControllerInterface controller;
  public EditorView(EditorControllerInterface controller) {
    this.controller = controller;
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
    root.add(boardView, 0, 1, 1, 2);
    root.setVgap(10);
    root.add(pieceMenu, 0, 3);
  }

  @Override
  public void resetDisplay(List<PieceViewBuilder> pieceViewList, Location bounds) {
    this.boardView = new EditorBoardView(controller, new ArrayList<>(), Main.DEFAULT_ROW, Main.DEFAULT_COL);
    this.pieceMenu = new PieceMenuView(controller, pieceViewList, bounds.getRow(), bounds.getCol());
    super.resetDisplay(pieceViewList, bounds);
  }
}