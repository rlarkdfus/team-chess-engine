package ooga.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;

public class JSONWriter {

  public void saveFile(File jsonFile, String filePath) throws IOException {
    JsonParser parser = new JsonParser();
    JSONObject jsonObject = parser.loadFile(jsonFile);
    jsonObject.remove("csv");
    jsonObject.put("csv", String.format("%s.csv", filePath));
    FileWriter writer = new FileWriter(filePath + ".json");
    writer.write(jsonObject.toString());
    writer.close();
  }
}
