package ooga.controller.Config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;

/**
 * Purpose: JSONWriter is used to be able to save a JSONFile to a specified file path. This will
 * allow for edited preferences of the board and the locations of pieces to be saved to a data file
 * allowing for the continuation of a saved game at a later date.
 * Assumptions: We assume that a properly formatted JSON is passed to saveFile() as well as a
 * filePath that exists on the local computer. We also assume the LocationWriter will effectively
 * write a csv file detailing the states of the pieces.
 * Dependencies: JsonParser
 */
public class JSONWriter {
  public static final String CSV = "csv";
  public static final String CSV_STRING_FORMAT = "%s.csv";
  public static final String JSON_EXTENSION = ".json";

  /**
   * saveFile() saves a jsonFile to a specified file path. This will allow the user of the
   * application to get a file with the game settings saved to their local computer if they wish
   * to start the game again at a later date.
   * @param jsonFile JSONFile containing the settings of the game
   * @param filePath The path where the JSON will be saved to
   * @throws IOException If there is an issue with the path to which the file is saved or creating a
   * FileWriter, then this exception will be thrown
   */
  public void saveFile(File jsonFile, String filePath) throws IOException {
    JsonParser parser = new JsonParser();
    JSONObject jsonObject = parser.loadFile(jsonFile);
    jsonObject.remove(CSV);
    jsonObject.put(CSV, String.format(CSV_STRING_FORMAT, filePath));
    FileWriter writer = new FileWriter(filePath + JSON_EXTENSION);
    writer.write(jsonObject.toString());
    writer.close();
  }
}
