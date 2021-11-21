package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.json.JSONObject;

public class JsonParser implements ParserInterface {

  public JsonParser() {
  }

  @Override
  public JSONObject loadFile(File file) {
    String currFileString = readFile(file);
    return buildJSON(currFileString);
  }

  private String readFile(File currFile) {
    Scanner scan;
    StringBuilder currFileString;
    try {
      scan = new Scanner(currFile);
    } catch (FileNotFoundException e) {
      return null;
    }

    currFileString = new StringBuilder();
    while (scan.hasNext()) {
      currFileString.append(scan.nextLine());
    }
    scan.close();
    return currFileString.toString();
  }

  private JSONObject buildJSON(String currFileString) {
    return new JSONObject(currFileString);
  }
}
