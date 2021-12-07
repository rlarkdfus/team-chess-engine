package ooga.controller;

import ooga.Location;
/**
 * @Authors gordon albert luis richard sam
 * <p>
 * purpose - this class defines the methods that are used in editor mode. This interface extends the
 *  controller interface so it inherits those methods that connect the view and model. The methods below
 *  are used for adding new pieces as editor mode allows the user to freely add, remove, and move
 *  pieces on the board.
 * assumptions - that all the methods below are defined
 * dependencies - this class depends on the editor model, view classes and the boardbuilder classes.
 * To use - The user create a new editor controller object and a default view and model will be created.
 *  If a file is selected, the file is sent to boardbuilder and then new view and model objects are
 *  created and the game is remade.
 */
public interface EditorControllerInterface extends ControllerInterface{

  /**
   * this method is used by the editor view to determine if the user has selected a new menu piece. if so,
   * we copy down the new piece's team and type so that we can create new copies of that piece on the board
   * @param team - the clicked piece's team color
   * @param name - the clicked piece's type
   * @return - true or false depending on if the user selected a new menu piece
   */
  boolean selectMenuPiece(String team, String name);

  /**
   * this method is used to figure out if the user has selected a menu piece or not
   * @return true or false depending on if the user has selected a menu piece or not
   */
  boolean hasMenuPiece();

  /**
   * this method is used to add a new piece to the board. the piece that is added is based on which
   * piece was clicked on, as that will set selectedteam and selectedname. The location is based on where
   * the user clicked after selecting which menu piece they want to create
   * @param location - the location that the user clicked to indicate where they want the piece to be
   * placed
   */
  void addPiece(Location location);

}
