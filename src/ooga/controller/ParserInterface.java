package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import org.json.JSONObject;

public interface ParserInterface {
  JSONObject loadFile(File file) throws FileNotFoundException;
}
