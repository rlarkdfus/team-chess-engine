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
  private JsonParser jsonParser;
  private Path directory;
  private File file;

  @TempDir
  private Path predirectory;

  @BeforeEach
  void setUp() {
    jsonWriter = new JSONWriter();
    jsonParser = new JsonParser();
    directory = predirectory.resolve("testfile.json");
  }

  @Test
  void testSavedFileSavesCorrectly() throws IOException {
    jsonWriter.saveFile(new File("data/chess/defaultChess.json"), directory.toString());
    JSONObject jsonObject = jsonParser.loadFile(new File(directory.toString() + ".json"));
    String expected = "chess";
    String actual = jsonObject.getString("type");
    assertEquals(expected, actual);
  }
}