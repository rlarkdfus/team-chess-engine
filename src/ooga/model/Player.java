package ooga.model;

import ooga.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player implements PlayerInterface {
    private Map<PieceInterface, List<Location>> remainingPieces;
    private final String team;
    private int score;

    //TODO: Chess timer

    public Player(String team) {
        this.team = team;
        remainingPieces = new HashMap<>();
        score = 0;
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

    public void removePiece(PieceInterface piece){
        remainingPieces.remove(piece);
        score -= piece.getScore();
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

    public List<Location> getLegalMoves(Location location){
        return remainingPieces.get(getPiece(location));
    }

    public void setLegalMoves(PieceInterface piece, List<Location> moves){
        remainingPieces.put(piece, moves);
    }

    private void calculateScore(){
        for(PieceInterface piece: remainingPieces.keySet()){
            score += piece.getScore();
        }
    }
}
