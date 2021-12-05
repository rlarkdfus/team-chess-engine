package ooga.controller.Config;

public class InvalidPowerupsConfigException extends Exception {

  public InvalidPowerupsConfigException() {}

  public InvalidPowerupsConfigException(String outOfBoundsError) {
    super(outOfBoundsError);
  }
}
