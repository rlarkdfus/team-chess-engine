package ooga.model;


import ooga.Location;
import ooga.Turn;
import ooga.model.Moves.Move;

import java.util.ArrayList;
import java.util.List;
import ooga.model.EndConditionHandler.EndConditionRunner;

//import static ooga.controller.BoardBuilder.DEFAULT_CHESS_CONFIGURATION;

public class Board implements Engine {

  private static final int ROWS = 8;
  private static final int COLS = 8;
  private static final int LAST_ROW = ROWS - 1;
  private static final int FIRST_ROW = 0;
  private static final String QUEEN = "Q";
  private static final String KING = "K";
  private static final String PAWN = "P";



  private List<PlayerInterface> players;
  private List<PieceInterface> allPieces;
  private EndConditionRunner endCondition;
  private int turnCount;
  private PlayerInterface currentPlayer;

  private List<Location> promotionSquares;
  private List<Location> timerSquares;
  private List<Location> skipSquares;

  public Board(List<PlayerInterface> players) {
    this.players = players;
    this.endCondition = new EndConditionRunner();
    turnCount = 0;
    allPieces = new ArrayList<>();
    for (PlayerInterface player : players) {
      allPieces.addAll(player.getPieces());
    }

    for (PieceInterface piece : allPieces) {
      piece.updateMoves(allPieces);
    }
//    System.out.println(this);
    updateLegalMoves();
    promotionSquares = new ArrayList<>();
    timerSquares = new ArrayList<>();
    skipSquares = new ArrayList<>();
    initializePowerUpSquares();
  }
  private void initializePowerUpSquares(){
    initializeTimeSquares();
    initializeSkipSquares();
    initializePromotionSquares();
  }

  private void initializePromotionSquares() {
        promotionSquares.add(new Location(4,0));
        promotionSquares.add(new Location(3,0));
  }

  private void initializeTimeSquares() {
//        timerSquares.add(new Location(4,0));
//        timerSquares.add(new Location(3,0));
//        timerSquares.add(new Location(2,0));

  }

  private void initializeSkipSquares() {
//        skipSquares.add(new Location(4,0));
  }

  /**
   * this method returns the list of all players
   *
   * @return
   */
  public List<PlayerInterface> getPlayers() {
    return players;
  }

  /**
   * this method sets the end conditions of the board
   *
   * @param endCondition
   */
  public void setEndCondition(EndConditionRunner endCondition) {
    this.endCondition = endCondition;
  }

  private void updateLegalMoves() {
    for (PieceInterface piece : allPieces) {
      piece.updateMoves(new ArrayList<>(allPieces));
    }
  }

  /**
   * Moves piece from start to end and updates the board
   *
   * @param start is piece initial location
   * @param end   is piece new location
   */
  public List<PieceInterface> movePiece(Location start, Location end) {
    // pause current player timer, start next player time
    PieceInterface piece = null;
    for (PieceInterface p : allPieces) {
      if (p.getLocation().equals(start)) {
        piece = p;
        break;
      }
    }

    Move move = piece.getMove(end);
    Turn turn = move.getTurn();
    move.executeMove(piece, allPieces, end);



    // remove piece from player if needed after turn
    for (Location removeLocation : turn.getRemoved()) {
      for (PlayerInterface player : players) {
        for (PieceInterface p : player.getPieces()) {
          if (p.getLocation().equals(removeLocation) && !p.equals(piece)) {
            player.removePiece(p);
          }
        }
      }
    }

    //Check for pawn promotion
    checkPromotion(piece, end);

    //Add time powerup
    checkTime(piece, end);
    // increment turn
    turnCount++;
    toggleTimers();

        checkSkip(piece, end);

    //update game data
    updateLegalMoves();
    return allPieces;
  }


