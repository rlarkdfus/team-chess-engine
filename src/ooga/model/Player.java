package ooga.model;

import javafx.beans.property.StringProperty;
import ooga.Location;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player implements PlayerInterface {
    private Map<PieceInterface, List<Location>> remainingPieces;
    //Keep track of all their killed pieces
    private List<PieceInterface> killedPieces;
    private final String team;
    private int score;
    private TimerInterface moveTimer;
    private List<PieceInterface> initialPieces;

    /**
     * creates a player object
     * @param team player's team
     */
    public Player(String team) {
        this.team = team;
        remainingPieces = new HashMap<>();
        score = 0;
        this.killedPieces = new ArrayList<>();
        this.moveTimer = new MoveTimer();
        this.initialPieces = new ArrayList<>();
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
        return new ArrayList<>(remainingPieces.keySet());
    }

    /**
     * adds a piece to players list of pieces
     * @param piece piece to add to a players list of remaining pieces
     */
    @Override
    public void addPiece(PieceInterface piece){
        remainingPieces.put(piece, new ArrayList<>());
        initialPieces.add(piece);
        score += piece.getScore();
    }

    //FIXME: add to interface


    /**
     * remove a piece from player's possession
     * @param piece remove a piece from player's posession
     */
    @Override
    public void removePiece(PieceInterface piece){
        remainingPieces.remove(piece);
        score -= piece.getScore();
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
