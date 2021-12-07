package ooga.view.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UIHelper {

  private static final int COL = 0;
  private static final int ROW = 1;
  private static final int COLSPAN = 3;
  private static final int ROWSPAN = 4;
  
  private static ResourceBundle resource;
  
  
  public static int getRow(String key) {
    return getkey(key).get(ROW);
  }
  
  public static int getCol(String key) {
    return getkey(key).get(COL);
  }

  public static int getColSpan(String key) {
    return getkey(key).get(COLSPAN);
  }

  public static int getRowSpan(String key) {
    return getkey(key).get(ROWSPAN);
  }

  private static List<Integer> getkey(String key) {
    List<Integer> coordinate = new ArrayList<>();
    String coords = resource.getString(key);
    for(String s : coords.split(",")) {
      coordinate.add(Integer.parseInt(s));
    }
    return coordinate;
  }
  
  public static void setResourceBundle(ResourceBundle resourceBundle) {
    resource = resourceBundle;
  }
}