  private void checkPromotion(PieceInterface piece, Location end) {
    //check pawn promotion specifically
    checkPawnPromotion(piece, end);

    //Check piece promotion specifically
    boolean promotionPieceHit =  false;
    for (Location promotionLocation : promotionSquares) {
//      System.out.println(promotionLocation);
      if (end.equals(promotionLocation)) {
        promotionPieceHit = true;
        promotePiece(piece, QUEEN);
        System.out.println("End square: " + end + " List of promotion squares: " + promotionSquares);
      }
    }
    if(promotionPieceHit){
      Location testLocation = new Location(4,0);
      System.out.println(promotionSquares.get(0).equals(end));
      System.out.println(promotionSquares.get(0).equals(testLocation));
      System.out.println(promotionSquares.remove(end));
      System.out.println(promotionSquares);
    }

  }

  private void checkTime(PieceInterface pieceInterface, Location end) {
    for (Location timerLocation : timerSquares) {
      if (end.equals(timerLocation)) {
        currentPlayer.incrementTime(100000);
        timerSquares.remove(timerLocation);
      }
    }
  }

  private void checkSkip(PieceInterface pieceInterface, Location end) {
    for (Location skipLocation : skipSquares) {
      if (end.equals(skipLocation)) {
        turnCount++;
        skipSquares.remove(skipLocation);
      }
    }
  }

  private void checkPawnPromotion(PieceInterface piece, Location end) {
    if (piece.getName().equals(PAWN)) {
      if (end.getRow() == FIRST_ROW || end.getRow() == LAST_ROW) {

//        System.out.println(currentPlayer.getPieces().size());
        promotePiece(piece, QUEEN);
//        System.out.println(currentPlayer.getPieces().size());
      }
    }
  }

    /**
     * pause current player timer, add increment, start next player time
     */
    private void toggleTimers() {
        PlayerInterface prevPlayer = findPlayerTurn(turnCount-1);
        PlayerInterface currPlayer = findPlayerTurn(turnCount);
        prevPlayer.toggleTimer();
        prevPlayer.incrementTimeUserInterface();
        currPlayer.toggleTimer();
    }

  private void promotePiece(PieceInterface pieceInterface, String newPieceName) {
    PieceInterface newPiece = null;
    try {
      newPiece = currentPlayer.createPiece(newPieceName);
    } catch (InvalidPieceException e) {
      e.printStackTrace();
    }

    currentPlayer.removePiece(pieceInterface);
    allPieces.remove(pieceInterface);

    newPiece.moveTo(pieceInterface.getLocation());
    allPieces.add(newPiece);
    currentPlayer.addPiece(newPiece);
//    System.out.println(this);
  }

  /**
   * see if the game is still running or if its over
   *
   * @return
   */
  @Override
  public GameState checkGameState() {
    return endCondition.satisfiedEndCondition(players);
  }

  /**
   * return a list of all legal moves for a piece at a location
   *
   * @param location
   * @return
   */
  public List<Location> getLegalMoves(Location location) {
    for (PieceInterface piece : allPieces) {
      if (piece.getLocation().equals(location)) {
        return piece.getEndLocations();
      }
    }
    return null;
  }

  /**
   * determine whether player selects their own piece on their turn
   *
   * @param location
   * @return
   */
  public boolean canMovePiece(Location location) {
    String turn = findPlayerTurn(turnCount).getTeam();
    for (PieceInterface piece : allPieces) {
      if (piece.getTeam().equals(turn) && piece.getLocation().equals(location)) {
        return true;
      }
    }

    return false;
  }

  private PlayerInterface findPlayerTurn(int turn) {
    currentPlayer = players.get((turn) % players.size());
    return currentPlayer;
  }

  /**
   * this method overrides toString and prints out the current board state in an easily digestable
   * format
   *
   * @return
   */
  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append("\t 0\t 1\t 2\t 3\t 4\t 5\t 6\t 7\n");
    for (int i = 0; i < 8; i++) {
      str.append(i + "\t|");
//            str.append("|");
      for (int j = 0; j < 8; j++) {
        Location location = new Location(i, j);
        boolean found = false;

        for (PieceInterface piece : allPieces) {
          if (piece.getLocation().equals(location)) {
            str.append(piece.toString() + "\t");
            found = true;
          }
        }
        if (!found) {
          str.append("\t");
        }
        str.append("|");
      }
      str.append("\n");
    }
    str.append("____________________________________\n");
    return str.toString();
  }
}
