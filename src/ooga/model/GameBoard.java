package ooga.model;

import ooga.Location;
import ooga.controller.Config.PieceBuilder;
import ooga.model.EndConditionHandler.EndConditionRunner;
import ooga.model.Moves.Move;
import ooga.model.Powerups.PowerupInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * @authors purpose - the gameboard is the playable board and keeps track of the pieces, players, and turns
 * assumptions - it assumes that the pieces and players are all valid
 * dependencies - it depends on EndConditionRunner, PlayerInterface, PowerupInterface,
 * Location, PieceInterface, and Move
 * usage - a user is able to call its methods to get and manage the moves of a piece
 */
public class GameBoard extends Board implements GameEngine, CheatInterface {
  private final EndConditionRunner endCondition;
  private int turnCount;
  private PlayerInterface currentPlayer;

  /**
   * the gameboard initializes the turns and the legal moves of each piece
   *
   * @param players      list of all the players
   * @param endCondition manages all end conditions
   * @param powerups     list of the powerups
   * @param bounds       bounds of the board
   */
  public GameBoard(List<PlayerInterface> players, EndConditionRunner endCondition, List<PowerupInterface> powerups, Location bounds) {
    super(players, bounds);
    this.endCondition = endCondition;
    this.powerupInterfaceList = powerups;
    turnCount = 0;
    for (PieceInterface piece : pieces) {
      piece.updateMoves(pieces);
    }
    updateLegalMoves();
    if (!players.isEmpty()) {
      this.currentPlayer = players.get(0);
    }
  }

  /**
   * get the move that will move a player to the end location
   *
   * @param end   end location for a move
   * @param piece the piece that is being moved
   * @return the move that moves a player to the end location
   */
  @Override
  protected Move getMove(Location end, PieceInterface piece) {
    return piece.getMove(end);
  }

  /**
   * checks for powerups and toggles timers
   *
   * @param piece piece to update rules
   */
  @Override
  protected void updateGameRules(PieceInterface piece) {
    PlayerInterface currentPlayer = findPlayerTurn(turnCount);
    for (PowerupInterface powerupInterface : powerupInterfaceList) {
      powerupInterface.checkPowerUp(piece, piece.getLocation(), currentPlayer, pieces);
    }
    System.out.println(currentPlayer.getTeam() + " " + currentPlayer.getScore());
    incrementTurn();
    toggleTimers();
  }

  /**
   * determines whether a piece at a location is eligible to be moved
   *
   * @param location location that a player selects
   * @return whether the piece at the location can be moved
   */
  @Override
  public boolean canMovePiece(Location location) {
    String turn = findPlayerTurn(turnCount).getTeam();
    for (PieceInterface piece : pieces) {
      if (piece.getTeam().equals(turn) && piece.getLocation().equals(location)) {
        return true;
      }
    }
    return false;
  }

  /**
   * return a list of all legal moves for a piece at a location
   *
   * @param location selected to move
   * @return list of all the end locations of the piece at location
   */
  @Override
  public List<Location> getLegalMoves(Location location) {
    for (PieceInterface piece : pieces) {
      if (piece.getLocation().equals(location)) {
        return piece.getEndLocations();
      }
    }
    return null;
  }

  /**
   * pause current player timer, add increment, start next player time
   */
  private void toggleTimers() {
    PlayerInterface prevPlayer = findPlayerTurn(turnCount - 1);
    PlayerInterface currPlayer = findPlayerTurn(turnCount);
    prevPlayer.toggleTimer();
    prevPlayer.incrementTimeUserInterface();
    currPlayer.toggleTimer();
  }

  private PlayerInterface findPlayerTurn(int turn) {
    currentPlayer = players.get((turn) % players.size());
    return players.get((turn) % players.size());
  }

  /**
   * determine the current running game state
   *
   * @return the current game state
   */
  @Override
  public GameState checkGameState() {
    return endCondition.satisfiedEndCondition(players);
  }


  private void incrementTurn() {
    turnCount++;
  }

  @Override
  public void incrementTurnCheat() {
    incrementTurn();
  }

