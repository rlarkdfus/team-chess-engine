package ooga.controller;


import ooga.Location;
import ooga.controller.Config.Builder;
import ooga.model.EditorBoard;
import ooga.model.EditorEngine;
import ooga.model.Engine;
import ooga.view.EditorView;
import ooga.view.ViewInterface;

import java.io.File;

/**
 * @author albert luis gordon sam richard
 * <p>
 * purpose - this class is a controller type object so it connects the model and view. This specific
 * subclass is used for when we are in editor mode, which allows for users to freely add, remove, and
 * move pieces on the board. This is done by calling editor subclasses of the view and model and their subclasses.
 * assumptions - we assume that the defaultConfiguration file is valid. if this isn't the case, the
 * game will crash.
 * dependencies - this class depends on the editor model classes, the editor view classes, and the
 * boardbuilder classes.
 * To use - The user must only create a new EditorController object instead of a GameController object
 * in main.
 */
public class EditorController extends Controller implements EditorControllerInterface {

  public static final File DEFAULT_CHESS_CONFIGURATION = new File("data/chess/defaultEditor.json");
  private String selectedTeam;
  private String selectedName;

  private EditorEngine model;

  /**
   * method for accessing the default configuration file
   */
  @Override
  protected File getDefaultConfiguration() {
    return DEFAULT_CHESS_CONFIGURATION;
  }

  /**
   * overridden method to initialize the board with the default configuration objects
   */
  @Override
  protected Engine initializeModel(Builder boardBuilder) {
    model = new EditorBoard(boardBuilder.getInitialPlayers(), boardBuilder.getBoardSize());
    return model;
  }

  /**
   * overridden method to initialize the view with the default configuration objects
   */
  @Override
  protected ViewInterface initializeView(Builder boardBuilder) {
    ViewInterface view = new EditorView(this);
    view.initializeDisplay(boardBuilder.getInitialPieceViews(), boardBuilder.getPowerupLocations(), boardBuilder.getBoardSize());
    return view;
  }

  /**
   * this method is used by the editor view to determine if the user has selected a new menu piece. if so,
   * we copy down the new piece's team and type so that we can create new copies of that piece on the board
   *
   * @param team - the clicked piece's team color
   * @param name - the clicked piece's type
   * @return - true or false depending on if the user selected a new menu piece
   */
  @Override
  public boolean selectMenuPiece(String team, String name) {
    boolean selectNewPiece = !hasMenuPiece() || !selectedTeam.equals(team) || !selectedName.equals(name);
    if (selectNewPiece) {
      selectedTeam = team;
      selectedName = name;
    } else {
      selectedTeam = null;
      selectedName = null;
    }
    return selectNewPiece;
  }

  /**
   * this method is used to figure out if the user has selected a menu piece or not
   *
   * @return true or false depending on if the user has selected a menu piece or not
   */
  @Override
  public boolean hasMenuPiece() {
    return selectedTeam != null && selectedName != null;
  }

  /**
   * this method is used to add a new piece to the board. the piece that is added is based on which
   * piece was clicked on, as that will set selectedteam and selectedname. The location is based on where
   * the user clicked after selecting which menu piece they want to create
   *
   * @param location - the location that the user clicked to indicate where they want the piece to be
   *                 placed
   */
  @Override
  public void addPiece(Location location) {
    model.addPiece(selectedTeam, selectedName, location);
    updateView();
  }
}