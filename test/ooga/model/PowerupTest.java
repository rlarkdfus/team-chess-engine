package ooga.model;

import ooga.Location;
import ooga.controller.Config.*;
import ooga.model.EndConditionHandler.EndConditionRunner;
import ooga.model.Moves.MoveUtility;
import ooga.model.Powerups.PowerupInterface;
import ooga.model.Powerups.PromotePowerup;
import ooga.model.Powerups.TimerPowerup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PowerupTest {

    EndConditionRunner endConRunner;
    List<PlayerInterface> players;
    Board board;
    static Location powerupTestLocation1 = new Location(5,0);
    static Location powerupTestLocation2 = new Location(3,1);

    List<PieceInterface> allpieces;

    @BeforeEach
    void setUp()
            throws InvalidEndGameConfigException, InvalidGameConfigException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String testFile = "data/chess/defaultChess.json";
        Builder boardBuilder = new BoardBuilder(new File(testFile));
        players = boardBuilder.getInitialPlayers();
        endConRunner = boardBuilder.getEndConditionHandler();

        List<Location> promotionLocations = new ArrayList<>();
        promotionLocations.add(powerupTestLocation1);
        promotionLocations.add(powerupTestLocation2);

        PromotePowerup promotePowerup = new PromotePowerup(promotionLocations);
        TimerPowerup timerPowerup = new TimerPowerup(promotionLocations);

        List<PowerupInterface> powerups = new ArrayList<>();
        powerups.add(promotePowerup);
        powerups.add(timerPowerup);

        board = new GameBoard(players, endConRunner, powerups);
    }


    @Test
    void testPromotionSquare()
            throws FileNotFoundException, InvalidPieceConfigException {
      PieceInterface expectedWhiteQueen =  PieceBuilder.buildPiece("w","Q",new Location(5,0));
        board.movePiece(new Location(6,0),new Location(5,0)); //move white pawn
        Assertions.assertEquals(expectedWhiteQueen.getName(),MoveUtility.pieceAt(powerupTestLocation1,board.allPieces).getName());
    }

    @Test
    void testTimerSquare()
            throws FileNotFoundException, InvalidPieceConfigException {
        PlayerInterface whitePlayer = board.getPlayers().get(0);
        //Initialized time is 10:00
        whitePlayer.resetTimer();
        System.out.println("Test time"+ whitePlayer.getTimeLeft());
        board.movePiece(new Location(6,0),new Location(5,0)); //move white pawn to timer square 3,1;
        //Each move adds 5 seconds, 1 second is removed because of the time taken for the move, 60 second is added by powerup, for a total of 5-1+60,
        // 64 incremented seconds
        String expectedTime = "11:04";
        assertEquals(expectedTime, whitePlayer.getTimeLeft().getValue());

    }



}
