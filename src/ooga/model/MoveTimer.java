package ooga.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Timer;
import java.util.TimerTask;

public class MoveTimer implements TimerInterface {
    // timer parameters
    public static final int DELAY = 1000;
    public static final int PERIOD = 1000;

    private StringProperty timeLeft;
    private int seconds;
    private int initialTime;
    private int increment;

    private boolean isPlaying;
    private boolean isPaused;

    private Timer timer;

    public MoveTimer() {
        this(0, 0);
    }

    public MoveTimer(int initialTimeMinutes, int initialIncrementSeconds) {
        setInitialTime(initialTimeMinutes);
        setIncrement(initialIncrementSeconds);
        seconds = initialTime;
        timeLeft = new SimpleStringProperty(timeToString(seconds));
        isPlaying = false;
        timer = new Timer();
    }

    @Override
    public StringProperty getTimeLeft() {
        return timeLeft;
    }

    @Override
    public boolean isOutOfTime() {
        return seconds == 0;
    }

    @Override
    public void setInitialTime(int minutes) {
        initialTime = Math.max(60 * minutes, 0);
    }

    @Override
    public void setIncrement(int seconds) {
        increment = Math.max(seconds, 0);
    }

    /**
     * toggles the state of the timer (on to off, and vice versa)
     */
    @Override
    public void toggle() {
        if (isPlaying) {
            pause();
        } else {
            start();
        }
    }

    private void start() {
        if (isPlaying) {
            return;
        }
        isPlaying = true;
        isPaused = false;
        timer = new Timer();
        TimerTask timerTask = makeTimerTask();
        timer.scheduleAtFixedRate(timerTask, DELAY, PERIOD);
        timeLeft.setValue(timeToString(seconds));
        timerTask.run();
    }

    private void pause() {
        if (isPaused) {
            return;
        }
        timer.cancel();
        isPaused = true;
        isPlaying = false;
    }

    @Override
    public void reset() {
        timer.cancel();
        isPaused = true;
        isPlaying = false;
        seconds = initialTime;
        timeLeft.setValue(timeToString(seconds));
    }

    private TimerTask makeTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                decrementTime();
            }
        };
    }

    /**
     * decrements the amount of time by one second
     */
    private void decrementTime() {
        if (seconds <= 0) {
            timer.cancel();
            return;
        }
        seconds--;
        timeLeft.setValue(timeToString(seconds));
    }

    /**
     * increments the amount of time by the increment value
     */
    @Override
    public void incrementTimeUserInterface() {
        incrementTime(increment);
//        seconds += increment;
//        timeLeft.setValue(timeToString(seconds));
    }

    public void incrementTime(int specifiedTime){
        if (seconds == 0){
            return;
        }
        seconds+=specifiedTime;
        timeLeft.setValue(timeToString(seconds));
    }

    /**
     * converts time in seconds to "mm:ss"
     *
     * @param seconds the time in seconds (maximum of 3599)
     * @return the String representation of the time
     */
    private String timeToString(int seconds) {
        String minutesStr = formatTime(seconds / 60);
        String secondStr = formatTime(seconds % 60);
        return String.format("%s:%s", minutesStr, secondStr);
    }

    /**
     * Converts time to two digit string
     *
     * @param time the time as an integer
     * @return the String representation of the time with two digits
     */
    private String formatTime(int time) {
        String result = String.valueOf(time);
        return result.length() == 1 ? String.format("0%s", result) : result;
    }
}
