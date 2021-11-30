package ooga.model;

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

    //TODO: Chess timer

    public Player(String team) {
        this.team = team;
        remainingPieces = new HashMap<>();
        score = 0;
        this.killedPieces = new ArrayList<>();
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
                piece.setEliminated(true);
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
        score += piece.getScore();
    }

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

    /**
     * returns the player team
     * @return
     */
    @Override
    public String getTeam(){
        return team;
    }

    @Override
    public PieceInterface getKing() {
        return null;
    }

    @Override
    public void movePiece(PieceInterface piece, Location end) {

    }

    @Override
    public void tryMove(PieceInterface piece, Location end) {

    }

    /**
     * return the piece, if it exists, at a location
     * @param location
     * @return
     */
    @Override
    public PieceInterface getPiece(Location location) {
        List<PieceInterface> p = new ArrayList<>();
        for(PieceInterface piece : remainingPieces.keySet()) {
            if(piece.getLocation().equals(location)) {
                p.add(piece);
//                return piece;
            }
        }
        if(p.size() > 0){
            if(p.size() > 1){
                System.out.println("Player.getPiece " + p.size() + " pieces " + p + " at " + location);
            }
            return p.get(0);
        }
        return null;
    }

    /**
     * return the legal moves of a piece at location
     * @param location
     * @return
     */
    public List<Location> getLegalMoves(Location location){
        return remainingPieces.get(getPiece(location));
    }

    /**
     * set the legal moves of a piece
     * @param piece
     * @param moves
     */
    public void setLegalMoves(PieceInterface piece, List<Location> moves){
        remainingPieces.put(piece, moves);
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
