package ooga.controller.Config;

import ooga.view.util.ViewUtility;

public class InvalidPowerupsConfigException extends Exception {

  public InvalidPowerupsConfigException(String outOfBoundsError) {
    super(outOfBoundsError);
    ViewUtility.showError(this.getClass().getSimpleName());
  }
}
