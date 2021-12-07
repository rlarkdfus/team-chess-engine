package ooga.model;

import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Purpose: Player is an abstraction representing a player playing the game of chess. This is
 * useful as the player will hold its own pieces and can keep track of the time left in their turn,
 * as well as their own score.
 * Assumptions: TimerInterface works as expected and provides an abstraction so that we can keep
 * track of the time left in the game.
 * Dependencies: TimerInterface, PieceInterface
 *
 * @author Sam Li, Gordon Kim
 */
public class Player implements PlayerInterface {

  private final String team;
  private final TimerInterface moveTimer;
  private List<PieceInterface> pieces;

  /**
   * creates a player object
   *
   * @param team player's team
   */
  public Player(String team) {
    this.team = team;
    pieces = new ArrayList<>();
    this.moveTimer = new MoveTimer();
  }

  /**
   * get the amount of time remaining on a player's timer
   *
   * @return the time left
   */
  @Override
  public StringProperty getTimeLeft() {
    return moveTimer.getTimeLeft();
  }

  /**
   * sets the timer of the player to its initial time but does not toggle the timer
   */
  @Override
  public void setToInitialTime() {
    moveTimer.setToInitialTime();
  }

  /**
   * start or stop a player's timer
   */
  @Override
  public void toggleTimer() {
    moveTimer.toggle();
  }

  /**
   * reset the player's timer
   */
  @Override
  public void resetTimer() {
    moveTimer.reset();
  }

  /**
   * configures the player's timer with an initial time and increment
   *
   * @param initialTimeMinutes initial time (min)
   * @param incrementSeconds   time increment per move (s)
   */
  @Override
  public void configTimer(int initialTimeMinutes, int incrementSeconds) {
    moveTimer.setInitialTime(initialTimeMinutes);
    moveTimer.setIncrement(incrementSeconds);
  }

  /**
   * this increments the timer user interface
   */
  @Override
  public void incrementTimeUserInterface() {
    moveTimer.incrementTimeUserInterface();
  }

  /**
   * this updates the timer increment
   *
   * @param specifiedTime the new time increment
   */
  public void incrementTime(Integer specifiedTime) {
    moveTimer.incrementTime(specifiedTime);
  }

  /**
   * get the list of pieces that a player has
   *
   * @return list of all pieces held by a player
   */
  public List<PieceInterface> getPieces() {
    return new ArrayList<>(pieces);
  }

  /**
   * adds a piece to players list of pieces
   *
   * @param piece piece to add to a players list of remaining pieces
   */
  @Override
  public void addPiece(PieceInterface piece) {
    pieces.add(piece);
  }

  //FIXME: add to interface


  /**
   * remove a piece from player's possession
   *
   * @param piece remove a piece from player's posession
   */
  @Override
  public void removePiece(PieceInterface piece) {
    pieces.remove(piece);
  }

  @Override
  public void clearPieces() {
    pieces = new ArrayList<>();
  }

  /**
   * return a player's score
   *
   * @return score
   */
  @Override
  public int getScore() {
    int score = 0;
    for (PieceInterface pieceInterface : pieces) {
      score += pieceInterface.getScore();
    }
    return score;
  }

  /**
   * returns the player team
   *
   * @return player's team
   */
  @Override
  public String getTeam() {
    return team;
  }


//    private void calculateScore(){
//        for(PieceInterface piece: remainingPieces.keySet()){
//            score += piece.getScore();
//        }
//    }
}
