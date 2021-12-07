package ooga.model;

import javafx.beans.property.StringProperty;

/**
 * @authors
 * purpose - this provides the api that allows the player to interact with their timer
 * assumptions - assumes that the timer is valid and enabled
 * dependencies - it does not depend on any other classes
 * usage - the player and other classes can call these methods to interact with the timer
 */
public interface TimerInterface {
    /**
     * returns the time remaining on the timer object
     * @return time remaining
     */
    StringProperty getTimeLeft();

    /**
     * sets the timer to its initial time but does not toggle it
     */
    void setToInitialTime();

    /**
     * determines whether a timer has run out
     * @return whether a timer is out of time
     */
    boolean isOutOfTime();

    /**
     * set the initial time in minutes of the timer
     * @param minutes initial time to count down from
     */
    void setInitialTime(int minutes);

    /**
     * set the increment of the timer that increases after a move
     * @param seconds amount of time to increment by
     */
    void setIncrement(int seconds);

    /**
     * set the increment through the user interface
     */
    void incrementTimeUserInterface();

    /**
     * increment the time by a certain amount
     * @param specifiedTime amount of time to increment by
     */
    void incrementTime(int specifiedTime);

    /**
     * toggle the timer to start or stop
     */
    void toggle();

    /**
     * reset the timer to start over from its initial values
     */
    void reset();
}
