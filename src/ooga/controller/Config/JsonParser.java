package ooga.controller.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.json.JSONObject;

/**
 * @Authors Albert
 * purpose - this class builds JSONObjects from a given file object. A JSONObject works similar to a map
 *  where in order to access certain fields, the user calls jsonobject.get()
 * assumptions - we assume that the file is a json file. If this isn't the case, then we throw FileNotFoundException
 * dependencies - this class depends on the JSONObject package
 * To use - The user will call loadFile() with a file object. this will return a JSONObject
 */
public class JsonParser {

  /**
   * takes in a json file and then out puts a jsonobject object.
   * @param file - file object of a json file
   * @return a jsonobject object
   * @throws FileNotFoundException - if the input file isn't a valid json file, this exception is thrown
   */
  public static JSONObject loadFile(File file) throws FileNotFoundException {
    String currFileString = readFile(file);
    return buildJSON(currFileString);
  }

  /**
   * reads each line of the file and adds it to a string. The string is then used to build the jsonobject
   */
  private static String readFile(File currFile) throws FileNotFoundException {
    Scanner scan = new Scanner(currFile);
    String currFileString = new String();
    while (scan.hasNext()) {
      currFileString += (scan.nextLine());
    }
    scan.close();
    return currFileString.toString();
  }

  private static JSONObject buildJSON(String currFileString) {
    return new JSONObject(currFileString);
  }
}
