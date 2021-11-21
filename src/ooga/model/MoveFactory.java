//package ooga.model;
//
//import ooga.Location;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MoveFactory {
//
//    private List<PlayerInterface> players;
//    private final int row;
//    private final int col;
//
//    public MoveFactory(List<PlayerInterface> players, int row, int col) {
//        this.players = players;
//        this.row = row;
//        this.col = col;
//    }
//
//    public List<Location> findLegalMoves(PlayerInterface player, PieceInterface piece) {
//        List<Location> legalMoves = new ArrayList<>();
//        // try all moves see if king in check
//        for(Location location : findAllMoves(piece)) {
//            if(tryMove(player, piece, location)) {
//                legalMoves.add(location);
//            }
//        }
//
//        return legalMoves;
//    }
//
//    /**
//     * Returns list of all possible locations of a given piece based on other pieces disregarding checks
//     * @param piece
//     * @return
//     */
//    private List<Location> findAllMoves(PieceInterface piece) {
//        Location location = piece.getLocation();
//        List<Location> moves = findMoves(piece, piece.getTakeVectors(), location, true);
//        moves.addAll(findMoves(piece, piece.getMoveVectors(), location, false));
//        return moves;
//    }
//
//    /**
//     * Returns list of possible locations based on piece movement vector or take vector
//     * @param piece
//     * @param vectors
//     * @param location
//     * @param take
//     * @return
//     */
//    private List<Location> findMoves(PieceInterface piece, List<Vector> vectors, Location location, boolean take) {
//        List<Location> moves = new ArrayList<>();
//
//        for (Vector vector : vectors) {
//            int pieceRow = location.getRow() + vector.getdRow();
//            int pieceCol = location.getCol() + vector.getdCol();
//
//            // while the new locations are in bounds
//            while (inBounds(pieceRow, pieceCol)) {
//                Location potentialLocation = new Location(pieceRow, pieceCol);
//                PieceInterface potentialPiece = pieceAt(potentialLocation);
//
//                //new spot has piece
//                if (potentialPiece != null) {
//                    if (potentialPiece.getTeam().equals(piece.getTeam())) { // same team break
//                        break;
//                    }
//                    if(take) { // if u can take that piece
//                        moves.add(new Location(pieceRow, pieceCol));
//                    }
//                    break;
//                } else { // new spot is empty and only calculating movement
//                    if(!take) {
//                        moves.add(new Location(pieceRow, pieceCol));
//                    }
//                }
//
//                if (piece.isLimited()) {
//                    break;
//                }
//
//                pieceRow += vector.getdRow();
//                pieceCol += vector.getdCol();
//            }
//        }
//        return moves;
//    }
//
//    /**
//     * Helper function to see if potential move is legal
//     * @param player is current player moving the piece
//     * @param piece is the piece player is attempting to move
//     * @param location is the location the player is attempting to move the piece to
//     * @return if the move is legal or not
//     */
//    private boolean tryMove(PlayerInterface player, PieceInterface piece, Location location) {
//        PlayerInterface otherPlayer = findNextPlayer(player);
//        PieceInterface king = player.getKing();
//        Location pieceLocation = new Location(piece.getLocation().getRow(), piece.getLocation().getCol());
//        PieceInterface takenPiece = null;
//        List<Location> takenPieceLegalMoves = new ArrayList<>();
//
//        // theoretically move piece to location
//        player.tryMove(piece, location);
//        if(otherPlayer.getPiece(location) != null) { //take piece if exists
//            takenPiece = otherPlayer.getPiece(location);
//            takenPieceLegalMoves = otherPlayer.getLegalMoves(takenPiece.getLocation());
//            otherPlayer.removePiece(location);
//        }
//
//        // if the king is in check, undo move and return false
//        if(inCheck(king, otherPlayer.getPieces())) {
//            undoTryMove(player, piece, pieceLocation, takenPiece, takenPieceLegalMoves);
//            return false;
//        }
//
//        //otherwise undo the move and return true
//        undoTryMove(player, piece, pieceLocation, takenPiece, takenPieceLegalMoves);
//        return true;
//    }
//
//    /**
//     * Undo the tried move after trying the move
//     * @param player is current player moving the piece
//     * @param piece is the piece player moved
//     * @param pieceLocation is the original location the player is attempting to move the piece to
//     * @param takenPiece is the piece that was taken during the turn, if a piece was taken
//     */
//    private void undoTryMove(PlayerInterface player, PieceInterface piece, Location pieceLocation, PieceInterface takenPiece, List<Location> takenPieceLegalMoves) {
//        player.tryMove(piece, pieceLocation);
//        if (takenPiece != null) {
//            findNextPlayer(player).addPiece(takenPiece);
//            findNextPlayer(player).setLegalMoves(takenPiece, takenPieceLegalMoves);
//        }
//    }
//
//    /**
//     * Checks if the king is under attack from enemy pieces
//     * @param king is the piece under sttack
//     * @param attackingPieces is the list of pieces attacking the king
//     * @return true if the king is under attack from list of pieces
//     */
//    public boolean inCheck(PieceInterface king, List<PieceInterface> attackingPieces) {
//        Location kingLocation = king.getLocation();
//
//        for(PieceInterface piece : attackingPieces) {
//            for(Location attackLocation : findAllMoves(piece)) {
//                if(kingLocation.equals(attackLocation)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    private PlayerInterface findNextPlayer(PlayerInterface currentPlayer) {
//        return players.get((players.indexOf(currentPlayer) + 1)% players.size());
//    }
//
//    public PieceInterface pieceAt(Location location) {
//        for(PlayerInterface player : players) {
//            if(player.getPiece(location) != null) {
//                return player.getPiece(location);
//            }
//        }
//        return null;
//    }
//
//    private boolean inBounds(int newRow, int newCol) {
//        return (newRow < row && newCol < col && newRow >= 0 && newCol >= 0); //FIXME: hardcoded row col
//    }
//
//}
