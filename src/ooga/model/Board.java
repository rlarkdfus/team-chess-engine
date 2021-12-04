package ooga.model;


import ooga.Location;
import ooga.Turn;
import ooga.controller.InvalidPieceConfigException;
import ooga.model.Moves.Move;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import ooga.model.EndConditionHandler.EndConditionRunner;
import ooga.model.Powerups.PowerupInterface;
import ooga.model.Powerups.PromotePowerup;
import ooga.model.Powerups.TimerPowerup;

//import static ooga.controller.BoardBuilder.DEFAULT_CHESS_CONFIGURATION;

public class Board implements Engine {

  public static final int ROWS = 8;
  public static final int COLS = 8;
  public static final int LAST_ROW = ROWS - 1;
  public static final int FIRST_ROW = 0;
  public static final String QUEEN = "Q";
  public static final String KING = "K";
  public static final String PAWN = "P";



  private List<PlayerInterface> players;
  private List<PieceInterface> allPieces;
  private EndConditionRunner endCondition;
  private int turnCount;
  private PlayerInterface currentPlayer;


  private PromotePowerup promotePowerup;
  private TimerPowerup timerPowerup;
  List<PowerupInterface> powerupInterfaces;

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

    updateLegalMoves();

    powerupInterfaces = new ArrayList<>();
    List<Location> testLocations = new ArrayList<>();
    testLocations.add(new Location(4,2));
    testLocations.add(new Location(4,0));


    promotePowerup = new PromotePowerup(testLocations);
    timerPowerup = new TimerPowerup(testLocations);
    powerupInterfaces.add(promotePowerup);
    powerupInterfaces.add(timerPowerup);

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
  public List<PieceInterface> movePiece(Location start, Location end) throws FileNotFoundException, InvalidPieceConfigException {
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
    ;

    for(PowerupInterface powerupInterface: powerupInterfaces){
      powerupInterface.checkPowerUp(piece,end,currentPlayer,allPieces);
    }


    // increment turn
    turnCount++;
    toggleTimers();


    //update game data
    updateLegalMoves();
    return allPieces;
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
