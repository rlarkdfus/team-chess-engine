package ooga.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class MoveTimerTest {

    public static final int INITIAL_TIME = 10;
    public static final int INITIAL_INCREMENT = 5;

    private TimerInterface moveTimer;

    @BeforeEach
    void setUp() {
        moveTimer = new MoveTimer(INITIAL_TIME, INITIAL_INCREMENT);
    }

    @Test
    void testResetWithNewInitialTime() {
        String expected = "20:00";
        int minutes = 20;
        moveTimer.setInitialTime(minutes);
        moveTimer.reset();
        assertEquals(expected, moveTimer.getTimeLeft().getValue());
    }

    @Test
    void testIsOutOfTime() {
        int minutes = 0;
        moveTimer.setInitialTime(minutes);
        moveTimer.reset();
        assertTrue(moveTimer.isOutOfTime());
    }

    @Test
    void testNotOutOfTime() {
        int minutes = 5;
        moveTimer.setInitialTime(minutes);
        moveTimer.reset();
        assertFalse(moveTimer.isOutOfTime());
    }

    @Test
    void testInvalidInitialTime() {
        int minutes = -1;
        moveTimer.setInitialTime(minutes);
        moveTimer.reset();
        assertTrue(moveTimer.isOutOfTime());
    }

    @Test
    void testInvalidIncrement() {
        int seconds = -1;
        String expected = "10:00";
        moveTimer.setIncrement(seconds);
        moveTimer.reset();
        moveTimer.incrementTime();
        assertEquals(expected, moveTimer.getTimeLeft().getValue());
    }

    @Test
    void testValidIncrementSingle() {
        int seconds = 7;
        String expected = "10:07";
        moveTimer.setIncrement(seconds);
        moveTimer.reset();
        moveTimer.incrementTime();
        assertEquals(expected, moveTimer.getTimeLeft().getValue());
    }

    @Test
    void testValidIncrementMultiple() {
        int seconds = 7;
        int numIncrements = 8;
        String expected = "10:56";
        moveTimer.setIncrement(seconds);
        moveTimer.reset();
        for (int i = 0; i < numIncrements; i++) {
            moveTimer.incrementTime();
        }
        assertEquals(expected, moveTimer.getTimeLeft().getValue());
    }
}
