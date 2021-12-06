package ooga.controller.Config;

import ooga.view.util.ViewUtility;

public class InvalidEndGameConfigException extends Exception {

  public InvalidEndGameConfigException(Class<? extends Exception> reason) {
    System.out.println(reason);
  }

  public InvalidEndGameConfigException(String reason) {
    System.out.println(reason);
    ViewUtility.showError(this.getClass().getSimpleName());
  }
}
