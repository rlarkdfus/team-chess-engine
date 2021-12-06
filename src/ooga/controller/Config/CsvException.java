package ooga.controller.Config;

import ooga.view.util.ViewUtility;

public class CsvException extends Exception{
  public CsvException(int row){
    super("Error found in CSV file on row: " + row);
    ViewUtility.showError(this.getClass().getSimpleName());
  }
}
