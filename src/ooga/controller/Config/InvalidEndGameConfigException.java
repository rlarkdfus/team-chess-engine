package ooga.controller.Config;

public class InvalidEndGameConfigException extends Exception {

  public InvalidEndGameConfigException(Class<? extends Exception> reason) {
    System.out.println(reason);
  }

  public InvalidEndGameConfigException(String reason) {
    System.out.println(reason);
  }
}
