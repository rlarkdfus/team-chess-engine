package ooga.controller;

public class InvalidPieceConfigException extends Exception{
  public InvalidPieceConfigException(int row, int col, String filepath, String errorKey){
    super("Error found while making piece:\"" + filepath + "\" on row: " + row + ", column: " + col + " during creation of: " + errorKey);
  }
}