  @Override
  public void addRandomQueenCheat() {
    PieceInterface newPiece = PieceBuilder.buildPiece(currentPlayer.getTeam(), Board.PIECES.getString("QUEEN"), this.randomEmptyLocation(), getBounds());
    currentPlayer.addPiece(newPiece);
    pieces.add(newPiece);
  }

  @Override
  public void moveKingRandomCheat() {
    List<PieceInterface> currentPlayerPieces = currentPlayer.getPieces();
    PieceInterface king = null;
    for (PieceInterface piece : currentPlayerPieces) {
      if (piece.getName().equals(Board.PIECES.getString("KING"))) {
        king = piece;
      }
    }
    king.moveTo(this.randomEmptyLocation());
  }

  @Override
  public void transformAllPawnsCheat() {
    List<PieceInterface> currentPlayerPieces = currentPlayer.getPieces();

    for (PieceInterface piece : currentPlayerPieces) {
      if (piece.getName().equals(Board.PIECES.getString("PAWN"))) {
        piece.transform(PieceBuilder.buildPiece(currentPlayer.getTeam(), Board.PIECES.getString("DEFAULT_PROMOTION"), piece.getLocation(), getBounds()));
      }
    }

  }

  @Override
  public void queenToPawnCheat() {
    PlayerInterface opponent = players.get((turnCount + 1) % players.size());
    List<PieceInterface> opponentPieces = opponent.getPieces();
    PieceInterface queen = null;
    for (PieceInterface piece : opponentPieces) {
      if (piece.getName().equals(Board.PIECES.getString("QUEEN"))) {
        queen = piece;
        queen.transform(PieceBuilder.buildPiece(opponent.getTeam(), Board.PIECES.getString("PAWN"), piece.getLocation(), getBounds()));
      }
    }
  }

  @Override
  public void addTimeCheat() {
    currentPlayer.incrementTime(600);
  }

  @Override
  public void removeRandomPieceCheat() {
    int randomIndex = (int) (Math.random() * currentPlayer.getPieces().size());
    PieceInterface randomPiece = currentPlayer.getPieces().get(randomIndex);
    if (randomPiece.getName().equals(Board.PIECES.getString("KING"))) {
      removeRandomPieceCheat();
    } else {
      currentPlayer.removePiece(randomPiece);
      pieces.remove(randomPiece);
      return;
    }
  }

  @Override
  public void decrementTimeCheat() {
    currentPlayer.incrementTime(-60);
  }

  //Make sure random piece isn't a king
  @Override
  public void addRandomPieceCheat() {
    List<String> pieceOptions = new ArrayList<>();
    pieceOptions.addAll(Board.PIECES.keySet());
    pieceOptions.remove("KING");

    int randomPieceIndex = (int) (Math.random() * pieceOptions.size());
    String randomPieceKey = pieceOptions.get(randomPieceIndex);
    String randomPieceName = Board.PIECES.getString(randomPieceKey);

    PieceInterface newPiece = PieceBuilder.buildPiece(currentPlayer.getTeam(), randomPieceName, this.randomEmptyLocation(), getBounds());
    currentPlayer.addPiece(newPiece);
    pieces.add(newPiece);

  }

  @Override
  public void addRandomRookCheat() {
    PieceInterface newPiece = PieceBuilder.buildPiece(currentPlayer.getTeam(), Board.PIECES.getString("ROOK"), this.randomEmptyLocation(), getBounds());
    currentPlayer.addPiece(newPiece);
    pieces.add(newPiece);
  }

  @Override
  public void addRandomBishopCheat() {
    PieceInterface newPiece = PieceBuilder.buildPiece(currentPlayer.getTeam(), Board.PIECES.getString("BISHOP"), this.randomEmptyLocation(), getBounds());
    currentPlayer.addPiece(newPiece);
    pieces.add(newPiece);
  }

  @Override
  public void addRandomKnightCheat() {
    PieceInterface newPiece = PieceBuilder.buildPiece(currentPlayer.getTeam(), Board.PIECES.getString("KNIGHT"), this.randomEmptyLocation(), getBounds());
    currentPlayer.addPiece(newPiece);
    pieces.add(newPiece);
  }
}
