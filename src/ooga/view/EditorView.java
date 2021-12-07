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
import ooga.view.boardview.PieceMenuView;
import ooga.view.ui.settingsUI.SettingsUI;

public class EditorView extends View {

  private SettingsUI settingsUI;
  private BoardView boardView;
  private PieceMenuView pieceMenu;
  private EditorControllerInterface controller;

  /**
   * creates the EditorView
   * @param controller the controller responsible for handling actions in the EditorView
   */
  public EditorView(EditorControllerInterface controller) {
    this.controller = controller;
  }

  /**
   * initializes the display of the BoardView based on piece images, special locations, and parameters
   *
   * @param pieceViewList         the list of piece images
   * @param specialLocations      the locations of special pieces (such as powerups)
   * @param bounds                the dimensions of the board
   */
  @Override
  protected BoardView initializeBoardView(List<PieceViewBuilder> pieceViewList, List<Location> specialLocations, Location bounds) {
    boardView = new EditorBoardView(controller, new ArrayList<>(), Main.DEFAULT_ROW, Main.DEFAULT_ROW);
    pieceMenu = new PieceMenuView(controller, pieceViewList, bounds.getRow(), bounds.getCol());
    return boardView;
  }

  /**
   *
   * @param viewController the viewController responsible for handling actions in the UI
   */
  @Override
  protected void initializeUI(ViewController viewController) {
    this.settingsUI = new SettingsUI(controller, viewController);
  }

  /**
   * adds the UI nodes to a GridPane
   * @return the GridPane holding the UIs created
   */
  @Override
  protected GridPane addUIs() {
    GridPane root = new GridPane();
    root.add(settingsUI, 2, 1);
    root.add(boardView, 0, 1, 1, 2);
    root.add(pieceMenu, 0, 3);
    return root;
  }

  /**
   * changes the styles of the pieces in the board in the EditorView
   * @param style the new style of the boardView pieces
   */
  @Override
  public void changePieceStyle(String style) {
    super.changePieceStyle(style);
    pieceMenu.changePieceStyle(style);
  }

  /**
   * changes the colors of the board in the EditorView
   * @param color1 the new first color used by the boardView
   * @param color2 the new second color used by the boardView
   */
  @Override
  public void changeBoardColor(Color color1, Color color2) {
    super.changeBoardColor(color1, color2);
    pieceMenu.changeColors(color1, color2);
  }
}