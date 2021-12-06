package ooga.view.util;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ViewUtility {

    public static final String ERROR_ALERT_TITLE = "Error";
    public final String SELECT_JSON_FILE = "Select JSON File";
    public final String JSON_FILE_EXTENSION_DESCRIPTION = "JSON Files (*.json)";
    public final String JSON_EXTENSION = "*.json";
    public final String EMPTY_FILE_PATH = "";
    public static final String LANGUAGE_RESOURCE_PATH = "ooga/view/resources/";
    public static final String DEFAULT_LANGUAGE = "English";
    private static ResourceBundle languageResource;

    private static List<Labeled> components = new ArrayList<>();
    private static List<MenuButton> menuButtons = new ArrayList<>();

    public ViewUtility() {
        languageResource = ResourceBundle.getBundle(LANGUAGE_RESOURCE_PATH + DEFAULT_LANGUAGE);
    }

    /**
     * makes a Label node
     *
     * @param property the property for the Label
     * @return the Label created
     */
    public Label makeLabel(String property) {
        Label result = new Label();
        result.setText(languageResource.getString(property));
        result.getStyleClass().add("label");
        components.add(result);
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
        result.setText(languageResource.getString(property));
        result.setOnAction(response);
        result.getStyleClass().add("button");
        components.add(result);
        return (Button) setID(property, result);
    }

    public TextField makeTextField(String property) {
        TextField result = new TextField();
        result.getStyleClass().add("text-field");
        return (TextField) setID(property, result);
    }

    /**
     * makes a TextField node
     *
     * @param property the property for the TextField
     * @return the TextField created
     */
    public TextField makeTextField(String property, EventHandler<KeyEvent> response) {
        TextField result = makeTextField(property);
        result.setOnKeyPressed(response);
        return result;
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
        result.setText(languageResource.getString(property));
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
            lang.put(languageResource.getString(option), option);
        }
        result.setItems(FXCollections.observableArrayList(lang.keySet().stream().toList()));
        result.valueProperty().addListener((o, oldValue, newValue) -> response.accept(lang.get(newValue)));
        result.getStyleClass().add("combo-box");
        return (ComboBox) setID(property, result);
    }

    public MenuButton makeMenu(String property, List<String> choices, Consumer<String> response) {
        MenuButton result = new MenuButton();
        List<MenuItem> options = new ArrayList<>();
        for(String option : choices) {
            MenuItem menuItem = new MenuItem(option);
            menuItem.setOnAction(e -> {
                response.accept(option);
                result.setText(languageResource.getString(option));
            });
            menuItem.getStyleClass().add("menu-item");
            menuItem.setId(option);
            options.add(menuItem);
        }
        result.getStyleClass().add("menu-button");
        result.getItems().addAll(options);
        result.setText(languageResource.getString(choices.get(0)));
        menuButtons.add(result);
        return (MenuButton) setID(property, result);
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
     * @param property     the property for the ColorPicker
     * @param defaultColor the default ColorPicker color
     * @param response     the event to occur when the ColorPicker color changes
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
//        FileChooser.ExtensionFilter fileExtension = new FileChooser.ExtensionFilter(
//                JSON_FILE_EXTENSION_DESCRIPTION, JSON_EXTENSION);
//        fileChooser.getExtensionFilters().addAll(fileExtension);
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
    public static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(ERROR_ALERT_TITLE);
        alert.setContentText(languageResource.getString(message));
        alert.showAndWait();
    }

    /**
     * makes a TextInputDialog without a cancel button
     *
     * @param headerText the header of the TextInputDialog
     *                   //* @param response the condition for allowing the text input dialog to close
     * @return the TextInputDialog created
     */
    public TextInputDialog makeUncancellableTextInputDialog(String headerText) {
        TextInputDialog result = new TextInputDialog();
        result.setHeaderText(headerText);
        result.getDialogPane().getButtonTypes().remove(ButtonType.CANCEL);
        return result;
    }

    public Dialog makeDialog(List<Label> labels, List<TextField> textFields) {
        Dialog dlg = new Dialog();
        dlg.getDialogPane().setContent(makeLabelsFieldsGridPane(labels, textFields));
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dlg.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dlg.setResultConverter(b -> b == buttonTypeOk ? getTextFieldStrings(textFields) : null);
        return dlg;
    }

    // makes an n by 2 grid with labels in the first column, textfields in the second; n is number of rows
    private GridPane makeLabelsFieldsGridPane(List<Label> labels, List<TextField> textFields) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));
        for (int row = 0; row < labels.size(); row++) {
            grid.add(labels.get(row), 0, row);
            grid.add(textFields.get(row), 1, row);
        }
        return grid;
    }

    private List<String> getTextFieldStrings(List<TextField> textFields) {
        List<String> strings = new ArrayList<>();
        for (TextField textField : textFields) {
            strings.add(textField.getText());
        }
        return strings;
    }

    public List<String> getDialogResults(Dialog dlg) {
        Optional<List<String>> strArr = dlg.showAndWait();
        return strArr.get();
    }

    public void changeLanguage(String language) {
        languageResource = ResourceBundle.getBundle(LANGUAGE_RESOURCE_PATH + language);
        for (Labeled component : components) {
            component.setText(languageResource.getString(component.getId()));
        }
        for(MenuButton menuButton : menuButtons) {
            for(MenuItem menuItem : menuButton.getItems()) {
                if(menuItem.getText().equals(menuButton.getText())) {
                    menuButton.textProperty().setValue(languageResource.getString(menuItem.getId()));
                }
                menuItem.textProperty().setValue(languageResource.getString(menuItem.getId()));
            }
        }
    }

    public void setTextInputDialogCloseRestrictions(TextInputDialog textInputDialog, Set<String> acceptableValues) {
        Button okButton = (Button) textInputDialog.getDialogPane().lookupButton(ButtonType.OK);
        TextField inputField = textInputDialog.getEditor();
        BooleanBinding isInvalid = Bindings.createBooleanBinding(() -> !acceptableValues.contains(inputField.getText().toLowerCase()), inputField.textProperty());
        okButton.disableProperty().bind(isInvalid);
    }

    /**
     * Displays a TextInputDialog and gets the value inputted by a user
     *
     * @param textInputDialog the textInputDialog to display and retrieve a string from
     * @return the String value inputted
     */
    public String getTextInputDialogResult(TextInputDialog textInputDialog) {
        return textInputDialog.showAndWait().get();
    }
}
