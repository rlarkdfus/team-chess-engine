package ooga.model;

import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;


class BoardBuilderTest extends DukeApplicationTest {
  BoardBuilder b;
  @BeforeEach
  void setUp(){
    b = new BoardBuilder();
  }

  @Test
  void testBuilder()throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
  }
}
