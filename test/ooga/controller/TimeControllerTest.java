package ooga.controller;

import ooga.model.Player;
import ooga.model.PlayerInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TimeControllerTest {

    public static final int INITIAL_TIME = 10;
    public static final int INITIAL_INCREMENT = 5;

    TimeController timeController;
    List<PlayerInterface> players;

    @BeforeEach
    void setUp() {
        timeController = new TimeController(INITIAL_TIME, INITIAL_INCREMENT);
        players = List.of(new Player("w"), new Player("b"));
    }

    @Test
    void testConfigTimers() {
        String expected = "10:00";
        timeController.configTimers(players);
        timeController.resetTimers(players);
        for (PlayerInterface player : players) {
            assertEquals(expected, player.getTimeLeft().getValue());
        }
    }

    @Test
    void testSetValidInitialTime() {
        int minutes = 20;
        String expected = "20:00";
        timeController.setInitialTime(minutes);
        timeController.configTimers(players);
        timeController.resetTimers(players);
        for (PlayerInterface player : players) {
            assertEquals(expected, player.getTimeLeft().getValue());
        }
    }

    @Test
    void testSetInvalidInitialTime() {
        int minutes = -1;
        String expected = "00:00";
        timeController.setInitialTime(minutes);
        timeController.configTimers(players);
        timeController.resetTimers(players);
        for (PlayerInterface player : players) {
            assertEquals(expected, player.getTimeLeft().getValue());
        }
    }

    @Test
    void testSetValidIncrement() {
        int seconds = 9;
        String expected = "10:09";
        timeController.setIncrement(seconds);
        timeController.configTimers(players);
        timeController.resetTimers(players);
        for (PlayerInterface player : players) {
            player.incrementTime();
            assertEquals(expected, player.getTimeLeft().getValue());
        }
    }

    @Test
    void testSetInvalidIncrement() {
        int seconds = -1;
        String expected = "10:00";
        timeController.setIncrement(seconds);
        timeController.configTimers(players);
        timeController.resetTimers(players);
        for (PlayerInterface player : players) {
            player.incrementTime();
            assertEquals(expected, player.getTimeLeft().getValue());
        }
    }
}
