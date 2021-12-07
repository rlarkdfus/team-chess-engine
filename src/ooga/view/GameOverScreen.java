package ooga.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ooga.LogUtils;
import ooga.controller.Controller;

public class GameOverScreen {

  private final int STAGE_SIZE = 200;
  private static final String DEFAULT_RESOURCE_PACKAGE =
          View.class.getPackageName() + ".resources.";
  private static final String STYLE_PACKAGE = "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  private static final String DEFAULT_STYLESHEET = STYLE_PACKAGE + "style.css";

  private final Controller controller;
  private final Stage stage;
  private final String winner;
  private BorderPane root;

  /**
   * makes the game over screen
   *
   * @param controller the controller responsible for handling actions in the GameOverScreen
   * @param winner     the side that won
   */
  public GameOverScreen(Controller controller, String winner) {
    this.controller = controller;
    this.stage = new Stage();
    this.winner = winner;
    setupDisplay();
  }

  /**
   * sets up the display of the game over screen
   */
  private void setupDisplay() {
    stage.setResizable(false);
    root = new BorderPane();
    root.setCenter(makeWinnerPanel());

    Scene scene = new Scene(root, STAGE_SIZE, STAGE_SIZE);
    scene.getStylesheets().add(getClass().getResource(DEFAULT_STYLESHEET).toExternalForm());
    stage.setScene(scene);
    stage.show();
  }

  /**
   * makes a BorderPane for holding the text, picture, and buttons
   *
   * @return the BorderPane created
   */
  private BorderPane makeWinnerPanel() {
    BorderPane winnerPanel = new BorderPane();

    winnerPanel.setTop(makeWinnerText());
    winnerPanel.setCenter(makeWinnerPicture());
    winnerPanel.setBottom(makeButtons());

    winnerPanel.getStyleClass().add("EndScreenUI");
    return winnerPanel;
  }

  /**
   * makes the Winner Text
   *
   * @return
   */
  private HBox makeWinnerText() {
    HBox top = new HBox();
    top.setAlignment(Pos.CENTER);

    String text = "Winner: " + winner;
    if (winner == null) {
      return top;
    }
    Label winnerText = new Label(text);
    winnerText.setId("WinnerText");

    top.getChildren().add(winnerText);
    return top;
  }

  /**
   * makes the picture for the winner screen
   *
   * @return
   */
  private Node makeWinnerPicture() {
    try {
      if (winner.equals("draw")) {
        Label drawLabel = new Label("Draw");
        drawLabel.setId("DrawText");
        return drawLabel;
      }
      String imagePath = String.format("images/%s/%s%s.png", "companion", winner, "K");
      return new ImageView(imagePath);
    } catch (Exception e) {
      LogUtils.error(this, "making winner picture: " + e);
      throw e;
    }
  }

  /**
   * makes the buttons in the game over screen
   *
   * @return
   */
  private HBox makeButtons() {
    HBox buttons = new HBox(5);
    buttons.setAlignment(Pos.CENTER);

    Button playagain = new Button("Play again");
    playagain.setOnAction(e -> playAgain());

    Button quit = new Button("Quit");
    quit.setOnAction(e -> quit());

    buttons.getChildren().addAll(playagain, quit);
    return buttons;
  }

  /**
   * resets the controller and allows the user to play again
   */
  private void playAgain() {
    controller.reset();
    stage.close();
  }

  /**
   * closes the game over screen
   */
  private void quit() {
    controller.quit();
  }

}
