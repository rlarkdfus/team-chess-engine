package ooga.controller.Config;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Authors Luis
 * purpose - this class takes a path to a csv file and creates a list of list of Strings where each string represents
 *  one piece, and the row and column indices represent the piece's location.
 * assumptions - we assume that the filepath is a csv. If the file isn't a valid csv file,
 *  then we throw a CsvException that get caught and sent to the view.
 * dependencies - this class depends on java.io.FileReader and opencsv.CsvReader packages
 * To use - The user will call getInitialLocations() with a filepath to a csv. This function will return
 *  a list of lists of Strings.
 */
public class LocationParser {

  /**
   * This method takes in a filepath to a csv file and outputs a list of lists of Strings
   * @param filePath - filepath to a csv file
   * @return a list of lists of Strings where the indices of each element corresponds to the location
   *  of the element
   * @throws CsvException - if the filepath doesn't point to a valid csv
   */
  public static List<List<String>> getInitialLocations(String filePath) throws CsvException {
    return parseLocations(filePath);
  }

  /**
   * populates the initiallocations list with the data based on the current row and column
   */
  private static List<List<String>> parseLocations(String filePath) throws CsvException {
    List<List<String>> initialLocations = new ArrayList<>();
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
    return initialLocations;
  }
}
