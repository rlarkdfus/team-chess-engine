package ooga.controller;
import com.opencsv.CSVReader;
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

  public List<List<String>> getInitialLocations(String filePath) throws CsvException {
    parseLocations(filePath);
    return initialLocations;
  }

  private void parseLocations(String filePath) throws CsvException {
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
      throw new CsvException(initialLocations.size());
    }
  }
  private void clearLocations() {
    initialLocations.clear();
  }
}
