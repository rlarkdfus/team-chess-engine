package ooga.model;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javafx.beans.property.StringProperty;
import ooga.Location;

public interface PlayerInterface {

  void toggleTimer();

  StringProperty getTimeLeft();

  void resetTimer();

  void configTimer(int initialTime, int increment);

  void incrementTime();

  void removePiece(Location location)
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException;

  List<PieceInterface> getPieces();

  void addPiece(PieceInterface piece);

  String getTeam();

  PieceInterface getKing();

  void movePiece(PieceInterface piece, Location end);

  void tryMove(PieceInterface piece, Location end);

  PieceInterface getPiece(Location location);

  List<Location> getLegalMoves(Location location);

  void setLegalMoves(PieceInterface piece, List<Location> legalMoves);

  void removePiece(PieceInterface piece);

    int getScore();
    PieceInterface createQueen();
    void addTime(Integer seconds);

  PieceInterface createPiece(String pieceName) throws InvalidPieceException;
}
