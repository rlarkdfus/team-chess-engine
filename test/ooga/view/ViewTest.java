package ooga.view;

import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import ooga.controller.Controller;
import org.junit.jupiter.api.Test;
import org.testfx.service.query.EmptyNodeQueryException;
import util.DukeApplicationTest;


import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewTest extends DukeApplicationTest {

    // this method is run BEFORE EACH test to set up application in a fresh state
    @Override
    public void start(Stage stage) {
        new Controller();
    }

    @Test
    void testChangeColor1() {
        ColorPicker colorPicker1 = lookup("#board_color_1").query();
        Rectangle testSquare = lookup("#square_location(0,0)").query();
        Color expected = Color.RED;
        setValue(colorPicker1, expected);
        assertEquals(expected, testSquare.getFill());
    }

    @Test
    void testChangeColor2() {
        ColorPicker colorPicker2 = lookup("#board_color_2").query();
        Rectangle testSquare = lookup("#square_location(0,1)").query();
        Color expected = Color.GREEN;
        setValue(colorPicker2, expected);
        assertEquals(expected, testSquare.getFill());
    }

    @Test
    void testChangeStyle() {
        ComboBox styleChanger = lookup("#piece_style").query();
        String expected = "horsey";
        clickOn(styleChanger);
        clickOn(expected);
        assertDoesNotThrow(() -> lookup("#pieceView_location(0,0)_style(horsey)").query());
    }

    @Test
    void testResetGame() {
        Button reset = lookup("#new_game").queryButton();
        clickOn(reset);
    }
}
