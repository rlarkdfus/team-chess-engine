package ooga.model;

import ooga.Location;

public interface EditorEngine extends Engine {
  /**
   * add a piece to the board
   * @param team team of the piece
   * @param name name of the piece
   * @param location location of the piece
   */
  void addPiece(String team, String name, Location location);

}
