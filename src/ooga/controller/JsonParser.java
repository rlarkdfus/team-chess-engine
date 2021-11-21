package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.json.JSONObject;

public class JsonParser implements ParserInterface {

  public JsonParser(){
    
  }

  @Override
  public JSONObject loadFile(File file) throws FileNotFoundException {
    String currFileString = readFile(file);
    JSONObject jsonObject = buildJSON(currFileString);
    return jsonObject;
  }

  private String readFile(File currFile) throws FileNotFoundException {
    Scanner scan = new Scanner(currFile);
    String currFileString = new String();

    while (scan.hasNext()) {
      currFileString += scan.nextLine();
    }
    scan.close();
    return currFileString;
  }

  private JSONObject buildJSON(String currFileString) {
    JSONObject obj = new JSONObject(currFileString);
    return obj;
  }
}
