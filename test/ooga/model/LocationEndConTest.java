package ooga.model;

import java.util.List;
import java.util.Map;
import ooga.controller.InvalidGameConfigException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LocationEndConTest {
  LocationEndConditionHandler l;
  @BeforeEach
  void setUp() {
    l = new LocationEndConditionHandler();
    try {
      l.setArgs(Map.of("pieceType", List.of("P","P"),
          "location", List.of("1,0","2,0")), List.of(new Player("b")));
    } catch (InvalidGameConfigException e) {
    }
  }

  @Test
  void testExecuteMove() {
  }

}
