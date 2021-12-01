package ooga.view.util;

import javafx.beans.property.StringProperty;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ViewUtility {

    private ResourceBundle myResources = ResourceBundle.getBundle("ooga/view/resources/English");
    public final String ERROR_ALERT_TITLE = "Error";
    public final String SELECT_JSON_FILE = "Select JSON File";
    public final String JSON_FILE_EXTENSION_DESCRIPTION = "JSON Files (*.json)";
    //public final String CSV_FILE_EXTENSION_DESCRIPTION = "CSV (Comma delimited) (*.csv)";
    public final String JSON_EXTENSION = "*.json";
    //public final String CSV_EXTENSION = "*.csv";
    public final String EMPTY_FILE_PATH = "";

    /**
     * makes a Label node
     *
     * @param property the property for the Label
     * @return the Label created
     */
    public Label makeLabel(String property) {
        Label result = new Label();
        result.setText(myResources.getString(property));
        //result.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        return (Label) setID(property, result);
    }

    /**
     * makes a Button node
     *
     * @param property the property for the button
     * @param response the event that occurs when we press the button
     * @return the Button created
     */
    public Button makeButton(String property, EventHandler<ActionEvent> response) {
        Button result = new Button();
        result.setText(myResources.getString(property));
        result.setOnAction(response);
        result.getStyleClass().add("button");
        return (Button) setID(property, result);
    }

    /**
     * makes a TextField node
     *
     * @param property the property for the TextField
     * @return the TextField created
     */
    public TextField makeTextField(String property, EventHandler<KeyEvent> response) {
        TextField result = new TextField();
        result.getStyleClass().add("text-field");
        result.setOnKeyPressed(response);
        return (TextField) setID(property, result);
    }

    /**
     * makes a PasswordField node
     *
     * @param property the property for the PasswordField
     * @return the PasswordField created
     */
    public PasswordField makePasswordField(String property, EventHandler<KeyEvent> response) {
        PasswordField result = new PasswordField();
        result.getStyleClass().add("password-field");
        result.setOnKeyPressed(response);
        return (PasswordField) setID(property, result);
    }

    /**
     * makes a Text node whose text changes often
     *
     * @param property   the property for the Text node
     * @param boundValue the StringProperty that the text will display
     * @return the Text created
     */
    public Text makeText(String property, StringProperty boundValue) {
        Text result = new Text();
        result.setText(myResources.getString(property));
        result.getStyleClass().add("text");
        result.textProperty().bind(boundValue);
        return (Text) setID(property, result);
    }

    /**
     * makes a ComboBox node
     *
     * @param property the property for the ComboBox
     * @param choices  the List of Strings representing the choices for the ComboBox
     * @param response the event to occur on action with the ComboBox
     * @return the ComboBox created
     */
    public ComboBox makeComboBox(String property, List<String> choices, Consumer<String> response) {
        ComboBox result = new ComboBox();
        Map<String, String> lang = new HashMap<>();
        for (String option : choices) {
            lang.put(myResources.getString(option), option);
        }
        result.setItems(FXCollections.observableArrayList(lang.keySet().stream().toList()));
        result.valueProperty()
                .addListener((o, oldValue, newValue) -> response.accept(lang.get(newValue)));
        result.getStyleClass().add("combo-box");
        //result.setValue("test");
        return (ComboBox) setID(property, result);
    }

    /**
     * makes a Slider node
     *
     * @param property the property
     * @param min      the minimum slider value
     * @param max      the maximum slider value
     * @param start    the initial slider value
     * @param response the event to occur when the slider value changes
     * @return the Slider created
     */
    public Slider makeSlider(String property, double min, double max, double start, Consumer<Double> response) {
        Slider result = new Slider(min, max, start);
        result.valueProperty().addListener((o, oldValue, newValue) -> response.accept(newValue.doubleValue()));
        result.getStyleClass().add("slider");
        return (Slider) setID(property, result);
    }

    /**
     * makes a ColorPicker node
     *
     * @param property the property for the ColorPicker
     * @param defaultColor the default ColorPicker color
     * @param response the event to occur when the ColorPicker color changes
     * @return the ColorPicker created
     */
    public ColorPicker makeColorPicker(String property, Color defaultColor, Consumer<Color> response) {
        ColorPicker result = new ColorPicker(defaultColor);
        result.valueProperty().addListener((o, oldColor, newColor) -> response.accept(newColor));
        return (ColorPicker) setID(property, result);
    }

    public GridPane makeGridPane(String property) {
        GridPane result = new GridPane();
        return (GridPane) setID(property, result);
    }

    /**
     * creates a FileChooser to select a JSON file
     *
     * @return the selected JSON file
     */
    public File selectJSONFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(SELECT_JSON_FILE);
        FileChooser.ExtensionFilter fileExtension = new FileChooser.ExtensionFilter(JSON_FILE_EXTENSION_DESCRIPTION,
                JSON_EXTENSION);
        fileChooser.getExtensionFilters().add(fileExtension);
        return fileChooser.showOpenDialog(new Stage());
    }

    /**
     * creates a FileChooser to save a JSON file
     *
     * @return the path of the saved JSON file
     */
    public String saveJSONPath() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtension = new FileChooser.ExtensionFilter(
                JSON_FILE_EXTENSION_DESCRIPTION, JSON_EXTENSION);
        fileChooser.getExtensionFilters().addAll(fileExtension);
        File file = fileChooser.showSaveDialog(new Stage());
        return file != null ? file.getAbsolutePath() : EMPTY_FILE_PATH;
    }

    /**
     * sets the id of a node
     *
     * @param id   the id of the node
     * @param node the node to set the id of
     * @return the node
     */
    private Node setID(String id, Node node) {
        node.setId(id);
        return node;
    }

    /**
     * shows an error message
     *
     * @param message the error message to show
     */
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR_ALERT_TITLE);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
