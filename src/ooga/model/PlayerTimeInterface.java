package ooga.model;

import javafx.beans.property.StringProperty;

public interface PlayerTimeInterface {
    void toggleTimer();

    StringProperty getTimeLeft();

    void resetTimer();

    void configTimer(int initialTime, int increment);

    void incrementTimeUserInterface();

    void incrementTime(Integer specifiedTime);
}
