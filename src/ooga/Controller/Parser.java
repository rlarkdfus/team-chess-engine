package ooga.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.json.JSONObject;

public class Parser {

  public Parser(){
    
  }
  public void loadFile(){
    File currFile = selectFile();
    String currFileString = readFile(currFile);
    JSONObject currJSONObject = buildJSON(currFileString);
  }

  private File selectFile() {
    FileChooser fileChooser = new FileChooser();
//    fileChooser.setTitle(myResources.getString("LoadJSONFile"));
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON", "*.json"));
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
      return selectedFile;
    }
    System.out.println("no file selected");
    return null;
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
