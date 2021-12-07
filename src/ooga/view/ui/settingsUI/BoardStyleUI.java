package ooga.view.ui.settingsUI;

import javafx.scene.control.ColorPicker;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import ooga.view.ViewController;
import ooga.view.ui.UIHelper;
import ooga.view.ui.UIInterface;
import ooga.view.util.ViewUtility;

import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class BoardStyleUI extends GridPane implements UIInterface {

  public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("ooga/view/ui/settingsUI/BoardStyleUI");

  public static final List<String> LANGUAGES = ViewController.LANGUAGES;
  public static final List<String> THEMES = ViewController.THEMES;
  public static final List<String> PIECE_STYLES = ViewController.PIECE_STYLES;

  public static final Color DEFAULT_COLOR1 = ViewController.DEFAULT_COLOR1;
  public static final Color DEFAULT_COLOR2 = ViewController.DEFAULT_COLOR2;

  public static final String COLOR_PICKER1 = "board_color1";
  public static final String COLOR_PICKER2 = "board_color2";

  public static final String BOARD_COLOR_LABEL = "board_color_label";
  public static final String PIECE_STYLE_LABEL = "piece_variation_label";
  public static final String PIECE_MENU = "piece_variation";
  public static final String LANGUAGE_LABEL = "language_label";
  public static final String LANGUAGE_MENU = "language_variation";
  public static final String THEME_LABEL = "theme_label";
  public static final String THEME_MENU = "theme_variation";

  public final Map<String, Consumer<String>> MENU_BEHAVIOR = Map.of(
          PIECE_MENU, e -> changePieceStyle(e),
          LANGUAGE_MENU, e -> changeLanguage(e),
          THEME_MENU, e -> changeTheme(e)
  );

  private ViewController viewController;
  private final ViewUtility viewUtility;
  private ColorPicker colorPicker1;
  private ColorPicker colorPicker2;
  public final Map<String, Consumer<Color>> COLORPICKER_BEHAVIOR = Map.of(
          COLOR_PICKER1, color -> viewController.handleChangeBoardColor(color, colorPicker2.getValue()),
          COLOR_PICKER2, color -> viewController.handleChangeBoardColor(colorPicker1.getValue(), color)
  );

  public BoardStyleUI(ViewController viewController) {
    this.viewController = viewController;
    this.viewUtility = new ViewUtility();
    this.getStyleClass().add("SettingsUI");
    makeColorPickers();
    createUI();
  }

  private void makeColorPickers() {
    colorPicker1 = viewUtility.makeColorPicker(COLOR_PICKER1, DEFAULT_COLOR1, COLORPICKER_BEHAVIOR.get(COLOR_PICKER1));
    colorPicker2 = viewUtility.makeColorPicker(COLOR_PICKER2, DEFAULT_COLOR2, COLORPICKER_BEHAVIOR.get(COLOR_PICKER2));
  }

  @Override
  public void createUI() {
    UIHelper.setResourceBundle(RESOURCE_BUNDLE);
    this.add(viewUtility.makeLabel(BOARD_COLOR_LABEL), UIHelper.getCol(BOARD_COLOR_LABEL), UIHelper.getRow(BOARD_COLOR_LABEL));
    this.add(colorPicker1, UIHelper.getCol(COLOR_PICKER1), UIHelper.getRow(COLOR_PICKER1));
    this.add(colorPicker2, UIHelper.getCol(COLOR_PICKER2), UIHelper.getRow(COLOR_PICKER2));
    this.add(viewUtility.makeLabel(PIECE_STYLE_LABEL), UIHelper.getCol(PIECE_STYLE_LABEL), UIHelper.getRow(PIECE_STYLE_LABEL));
    this.add(viewUtility.makeMenu(PIECE_MENU, PIECE_STYLES, MENU_BEHAVIOR.get(PIECE_MENU)), UIHelper.getCol(PIECE_MENU), UIHelper.getRow(PIECE_MENU));

    this.add(viewUtility.makeLabel(LANGUAGE_LABEL), UIHelper.getCol(LANGUAGE_LABEL), UIHelper.getRow(LANGUAGE_LABEL));
    this.add(viewUtility.makeMenu(LANGUAGE_MENU, LANGUAGES, MENU_BEHAVIOR.get(LANGUAGE_MENU)), UIHelper.getCol(LANGUAGE_MENU), UIHelper.getRow(LANGUAGE_MENU));

    this.add(viewUtility.makeLabel(THEME_LABEL), UIHelper.getCol(THEME_LABEL), UIHelper.getRow(THEME_LABEL));
    this.add(viewUtility.makeMenu(THEME_MENU, THEMES, MENU_BEHAVIOR.get(THEME_MENU)), UIHelper.getCol(THEME_MENU), UIHelper.getRow(THEME_MENU));
  }

  private void changePieceStyle(String style) {
    viewController.handleChangePieceStyle(style);
  }

  private void changeLanguage(String language) {
    viewController.changeLanguage(language);
  }

  private void changeTheme(String theme) {
    viewController.changeTheme(theme);
  }
}
