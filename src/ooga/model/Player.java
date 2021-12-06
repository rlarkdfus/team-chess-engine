package ooga.model;

import javafx.beans.property.StringProperty;
import ooga.Location;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player implements PlayerInterface {
    //Keep track of all their killed pieces
    private final String team;
    private int score;
    private TimerInterface moveTimer;
    private List<PieceInterface> pieces;

    /**
     * creates a player object
     * @param team player's team
     */
    public Player(String team) {
        this.team = team;
        pieces = new ArrayList<>();
        score = 0;
        this.moveTimer = new MoveTimer();
    }

    /**
     * get the amount of time remaining on a player's timer
     * @return the time left
     */
    @Override
    public StringProperty getTimeLeft() {
        return moveTimer.getTimeLeft();
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
        System.out.println("resetting timer: " + team);
        moveTimer.reset();
    }

    /**
     * configures the player's timer with an initial time and increment
     * @param initialTimeMinutes initial time (min)
     * @param incrementSeconds time increment per move (s)
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
     * @param specifiedTime the new time increment
     */
    public void incrementTime(Integer specifiedTime){
        moveTimer.incrementTime(specifiedTime);
    }

    /**
     * get the list of pieces that a player has
     * @return list of all pieces held by a player
     */
    public List<PieceInterface> getPieces(){
        return new ArrayList<>(pieces);
    }

    /**
     * adds a piece to players list of pieces
     * @param piece piece to add to a players list of remaining pieces
     */
    @Override
    public void addPiece(PieceInterface piece){
        pieces.add(piece);
        score += piece.getScore();
    }

    //FIXME: add to interface


    /**
     * remove a piece from player's possession
     * @param piece remove a piece from player's posession
     */
    @Override
    public void removePiece(PieceInterface piece){
        pieces.remove(piece);
        score -= piece.getScore();
    }

    @Override
    public void clearPieces(){
        pieces = new ArrayList<>();
    }

    /**
     * return a player's score
     * @return score
     */
    @Override
    public int getScore() {
        return score;
    }

    /**
     * returns the player team
     * @return player's team
     */
    @Override
    public String getTeam(){
        return team;
    }

//    /**
//     * perform a piece move to end location
//     * @param piece
//     * @param end
//     */
//    @Override
//    public void movePiece(PieceInterface piece, Location end) {
//
//    }
//
//    /**
//     * test a piece move to end location
//     * @param piece
//     * @param end
//     */
//    @Override
//    public void tryMove(PieceInterface piece, Location end) {
//
//    }
//
//    /**
//     * get score of all pieces
//     */
//    private void calculateScore(){
//        for(PieceInterface piece: remainingPieces.keySet()){
//            score += piece.getScore();
//        }
//    }
}
