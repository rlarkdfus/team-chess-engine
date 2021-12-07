package ooga.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ooga.Location;

public class PowerupView extends Circle {
    public static final int POWER_UP_SIZE = 60;
    public static final Color DEFAULT_COLOR = Color.RED;

    private Location location;

    /**
     * creates a circle representing a powerup in the View
     * @param location the location of the powerup
     */
    public PowerupView(Location location) {
        this.location = location;
        this.setRadius(POWER_UP_SIZE/2);
        this.setFill(DEFAULT_COLOR);
    }
}
