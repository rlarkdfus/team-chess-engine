package ooga.controller.Config;

import ooga.Location;

public class InvalidPieceConfigException extends Exception{
  public InvalidPieceConfigException(Location location, String filepath, String errorKey){
    super("Error found while making piece:\"" + filepath + "\" on row: " + location.getRow() + ", column: " + location.getCol() + " during creation of: " + errorKey);
  }
}
