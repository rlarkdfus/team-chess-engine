package ooga.model;

import ooga.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player implements PlayerInterface {
    private Map<Integer, Boolean> pieceIDandState;
    private Map<PieceInterface, List<Location>> remainingPieces;
    //Keep track of all their killed pieces
    private List<PieceInterface> killedPieces;
    private final String team;
    private int score;

    //TODO: Chess timer

    public Player(String team) {
        this.team = team;
        remainingPieces = new HashMap<>();
//        inCheck = false;
        score = 0;
        pieceIDandState = new HashMap<>();
        this.killedPieces = new ArrayList<>();
    }

    /**
     * remove a pice from the player's posession
     * @param location
     */
    public void removePiece(Location location){
        for(PieceInterface piece : remainingPieces.keySet()) {
            if(piece.getLocation().equals(location)) {
                piece.setEliminated(true);
                pieceIDandState.put(piece.getUniqueId(), piece.getEliminatedState());
                killedPieces.add(piece);
//                System.out.println(piece.getTeam() + " " +  piece.getUniqueId() + piece.getEliminatedState());
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
        pieceIDandState.put(piece.getUniqueId(),piece.getEliminatedState());
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

    public PieceInterface getKing(){
        for(PieceInterface piece : remainingPieces.keySet()) {
            if(piece.getName().equals("K")) {
                return piece;
            }
        }
        return null;
    }

    @Override
    public void movePiece(PieceInterface piece, Location end) {
        piece.moveTo(end);
    }
    
    @Override
    public void tryMove(PieceInterface piece, Location end) {
        piece.tryMove(end);
    }

    @Override
    public PieceInterface getPiece(Location location) {
        for(PieceInterface piece : remainingPieces.keySet()) {
            if(piece.getLocation().equals(location)) {
                return piece;
            }
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
