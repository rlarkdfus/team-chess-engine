package ooga.controller;

import javafx.beans.property.StringProperty;
import ooga.Location;
import ooga.controller.Config.*;
import ooga.model.*;
import ooga.view.ViewInterface;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Controller implements ControllerInterface {

  public static final File DEFAULT_CHESS_CONFIGURATION = new File("data/chess/defaultChess.json");

  //TODO: change protected
  private Engine model;
  private ViewInterface view;
  protected Builder boardBuilder;
  private File jsonFile;
  private Location selectedLocation;

  public Controller() {
    jsonFile = DEFAULT_CHESS_CONFIGURATION;
    boardBuilder = new BoardBuilder(DEFAULT_CHESS_CONFIGURATION);
    model = initializeModel(boardBuilder);
    view = initializeView(boardBuilder.getInitialPieceViews());
  }

  protected abstract Engine initializeModel(Builder boardBuilder);
  protected abstract ViewInterface initializeView(List<PieceViewBuilder> pieces);

  /**
   * Reset the game with the default board configuration
   */
  @Override
  public void reset() {
    uploadConfiguration(DEFAULT_CHESS_CONFIGURATION);
  }

  /**
   * Quits the game
   */
  @Override
  public void quit() {
    System.exit(0);
  }

  /**
   * @param location is the desired destination of the move
   * @return whether the piece can be moved
   */
  @Override
  public boolean canMovePiece(Location location) {
    return model.canMovePiece(location);
  }


  /**
   * sets up a new game with the initial configuration file
   *
   * @param file the sim file with the initial board configuration
   */
  @Override
  public void uploadConfiguration(File file) {
    try {
      boardBuilder.build(file);
    } catch (Exception e) {
      view.showError("no");
      e.printStackTrace();//FIXME
      return;
    }

    jsonFile = file;
    model = initializeModel(boardBuilder);
    view = initializeView(boardBuilder.getInitialPieceViews());
  }

  /**
   * moves a piece from the start location to the end location
   *
   * @param start is initial location of moved piece
   * @param end   is final location of moved piece
   */
  @Override
  public void movePiece(Location start, Location end) throws FileNotFoundException, InvalidPieceConfigException {
    List<PieceViewBuilder> pieceViewList = new ArrayList<>();
    for (PieceInterface piece : model.movePiece(start, end)) {
      pieceViewList.add(new PieceViewBuilder(piece));
    }
    view.updateDisplay(pieceViewList);
  }

  public List<Location> getLegalMoves(Location location) {
    return model.getLegalMoves(location);
  }

  @Override
  public void downloadGame(String filePath) {
    try {
      JSONWriter jsonWriter = new JSONWriter();
      jsonWriter.saveFile(jsonFile, filePath);
      //  private ViewInterface view;
      LocationWriter locationWriter = new LocationWriter();
      locationWriter.saveCSV(filePath + ".csv", model.getPlayers());
    } catch (IOException ignored) {
    }
  }

  /**
   * gets the amount of time left on a player's timer
   *
   * @param side the side of the player
   * @return a StringProperty ("mm:ss") representing the amount of time left
   */
  public StringProperty getTimeLeft(int side) {
    return model.getPlayers().get(side).getTimeLeft();
  }

  public abstract void setInitialTime(int minutes);

  public abstract void setIncrement(int seconds);

  public void selectPiece(Location location) {
    selectedLocation = location;
  }

  public boolean hasPiece(Location location) {
    return selectedLocation != null;
  }

  public void addPiece() {
//        PieceViewBuilder selectedPiece = pieces.get(selectedLocation.getRow() * col + selectedLocation.getCol());
//        PieceInterface newPiece = null;
//        try {
//            newPiece = PieceBuilder.buildPiece(selectedPiece.getTeam(), selectedPiece.getName(), selectedLocation);
//        } catch (FileNotFoundException | InvalidPieceConfigException e) {
//            e.printStackTrace();
//        }
//        model.addPiece(newPiece);
  }

  protected GameState getGameState() {
    return model.checkGameState();
  }
}