package ooga.model;

import javafx.beans.property.StringProperty;

public interface TimerInterface {
    StringProperty getTimeLeft();

    boolean isOutOfTime();

    void setInitialTime(int minutes);

    void setIncrement(int seconds);

    void incrementTime();

    void toggle();

    void reset();
}
