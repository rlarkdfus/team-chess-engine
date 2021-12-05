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

  void incrementTimeUserInterface();

  void incrementTime(Integer specifiedTime);

  List<PieceInterface> getPieces();

  void addPiece(PieceInterface piece);

  String getTeam();

  void movePiece(PieceInterface piece, Location end);

  void tryMove(PieceInterface piece, Location end);

  void removePiece(PieceInterface piece);

    int getScore();


}
