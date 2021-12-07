package ooga.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import ooga.Location;
import ooga.Main;
import ooga.controller.Config.PieceViewBuilder;
import ooga.controller.EditorControllerInterface;
import ooga.view.boardview.BoardView;
import ooga.view.boardview.EditorBoardView;
import ooga.view.boardview.GameBoardView;
import ooga.view.boardview.PieceMenuView;
import ooga.view.ui.settingsUI.SettingsUI;

public class EditorView extends View {

  private SettingsUI settingsUI; // right
  private BoardView boardView;
  private PieceMenuView pieceMenu;
  private EditorControllerInterface controller;

  public EditorView(EditorControllerInterface controller) {
    this.controller = controller;
  }

  @Override
  protected BoardView initializeBoardView(List<PieceViewBuilder> pieceViewList, List<Location> specialLocations, Location bounds) {
    boardView = new EditorBoardView(controller, new ArrayList<>(), Main.DEFAULT_ROW, Main.DEFAULT_ROW);
    pieceMenu = new PieceMenuView(controller, pieceViewList, bounds.getRow(), bounds.getCol());
    return boardView;
  }

  @Override
  protected void initializeUI(ViewController viewController) {
    this.settingsUI = new SettingsUI(controller, viewController);
  }

  @Override
  protected GridPane addUIs() {
    GridPane root = new GridPane();
    root.add(settingsUI, 2, 1);
    root.add(boardView, 0, 1, 1, 2);
    root.add(pieceMenu, 0, 3);
    return root;
  }

  @Override
  public void changePieceStyle(String style) {
    super.changePieceStyle(style);
    pieceMenu.changePieceStyle(style);
  }

  @Override
  public void changeBoardColor(Color color1, Color color2) {
    super.changeBoardColor(color1, color2);
    pieceMenu.changeColors(color1, color2);
  }
}