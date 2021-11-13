package ooga.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Player implements PlayerInterface {
    List<PieceInterface> remainingPieces;
    private String team;
    private int secondsLeft;
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

  public void startTimer() {
    secondsLeft = 30;
    setTimer();
  }
  public int getSecondsLeft() {
    return secondsLeft;
  }

  private void setTimer() {
      Timer timer = new Timer();
      TimerTask task = new TimerTask() {
        @Override
        public void run() {
          secondsLeft--;
        }
      };
      timer.scheduleAtFixedRate(task, 1000, 1000);
    }
}
