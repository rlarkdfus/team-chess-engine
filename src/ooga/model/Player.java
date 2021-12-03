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

    public Player(String team) {
        this.team = team;
        remainingPieces = new HashMap<>();
        score = 0;
        this.killedPieces = new ArrayList<>();
        this.moveTimer = new MoveTimer();
        this.initialPieces = new ArrayList<>();
    }

    @Override
    public StringProperty getTimeLeft() {
        return moveTimer.getTimeLeft();
    }

    @Override
    public void toggleTimer() {
        moveTimer.toggle();
    }

    @Override
    public void resetTimer() {
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

    @Override
    public void incrementTimeUserInterface() {
        moveTimer.incrementTimeUserInterface();
    }

    public void incrementTime(Integer specifiedTime){
        moveTimer.incrementTime(specifiedTime);
    }

    /**
     * remove a piece from the player's possession
     * @param location
     */
    public void removePiece(Location location) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        for(PieceInterface piece : remainingPieces.keySet()) {
            if(piece.getLocation().equals(location)) {
                //Fixme: added for testing purposes
//                System.out.println("A Piece Has been Killed (you have reached the inside of the if conditional)");
//                System.out.println(piece.getEndState());
//                piece.setEliminated(true);
                //Fixme: added for testing purposes
//                System.out.println(piece.getEndState());
                killedPieces.add(piece);
                //Fixme: added for testing purposes
//                System.out.println(piece.getName() + " " + piece.getTeam() + " " +  piece.getUniqueId() + "" + piece.getEliminatedState());
                remainingPieces.remove(piece);
                score -= piece.getScore();

                return;
            }
        }
    }


    /**
     * get the list of pieces that a player has
     * @return
     */
    public List<PieceInterface> getPieces(){
        return new ArrayList<>(remainingPieces.keySet());
    }

    /**
     * adds a piece to players list of pieces
     * @param piece
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
     * @param piece
     */
    @Override
    public void removePiece(PieceInterface piece){
        remainingPieces.remove(piece);
        score -= piece.getScore();
    }

    /**
     * return a player's score
     * @return
     */
    @Override
    public int getScore() {
        return score;
    }

    @Override
    public PieceInterface createQueen() {
            PieceInterface queen = null;
            for(PieceInterface pieceInterface: initialPieces){
                if(pieceInterface.getName().equals("Q")){
                    queen = pieceInterface;
                }
            }
            return queen;

    }

    //FIXME:
    //Needs to be in initial Piece List
    public PieceInterface createPiece(String pieceName) throws InvalidPieceException {
        for(PieceInterface pieceInterace: initialPieces){
            if(pieceInterace.getName().equals(pieceName)){
                return pieceInterace;
            }
        }
        throw new InvalidPieceException(pieceName);
    }

    @Override
    public void addTime(Integer seconds) {

    }

    /**
     * returns the player team
     * @return
     */
    @Override
    public String getTeam(){
        return team;
    }

    /**
     * return the player's king
     * @return
     */
    @Override
    public PieceInterface getKing() {
        return null;
    }

    /**
     * perform a piece move to end location
     * @param piece
     * @param end
     */
    @Override
    public void movePiece(PieceInterface piece, Location end) {

    }

    /**
     * test a piece move to end location
     * @param piece
     * @param end
     */
    @Override
    public void tryMove(PieceInterface piece, Location end) {

    }

    /**
     * get score of all pieces
     */
    private void calculateScore(){
        for(PieceInterface piece: remainingPieces.keySet()){
            score += piece.getScore();
        }
    }
}
