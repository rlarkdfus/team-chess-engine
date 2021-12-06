//package ooga.controller;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//class LoginControllerTest {
//  private LoginController loginController;
//
//  @BeforeEach
//  void setUp() {
//    loginController = new LoginController();
//  }
//
//  @Test
//  void GivenValidUsernameAndPassword_ThenLoginSuccessful() throws Exception {
//    boolean expected = true;
//    assertEquals(expected, loginController.handleLoginAttempt("Luis", "password", "Luis", "password"));
//  }
//
//  @Test
//  void GivenInvalidUserName_ThenThrowException() {
//    assertThrowsExactly(Exception.class, () -> loginController.handleLoginAttempt("Juancho", "DukeLostToOSU", "Yoyo", "Juan"));
//  }
//
//  @Test
//  void GivenInvalidPasswordAndValidUsername_ThenReturnFalse() throws Exception {
//    boolean expected = false;
//    assertEquals(expected, loginController.handleLoginAttempt("Luis", "SanchoPanza", "Luis", "password"));
//  }
//}