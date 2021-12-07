package ooga.view;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.*;

/**
 * Handles changes in the view that do not involve the model
 */
public class ViewController {
  public static final ResourceBundle STYLE_BUNDLE = View.STYLE_BUNDLE;

  public static final List<String> LANGUAGES = Arrays.stream(STYLE_BUNDLE.getString("languages").split(",")).toList();
  public static final List<String> THEMES = Arrays.stream(STYLE_BUNDLE.getString("themes").split(",")).toList();
  public static final List<String> PIECE_STYLES = Arrays.stream(STYLE_BUNDLE.getString("piece_styles").split(",")).toList();

  public static final String DEFAULT_PIECE_STYLE = STYLE_BUNDLE.getString("default_style");
  public static final String DEFAULT_COLOR = View.DEFAULT_THEME;
  public static final Color DEFAULT_COLOR1 = Color.web(STYLE_BUNDLE.getString(DEFAULT_COLOR + "1"));
  public static final Color DEFAULT_COLOR2 = Color.web(STYLE_BUNDLE.getString(DEFAULT_COLOR + "2"));

  public static final int SQUARE_WIDTH = Integer.parseInt(STYLE_BUNDLE.getString("width"));
  public static final int SQUARE_HEIGHT = Integer.parseInt(STYLE_BUNDLE.getString("height"));

  private final Map<String, List<Color>> THEME_COLORMAP;

  private final ViewInterface view;

  /**
   * creates a ViewController object
   *
   * @param view the ViewInterface representing the UI from which actions are handled
   */
  public ViewController(ViewInterface view) {
    this.view = view;
    THEME_COLORMAP = getThemeColormap();
  }

  /**
   * calls on the view to change the board color
   *
   * @param color1 the new first board square color
   * @param color2 the new second board square color
   */
  public void handleChangeBoardColor(Color color1, Color color2) {
    view.changeBoardColor(color1, color2);
  }

  /**
   * calls on the view to change the piece style
   *
   * @param style the new piece style
   */
  public void handleChangePieceStyle(String style) {
    view.changePieceStyle(style);
  }

  /**
   * calls on the view to change the language
   *
   * @param language the new language
   */
  public void changeLanguage(String language) {
    view.changeLanguage(language);
  }

  /**
   * calls on the view to change the theme
   *
   * @param theme the new theme
   */
  public void changeTheme(String theme) {
    view.changeTheme(theme);
    List<Color> colors = THEME_COLORMAP.get(theme);
    handleChangeBoardColor(colors.get(0), colors.get(1));
  }

  private static Map<String, List<Color>> getThemeColormap() {
    Map<String, List<Color>> themeMap = new HashMap<>();
    for (String theme : THEMES) {
      List<Color> colors = List.of(Color.web(STYLE_BUNDLE.getString(theme + "1")), Color.web(STYLE_BUNDLE.getString(theme + "2")));
      themeMap.put(theme, colors);
    }
    return themeMap;
  }

  public void handleKeyPress(KeyCode k) {
    if (k == KeyCode.C) {
      ((GameView) view).toggleCheatsVisible();
    }
  }
}
