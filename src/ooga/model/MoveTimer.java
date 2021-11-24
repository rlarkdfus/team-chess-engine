package ooga.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Timer;
import java.util.TimerTask;

public class MoveTimer {
    private StringProperty timeLeft;
    private int seconds;
    private int initialTime;
    private int increment;
    private boolean outOfTime;

    private boolean isPlaying;
    private boolean isPaused;

    private Timer timer;
    private TimerTask timerTask;

    public MoveTimer(int initialTime, int initialIncrement) {
        this.initialTime = initialTime;
        this.seconds = initialTime;
        this.timeLeft = new SimpleStringProperty(formatTime(initialTime));
        this.increment = initialIncrement;
        this.outOfTime = false;
        this.isPlaying = false;
    }

    public StringProperty getTimeLeft() {
        return timeLeft;
    }

    public boolean isOutOfTime() {
        return outOfTime;
    }

    public void setInitialTime(int initialTime) {
        this.initialTime = initialTime;
    }

    public void setIncrement(int increment) {
        this.increment = increment;
    }

    public void start() {
        if (isPlaying) {
            return;
        }
        isPlaying = true;
        isPaused = false;
        timer = new Timer();
        timerTask = makeTimerTask();
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
        timerTask.run();
    }

    public void pause() {
        if (isPaused) {
            return;
        }
        timer.cancel();
        isPaused = true;
        isPlaying = false;
    }

    public void reset() {
        timer.cancel();
        isPaused = true;
        isPlaying = false;
        seconds = initialTime;
        outOfTime = false;
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
        seconds--;
        if (seconds == 0) {
            outOfTime = true;
            timer.cancel();
        }
        timeLeft.setValue(timeToString(seconds));
    }

    /**
     * increments the amount of time by the increment value
     */
    public void incrementTime() {
        seconds += increment;
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
        return result.length() == 1 ? "0" + result : result;
    }
}
