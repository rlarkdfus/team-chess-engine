package ooga.model;

import ooga.Location;
import ooga.Turn;
import ooga.model.Moves.Move;
import java.util.ArrayList;
import java.util.List;
import ooga.model.Moves.MoveUtility;

public abstract class Board implements Engine {

    protected List<PlayerInterface> players;
    protected List<PieceInterface> allPieces;

    public Board(List<PlayerInterface> players) {
        this.players = players;
        allPieces = new ArrayList<>();
        for (PlayerInterface player : players) {
            allPieces.addAll(player.getPieces());
        }
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
     * Moves piece from start to end and updates the board
     *
     * @param start is piece initial location
     * @param end   is piece new location
     */
    public List<PieceInterface> movePiece(Location start, Location end) {
        PieceInterface piece = MoveUtility.pieceAt(start, allPieces);
        Move move = getMove(end, piece);
        move.executeMove(piece, allPieces, end);
        updatePlayerPieces(piece, move.getTurn());
        updateGameRules();
        updateLegalMoves();
        return allPieces;
    }

    protected abstract Move getMove(Location end, PieceInterface piece);

    private void updatePlayerPieces(PieceInterface piece, Turn turn) {
        for (Location removeLocation : turn.getRemoved()) {
            for (PlayerInterface player : players) {
                for (PieceInterface p : player.getPieces()) {
                    if (p.getLocation().equals(removeLocation) && !p.equals(piece)) {
                        player.removePiece(p);
                    }
                }
            }
        }
    }

    protected abstract void updateGameRules();

    protected void updateLegalMoves() {
        for (PieceInterface piece : allPieces) {
            piece.updateMoves(new ArrayList<>(allPieces));
        }
    }

    /**
     * see if the game is still running or if its over
     *
     * @return
     */
    @Override
    public abstract GameState checkGameState();

    /**
     * determine whether player selects their own piece on their turn
     *
     * @param location
     * @return
     */
    public abstract boolean canMovePiece(Location location);

    /**
     * return a list of all legal moves for a piece at a location
     *
     * @param location
     * @return
     */
    public abstract List<Location> getLegalMoves(Location location);

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

    //  private void initializePowerUpSquares(){
//    initializeTimeSquares();
//    initializeSkipSquares();
//    initializePromotionSquares();
//  }
//
//  private void initializePromotionSquares() {
//        promotionSquares.add(new Location(4,0));
//        promotionSquares.add(new Location(3,0));
//  }
//
//  private void initializeTimeSquares() {
////        timerSquares.add(new Location(4,0));
////        timerSquares.add(new Location(3,0));
////        timerSquares.add(new Location(2,0));
//
//  }
//
//  private void initializeSkipSquares() {
////        skipSquares.add(new Location(4,0));
//  }


//  private void checkPromotion(PieceInterface piece, Location end) {
//    //check pawn promotion specifically
//    checkPawnPromotion(piece, end);
//
//    //Check piece promotion specifically
//    boolean promotionPieceHit = false;
//    for (Location promotionLocation : promotionSquares) {
////      System.out.println(promotionLocation);
//      if (end.equals(promotionLocation)) {
//        promotionPieceHit = true;
//        promotePiece(piece, QUEEN);
//        System.out.println("End square: " + end + " List of promotion squares: " + promotionSquares);
//      }
//    }
//    if (promotionPieceHit) {
//      Location testLocation = new Location(4, 0);
//      System.out.println(promotionSquares.get(0).equals(end));
//      System.out.println(promotionSquares.get(0).equals(testLocation));
//      System.out.println(promotionSquares.remove(end));
//      System.out.println(promotionSquares);
//    }
//  }
//
//  private void promotePiece(PieceInterface pieceInterface, String newPieceName) {
//    PieceInterface newPiece = null;
//    try {
//      newPiece = currentPlayer.createPiece(newPieceName);
//    } catch (InvalidPieceException e) {
//      e.printStackTrace();
//    }
//
//    currentPlayer.removePiece(pieceInterface);
//    allPieces.remove(pieceInterface);
//
//    newPiece.moveTo(pieceInterface.getLocation());
//    allPieces.add(newPiece);
//    currentPlayer.addPiece(newPiece);
////    System.out.println(this);
//  }

//  private void checkPawnPromotion(PieceInterface piece, Location end) {
//    if (piece.getName().equals(PAWN)) {
//      if (end.getRow() == FIRST_ROW || end.getRow() == LAST_ROW) {
//
////        System.out.println(currentPlayer.getPieces().size());
//        promotePiece(piece, QUEEN);
////        System.out.println(currentPlayer.getPieces().size());
//      }
//    }
//  }
}
