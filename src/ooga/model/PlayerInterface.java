package ooga.model;

import java.util.List;
import javafx.beans.property.StringProperty;

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

//  void movePiece(PieceInterface piece, Location end);
//
//  void tryMove(PieceInterface piece, Location end);

  void removePiece(PieceInterface piece);

  void clearPieces();

  int getScore();


}
