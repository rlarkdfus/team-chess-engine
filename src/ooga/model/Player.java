package ooga.model;

import ooga.Location;

import java.util.ArrayList;
import java.util.List;

public class Player implements PlayerInterface {
    List<PieceInterface> remainingPieces;
    private final String team;
    private Location kingLocation;
    private boolean inCheck;
    private int score;

    //TODO: Chess timer


    public Player(String team) {
        this.team = team;
        remainingPieces = new ArrayList<>();
        inCheck = false;
        score = 0;
    }

    /**
     * remove a pice from the player's posession
     * @param piece
     */
    public void removePiece(Piece piece){
        remainingPieces.remove(piece);
        score -= piece.getScore();
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
        if(piece.getName().equals("K")) {
            kingLocation = piece.getLocation();
        }
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
        return kingLocation;
    }


    private void calculateScore(){
        for(PieceInterface piece: remainingPieces){
            score += piece.getScore();
        }
    }

    public void setInCheck(boolean inCheck) {
        this.inCheck = inCheck;
    }
}
