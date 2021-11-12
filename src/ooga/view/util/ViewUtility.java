package ooga.view.util;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class ViewUtility {

    private static ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/resources/English");

    /**
     * makes a label
     * @param property the string that will be used for the label
     * @return the label we made
     */
    public static Label makeLabel(String property) {
        Label result = new Label();
        result.setText(myResources.getString(property));
        //result.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        return (Label) setID(property, result);
    }

    /**
     * makes a button
     * @param property the property for the button and label
     * @param response the event that occurs when we press the button
     * @return the button we made
     */
    public static Button makeButton(String property, EventHandler<ActionEvent> response) {
        Button result = new Button(property);
        result.setText(myResources.getString(property));
        result.setOnAction(response);
        result.getStyleClass().add("button");
        return (Button) setID(property, result);
    }

    // used for timers, think of better way maybe?
    public static Text makeText(String property) {
        Text result = new Text(property);
        result.setText(myResources.getString(property));
        result.getStyleClass().add("text");
        return (Text) setID(property, result);
    }

    /**
     *
     * @param response the event to occur on action with the combobox
     * @return the combobox that was made
     */
    public static ComboBox makeComboBox(String property, List<String> choices, Consumer<String> response) {
        ComboBox result = new ComboBox();
        Map<String, String> lang = new HashMap<>();
        for (String option : choices) {
            lang.put(myResources.getString(option), option);
        }
        result.setItems(FXCollections.observableArrayList(lang.keySet().stream().toList()));
        result.valueProperty()
                .addListener((o, oldValue, newValue) -> response.accept(lang.get(newValue)));
        result.getStyleClass().add("combo-box");
        return (ComboBox) setID(property, result);
    }

    /**
     * creates a slider
     * @param property the property
     * @param min the minimum slider value
     * @param max the maximum slider value
     * @return the slider
     */
    public static Slider makeSlider(String property, double min, double max, double start, Consumer<Number> response) {
        Slider result = new Slider(min, max, start);
        result.valueProperty().addListener((o, oldValue, newValue) -> response.accept(newValue));
        result.getStyleClass().add("slider");
        return (Slider) setID(property, result);
    }

    public static ColorPicker makeColorPicker(String property, Color color) {
        ColorPicker result = new ColorPicker(color);
        return (ColorPicker) setID(property, result);
    }

    public static GridPane makeGridPane(String property) {
        GridPane result = new GridPane();
        return (GridPane) setID(property, result);
    }

    /**
     * sets the id of a node
     * @param id the id of the node
     * @param node the node to set the id of
     * @return the node
     */
    private static Node setID(String id, Node node) {
        node.setId(id);
        return node;
    }

//    /**
//     * shows an error message
//     * @param message the error message to show
//     */
//    protected void showError(String message) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle(ERROR_ALERT_TITLE);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
}
