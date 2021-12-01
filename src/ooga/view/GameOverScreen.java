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
import ooga.controller.Controller;

public class GameOverScreen {
  private final int STAGE_SIZE = 200;
  private static final String DEFAULT_RESOURCE_PACKAGE = View.class.getPackageName() + ".resources.";
  private static final String STYLE_PACKAGE = "/" + DEFAULT_RESOURCE_PACKAGE.replace(".", "/");
  private static final String DEFAULT_STYLESHEET = STYLE_PACKAGE + "style.css";

  private Controller controller;
  private Stage stage;
  private String winner;

  private BorderPane root;

  public GameOverScreen(Controller controller, String winner) {
    this.controller = controller;
    this.stage = new Stage();
    this.winner = winner;
    setupDisplay();
  }

  private void setupDisplay() {
    stage.setResizable(false);
    root = new BorderPane();
    root.setCenter(makeWinnerPanel());

    Scene scene = new Scene(root, STAGE_SIZE, STAGE_SIZE);
    scene.getStylesheets().add(getClass().getResource(DEFAULT_STYLESHEET).toExternalForm());
    stage.setScene(scene);
    stage.show();
  }

  private BorderPane makeWinnerPanel() {
    BorderPane winnerPanel = new BorderPane();

    winnerPanel.setTop(makeWinnerText());
    winnerPanel.setCenter(makeWinnerPicture());
    winnerPanel.setBottom(makeButtons());

    winnerPanel.getStyleClass().add("EndScreenUI");
    return winnerPanel;
  }

  private HBox makeWinnerText() {
    HBox top = new HBox();
    top.setAlignment(Pos.CENTER);

    String text = "Winner: " + winner;
    if (winner == null){
      return top;
    }
    Label winnerText = new Label(text);
    winnerText.setId("WinnerText");

    top.getChildren().add(winnerText);
    return top;
  }

  private Node makeWinnerPicture() {
    if (winner == null){
      Label drawLabel = new Label("Draw");
      drawLabel.setId("DrawText");
      return drawLabel;
    }
    String imagePath = String.format("images/%s/%s%s.png", "companion", winner, "K");
    System.out.println(imagePath);
    return new ImageView(imagePath);
  }

  private HBox makeButtons() {
    HBox buttons = new HBox(5);
    buttons.setAlignment(Pos.CENTER);

    Button playagain = new Button("Play again");
    playagain.setOnAction(e -> playagain());

    Button quit = new Button("Quit");
    quit.setOnAction(e -> quit());

    buttons.getChildren().addAll(playagain,quit);
    return buttons;
  }

  private void playagain() {
    controller.resetGame();
    stage.close();
  }

  private void quit(){
    controller.quit();
  }

}
