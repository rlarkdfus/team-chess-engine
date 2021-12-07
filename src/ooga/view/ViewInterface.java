package ooga.view;

import javafx.scene.paint.Color;
import ooga.Location;
import ooga.controller.Config.PieceViewBuilder;
import ooga.view.util.ViewUtility;

import java.util.List;

public interface ViewInterface {
  /**
   * Updates position of pieces on the board
   */
  void updateDisplay(List<PieceViewBuilder> pieceViewList);

  /**
   * initializes the display of the View based on piece images, special locations, and parameters
   *
   * @param pieceViewList         the list of piece images
   * @param specialPieceLocations the locations of special pieces (such as powerups)
   * @param bounds                the dimensions of the board
   */
  void initializeDisplay(List<PieceViewBuilder> pieceViewList, List<Location> specialPieceLocations, Location bounds);

  /**
   * changes the color of the board
   *
   * @param color1 the new first color used by the board
   * @param color2 the new second color used by the board
   */
  void changeBoardColor(Color color1, Color color2);

  /**
   * changes the style of pieces in the board
   *
   * @param style the new style of the board pieces
   */
  void changePieceStyle(String style);

  /**
   * changes the theme of the UI
   *
   * @param theme the new theme
   */
  void changeTheme(String theme);

  /**
   * changes the language in the UI
   *
   * @param language the new language
   */
  void changeLanguage(String language);

  static void showError(String message) {
    ViewUtility.showError(message);
  }
}
