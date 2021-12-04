package ooga.controller.Config;

public class CsvException extends Exception{
  public CsvException(int row){
    super("Error found in CSV file on row: " + row);
  }
}
