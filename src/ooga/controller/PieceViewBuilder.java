package ooga.controller;

import ooga.Location;
import ooga.model.Piece;

public class PieceViewBuilder {

  private String team;
  private String name;
  private Location location;

  public PieceViewBuilder(Piece piece) {
    this.team = piece.getTeam();
    this.name = piece.getName();
    this.location = piece.getLocation();
  }

  public String getTeam() {
    return team;
  }

  public String getName() {
    return name;
  }

  public Location getLocation() {
    return location;
  }
}
