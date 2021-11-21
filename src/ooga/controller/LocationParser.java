package ooga.controller;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.opencsv.CSVReader;

public class LocationParser {
  private List<List<String>> initialLocations;

  public LocationParser() {
    initialLocations = new ArrayList<>();
  }

  public List<List<String>> getInitialLocations(String filePath) throws Exception {
    parseLocations(filePath);
    return initialLocations;
  }

  private void parseLocations(String filePath) throws Exception {
    clearLocations();
    try {
      FileReader fileReader = new FileReader(filePath);
      CSVReader csvReader = new CSVReader(fileReader);

      String[] csvLine;
      while ((csvLine = csvReader.readNext()) != null) {
        List<String> row = Arrays.asList(csvLine);
        initialLocations.add(row);
      }
    }
    catch (Exception e) {
      throw new Exception("Invalid CSV file passed in");
    }
  }
  private void clearLocations() {
    initialLocations.clear();
  }
}
