package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.json.JSONObject;

public class JsonParser implements ParserInterface {

  public JsonParser(){
    
  }

  @Override
  public JSONObject loadFile(File file){
    String currFileString = readFile(file);
    JSONObject jsonObject = buildJSON(currFileString);
    return jsonObject;
  }

  private String readFile(File currFile) {
    Scanner scan = null;
    String currFileString;
    try {
      scan = new Scanner(currFile);
    } catch (FileNotFoundException e) {
      return null;
    }

    currFileString = new String();
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
