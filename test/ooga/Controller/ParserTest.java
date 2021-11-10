package ooga.Controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ParserTest {
  Parser p;
  @BeforeEach
  void setUp(){
    p = new Parser();
  }
  @Test
  void testSelectFile()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    Method m = p.getClass().getDeclaredMethod("selectFile");
    m.setAccessible(true);
    m.invoke(p);


  }
  @Test
  void testReadFile(){

  }
  @Test
  void testBuildJson(){

  }
}