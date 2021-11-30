package ooga.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javafx.beans.property.StringProperty;
import ooga.Location;
import ooga.model.*;
import ooga.view.View;
import ooga.view.ViewInterface;
import org.json.JSONObject;

public class Controller implements ControllerInterface {

    public static final File DEFAULT_CHESS_CONFIGURATION = new File("data/chess/defaultChess.json");
    public static final int DEFAULT_INITIAL_TIME = 10;
    public static final int DEFAULT_INITIAL_INCREMENT = 5;

    private Engine model;
    private ViewInterface view;
    private LocationWriter locationWriter;
    private Builder boardBuilder;
    private TimeController timeController;
    private File jsonFile;

    public Controller() {
        try {
            jsonFile = DEFAULT_CHESS_CONFIGURATION;
            boardBuilder = new BoardBuilder(DEFAULT_CHESS_CONFIGURATION);
            timeController = new TimeController(DEFAULT_INITIAL_TIME, DEFAULT_INITIAL_INCREMENT);
            view = new View(this);
            locationWriter = new LocationWriter();
            buildGame(boardBuilder);
            view.initializeDisplay(boardBuilder.getInitialPieceViews());
            timeController.configTimers(model.getPlayers());
            startTimersForNewGame();
        } catch (Exception e) {
            System.out.println(e.getMessage());
//      view.showError(e.getMessage());
        }
    }

    /**
     * Reset the game with the default board configuration
     */
    @Override
    public void resetGame() {
        try {
            uploadConfiguration(DEFAULT_CHESS_CONFIGURATION);
        } catch (Exception e) {
            //todo:handle
        }
    }

    /**
     *
     * @param location is the desired destination of the move
     * @return whether the piece can be moved to the location
     */
    @Override
    public boolean canMovePiece(Location location) {
        return model.canMovePiece(location);
    }

    /**
     * sets up a new game with the initial configuration file
     * @param file the sim file with the initial board configuration
     */
    @Override
    public void uploadConfiguration(File file) {
        try {
            jsonFile = file;
            boardBuilder.build(file);
            buildGame(boardBuilder);
            view.resetDisplay(boardBuilder.getInitialPieceViews());
            startTimersForNewGame();
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    /**
     * reset timers for a new game and start the first player's timer
     */
    private void startTimersForNewGame() {
        timeController.resetTimers(model.getPlayers());
        timeController.startPlayer1Timer(model.getPlayers());
    }

    /**
     * moves a piece from the start location to the end location
     * @param start is initial location of moved piece
     * @param end is final location of moved piece
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     */
    @Override
    public void movePiece (Location start, Location end) throws
            InvocationTargetException, NoSuchMethodException, IllegalAccessException, FileNotFoundException, InvalidPieceConfigException {
      view.updateDisplay(model.movePiece(start, end));
      if (model.checkGameState() != Board.GameState.RUNNING) {
        System.out.println(model.checkGameState()); //FIXME

<<<<<<< src/ooga/controller/Controller.java
      }
      incrementWhiteTime();
    }

    public List<Location> getLegalMoves (Location location){
      return model.getLegalMoves(location);
    }

    private void buildGame (Builder boardBuilder) throws
    InvocationTargetException, NoSuchMethodException, IllegalAccessException {
      model = new Board(boardBuilder.getInitialPlayers());
      model.setEndCondition(boardBuilder.getEndConditionHandler());
      view.initializeDisplay(boardBuilder.getInitialPieceViews());
    }


    public void downloadGame (String filePath){
      try {
        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.saveFile(jsonFile, filePath);

        locationWriter.saveCSV(filePath + ".csv", model.getPlayers());
      } catch (IOException ignored) {
      }
    }

    //TODO: make this part use reflection
    public StringProperty getWhiteTimeLeft () {
      return whiteMoveTimer.getTimeLeft();
    }

    public StringProperty getBlackTimeLeft () {
      return blackMoveTimer.getTimeLeft();
    }

    public void incrementWhiteTime () {
      whiteMoveTimer.incrementTime();
    }

    public void incrementBlackTime () {
      blackMoveTimer.incrementTime();
=======
        }
>>>>>>> src/ooga/controller/Controller.java
    }

    /**
     * gets the legal moves for the given location
     * @param location the initial location
     * @return a list of destination locations reachable from the initial location
     */
    public List<Location> getLegalMoves(Location location) {
        return model.getLegalMoves(location);
    }

    private void buildGame(Builder boardBuilder) throws
            InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        model = new Board(boardBuilder.getInitialPlayers());
        timeController.configTimers(model.getPlayers());
        model.setEndCondition(boardBuilder.getEndConditionHandler());
    }

    /**
     * downloads the current board state to a csv file
     * @param filePath the file path of the csv
     */
    public void downloadGame(String filePath) {
        try {
            locationWriter.saveCSV(filePath, model.getPlayers());
        } catch (IOException ignored) {
        }
    }

    /**
     * gets the amount of time left on a player's timer
     * @param side the side of the player
     * @return a StringProperty ("mm:ss") representing the amount of time left
     */
    public StringProperty getTimeLeft(int side) {
        return model.getPlayers().get(side).getTimeLeft();
    }

    /**
     * tells the timeController to change the initial time for the next game
     * @param minutes the new initial time (min)
     */
    public void setInitialTime(int minutes) {
        timeController.setInitialTime(minutes);
    }

    /**
     * tells the timeController to change the increment time for the next game
     * @param seconds the new increment (s)
     */
    public void setIncrement(int seconds) {
        timeController.setIncrement(seconds);
    }
}
