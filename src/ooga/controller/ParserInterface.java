package ooga.controller;

import java.io.File;
import org.json.JSONObject;

public interface ParserInterface {
  JSONObject loadFile(File file);
}
