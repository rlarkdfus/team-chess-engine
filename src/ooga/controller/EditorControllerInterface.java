package ooga.controller;

import ooga.Location;

public interface EditorControllerInterface extends ControllerInterface{

  boolean selectMenuPiece(String team, String name);

  boolean hasMenuPiece();

  void addPiece(Location location);

}
