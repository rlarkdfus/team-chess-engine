package ooga.controller.Config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;

public class JSONWriter {
  public static final String CSV = "csv";
  public static final String CSV_STRING_FORMAT = "%s.csv";
  public static final String JSON_EXTENSION = ".json";

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
