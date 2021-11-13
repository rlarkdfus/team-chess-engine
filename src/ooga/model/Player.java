package ooga.model;

import ooga.Location;

import java.util.ArrayList;
import java.util.List;

public class Player implements PlayerInterface {
    List<PieceInterface> remainingPieces;
    private String team;
//    private Location kingLocation;
    private boolean inCheck;
    //Chess timer
//FIXME:
   int score = 0;

    public Player(String team) {
        this.team = team;
        remainingPieces = new ArrayList<>();
        calculateScore();
    }

//    public void holdPiece(Piece piece) {
//        remainingPieces.add(piece);
//    }

    private void calculateScore(){
        for(PieceInterface piece: remainingPieces){
            score += piece.getScore();
        }
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
        if(piece.getTeam().equals(this.getTeam())){
            remainingPieces.add(piece);
            score += piece.getScore();
        }
    }

    /**
     * returns the player team
     * @return
     */
    @Override
    public String getTeam(){
        return team;
    }

    //Check opponent player if they have a king
    //If it doesn't have king -> other player wins, if it has king, but can't make moves stalemate,

    public Location getKingLocation(){
        for(PieceInterface piece : remainingPieces) {
            if(piece.getName().equals("K")) {
                return piece.getLocation();
            }
        }
        return null;
    }

    public void setInCheck(boolean inCheck) {
        this.inCheck = inCheck;
    }
}
