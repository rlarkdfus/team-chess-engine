package ooga.controller.Config;

import ooga.view.util.ViewUtility;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Albert
 * purpose - this class builds JSONObjects from a given file object. A JSONObject works similar to a map
 * where in order to access certain fields, the user calls jsonobject.get()
 * assumptions - we assume that the file is a json file. If this isn't the case, then we throw FileNotFoundException
 * dependencies - this class depends on the JSONObject package
 * To use - The user will call loadFile() with a file object. this will return a JSONObject
 */
public class JsonParser {

  private static final String FILE_NOT_FOUND = "fileNotFound";

  /**
   * takes in a json file and then out puts a jsonobject object.
   *
   * @param file - file object of a json file
   * @return a jsonobject object
   */
  public static JSONObject loadFile(File file) {
    String currFileString = null;
    try {
      currFileString = readFile(file);
    } catch (FileNotFoundException e) {
      ViewUtility.showError(FILE_NOT_FOUND);
    }
    return buildJSON(currFileString);
  }

  /**
   * reads each line of the file and adds it to a string. The string is then used to build the jsonobject
   */
  private static String readFile(File currFile) throws FileNotFoundException {
    Scanner scan = new Scanner(currFile);
    String currFileString = "";
    while (scan.hasNext()) {
      currFileString += (scan.nextLine());
    }
    scan.close();
    return currFileString;
  }

  private static JSONObject buildJSON(String currFileString) {
    return new JSONObject(currFileString);
  }
}
