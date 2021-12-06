package ooga.controller.Config;

import ooga.view.util.ViewUtility;

public class PlayerNotFoundException extends Exception{

  public PlayerNotFoundException(int row, int col, String team){
    super("player \"" + team + "\" not found. Error found on row: " + row + ", column: " + col + ".");
    ViewUtility.showError(this.getClass().getSimpleName());
  }
}
