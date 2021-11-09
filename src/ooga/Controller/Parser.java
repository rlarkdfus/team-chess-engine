package ooga.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.json.JSONObject;

public class Parser {
  File currFile;
  String currFileString;
  JSONObject currJSONObject;
  public Parser(){
    
  }
  public void loadFile(){
    selectFile();
    readFile();
    buildJSON();
  }

  private void selectFile() {
    FileChooser fileChooser = new FileChooser();
//    fileChooser.setTitle(myResources.getString("LoadSim"));
    fileChooser.getExtensionFilters().addAll(new ExtensionFilter("JSON", "*.json"));
    File selectedFile = fileChooser.showOpenDialog(null);
    if (selectedFile != null) {
      currFile = selectedFile;
      return;
    }
    currFile = null;
  }

  private void readFile() {
    Scanner scan = null;

    try {
      scan = new Scanner(currFile);
    } catch (FileNotFoundException e) {
      return;
    }

    currFileString = new String();
    while (scan.hasNext()) {
      currFileString += scan.nextLine();
    }
    scan.close();
  }

  private void buildJSON() {
    currJSONObject = new JSONObject(currFileString);
  }

}
