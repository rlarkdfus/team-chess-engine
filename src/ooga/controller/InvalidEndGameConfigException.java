package ooga.controller;

public class InvalidEndGameConfigException extends Exception {

  public InvalidEndGameConfigException(Class<? extends Exception> reason) {
    System.out.println(reason);
  }
}
