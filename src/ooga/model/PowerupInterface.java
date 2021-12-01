package ooga.model;

import ooga.Location;

public interface PowerupInterface {
    public void addLocation(Location location);
    public void executePower(PlayerInterface playerInterface, PieceInterface pieceInterface);

}
