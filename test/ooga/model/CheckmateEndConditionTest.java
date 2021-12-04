//package ooga.model;
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.lang.reflect.InvocationTargetException;
//import java.util.List;
//import ooga.Location;
//import ooga.controller.BoardBuilder;
//import ooga.controller.Builder;
//import ooga.controller.InvalidEndGameConfigException;
//import ooga.controller.InvalidGameConfigException;
//import ooga.controller.InvalidPieceConfigException;
//import ooga.model.Board.GameState;
//import ooga.model.EndConditionHandler.EndConditionRunner;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//class CheckmateEndConditionTest {
//
//  EndConditionRunner endConRunner;
//  List<PlayerInterface> players;
//  Board board;
//
//  @BeforeEach
//  void setUp()
//      throws InvalidEndGameConfigException, InvalidGameConfigException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
//    String testFile = "data/chess/defaultChess.json";
//    Builder boardBuilder = new BoardBuilder(new File(testFile));
//    players = boardBuilder.getInitialPlayers();
//    endConRunner = boardBuilder.getEndConditionHandler();
//    board = new Board(players);
//    board.setEndCondition(endConRunner);
//  }
//
//  @Test
//  void testNoCheck() {
//    assertEquals(GameState.RUNNING, board.checkGameState());
//  }
//
//  @Test
//  void testWhiteWin()
//      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException, InvalidPieceConfigException {
//    board.movePiece(new Location(6,4),new Location(4,4)); //pawn
//    board.movePiece(new Location(7,3),new Location(5, 5)); //queen
//    board.movePiece(new Location(7,5),new Location(4,2)); //bishop
//    board.movePiece(new Location(5,5),new Location(1, 5)); //queen
//    System.out.println(board.toString());
//    assertEquals(GameState.CHECKMATE, board.checkGameState());
//    assertEquals("w", board.getWinner());
//  }
//
//  @Test
//  void testBlackWin()
//      throws FileNotFoundException, InvocationTargetException, InvalidPieceConfigException, NoSuchMethodException, IllegalAccessException {
//    board.movePiece(new Location(1,4),new Location(3,4)); //pawn
//    board.movePiece(new Location(0,3),new Location(2, 5)); //queen
//    board.movePiece(new Location(0,5),new Location(3,2)); //bishop
//    board.movePiece(new Location(2,5),new Location(6, 5)); //queen
//    assertEquals(GameState.CHECKMATE, board.checkGameState());
//    assertEquals("b", board.getWinner());
//  }
////
////  @Test
////  void testBlackCheck()
////      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException, InvalidPieceConfigException {
////    board.movePiece(new Location(1,4),new Location(3,4)); //pawn
////    board.movePiece(new Location(0,3),new Location(2, 5)); //queen
////    board.movePiece(new Location(2,5),new Location(6, 5)); //queen
////    assertEquals(GameState.CHECK, board.checkGameState());
////  }
////
////  @Test
////  void testWhiteCheck()
////      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException, InvalidPieceConfigException {
////    board.movePiece(new Location(6,4),new Location(4,4)); //pawn
////    board.movePiece(new Location(7,3),new Location(5, 5)); //queen
////    board.movePiece(new Location(5,5),new Location(1, 5)); //queen
////    assertEquals(GameState.CHECK, board.checkGameState());
////  }
//}
