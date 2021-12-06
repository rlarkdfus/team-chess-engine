package ooga.controller.Config;

import ooga.view.util.ViewUtility;

public class InvalidGameConfigException extends Exception {

  public InvalidGameConfigException(String message) {
    super(message);
    ViewUtility.showError(this.getClass().getSimpleName());
  }

}
