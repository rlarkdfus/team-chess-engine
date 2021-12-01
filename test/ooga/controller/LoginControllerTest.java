package ooga.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginControllerTest {
  private LoginController loginController;

  @BeforeEach
  void setUp() {
   loginController = new LoginController();
  }

  @Test
  void GivenValidUsernameAndPassword_ThenLoginSuccessful() throws Exception {
    boolean expected = true;

    boolean actual = loginController.isValidLogin("Luis", "password");

    assertEquals(expected, actual);
  }

  @Test
  void GivenInvalidUserName_ThenThrowException() {
    assertThrowsExactly(Exception.class, () -> loginController.isValidLogin("Juancho", "DukeLostToOSU"));
  }

  @Test
  void GivenInvalidPasswordAndValidUsername_ThenReturnFalse() throws Exception {
    boolean actual = loginController.isValidLogin("Luis", "SanchoPanza");
    boolean expected = false;
    assertEquals(expected, actual);
  }
}