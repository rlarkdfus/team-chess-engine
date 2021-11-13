package ooga.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ControllerTest {

  private Controller c;
  @BeforeEach
  void setUp() {
    c = new Controller();
  }

  @Test
  void testControllerConstructor(){
    assertTrue(c != null);
  }

}