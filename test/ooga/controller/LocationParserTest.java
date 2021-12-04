package ooga.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import ooga.controller.Config.LocationParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationParserTest {

  private LocationParser locationParser;
  private String filePath;

  @BeforeEach
  void setUp() {
    locationParser =  new LocationParser();
    filePath = "./data/chess/locations/chess.csv";
  }

  @Test
  void testLocationParserWritesCorrectly() throws Exception {
    List<List<String>> initialPositions = locationParser.getInitialLocations(filePath);
    String expected = "b_B";
    String test =  initialPositions.get(0).get(2);
    assertEquals(expected, test);
  }

  @Test
  void testZerosWrittenAtEmptyPositions() throws Exception {
    List<List<String>> initialPositions = locationParser.getInitialLocations(filePath);
    String expected = "0";
    String test =  initialPositions.get(4).get(3);
    assertEquals(expected, test);
  }
}