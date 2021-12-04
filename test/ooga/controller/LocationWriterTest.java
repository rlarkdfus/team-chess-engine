package ooga.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import ooga.controller.Config.BoardBuilder;
import ooga.controller.Config.Builder;
import ooga.controller.Config.JsonParser;
import ooga.controller.Config.LocationParser;
import ooga.controller.Config.LocationWriter;
import ooga.model.PlayerInterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class LocationWriterTest {
  private LocationWriter locWriter;
  private LocationParser locationParser;
  private JsonParser jsonParser;
  private Builder boardBuilder;
  private File file;
  private Path directory;
  @TempDir
  public Path predirectory;

  @BeforeEach
  void setUp() throws Exception {
    locWriter = new LocationWriter();
    locationParser = new LocationParser();
    jsonParser = new JsonParser();
    file = new File("data/chess/defaultChess.json");

    boardBuilder = new BoardBuilder(file);

    directory = predirectory.resolve("testfile1.csv");
  }


  @Test
  void givenDefaultBoard_WhenWriteToFile_ThenValuesAreCorrect()
      throws Exception {

    List<PlayerInterface> players = boardBuilder.getInitialPlayers();
    locWriter.saveCSV(directory.toString(), players);

    File expectedFile = new File("data/chess/locations/chess.csv");
    assertLinesMatch(Files.readAllLines(expectedFile.toPath()), Files.readAllLines(directory));
  }

}