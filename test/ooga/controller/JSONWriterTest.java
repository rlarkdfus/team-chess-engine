package ooga.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import ooga.controller.Config.JSONWriter;
import ooga.controller.Config.JsonParser;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JSONWriterTest {
  private JSONWriter jsonWriter;
  private Path directory;
  private File file;

  @TempDir
  private Path predirectory;

  @BeforeEach
  void setUp() {
    jsonWriter = new JSONWriter();
    directory = predirectory.resolve("testfile.json");
  }

  @Test
  void testSavedFileSavesCorrectly() throws IOException {
    JSONObject jsonData = JsonParser.loadFile(new File("data/chess/defaultChess.json"));
    jsonWriter.saveFile(jsonData, directory.toString());
    JSONObject jsonObject = JsonParser.loadFile(new File(directory.toString() + ".json"));
    String expected = "chess";
    String actual = jsonObject.getString("type");
    assertEquals(expected, actual);
  }
}