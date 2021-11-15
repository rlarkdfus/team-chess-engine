package ooga.model;

import ooga.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player implements PlayerInterface {
    private List<PieceInterface> remainingPieces;
    private List<PieceInterface> removedPieces;
    private final String team;
    private int score;

    //TODO: Chess timer

    public Player(String team) {
        this.team = team;
        remainingPieces = new ArrayList<>();
        removedPieces = new ArrayList<>();
//        inCheck = false;
        score = 0;
    }

    /**
     * remove a pice from the player's posession
     * @param location
     */
    public void removePiece(Location location){
        for(PieceInterface piece : remainingPieces) {
            if(piece.getLocation().equals(location)) {
                remainingPieces.remove(piece);
                removedPieces.add(piece);
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
        return remainingPieces;
    }

    /**
     * adds a piece to players list of pieces
     * @param piece
     */
    @Override
    public void addPiece(PieceInterface piece){
        remainingPieces.add(piece);
        score += piece.getScore();
    }

    /**
     * returns the player team
     * @return
     */
    @Override
    public String getTeam(){
        return team;
    }

    public Location getKingLocation(){
        for(PieceInterface piece : remainingPieces) {
            if(piece.getName().equals("K")) {
                return piece.getLocation();
            }
        }
        return null;
    }

    @Override
    public void movePiece(PieceInterface piece, Location end) {
        piece.moveTo(end);
    }

    @Override
    public PieceInterface getPiece(Location location) {
        for(PieceInterface piece : remainingPieces) {
            if(piece.getLocation().equals(location)) {
                return piece;
            }
        }
        return null;
    }

    private void calculateScore(){
        for(PieceInterface piece: remainingPieces){
            score += piece.getScore();
        }
    }
}
