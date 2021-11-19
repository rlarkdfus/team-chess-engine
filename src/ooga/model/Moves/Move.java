package ooga.model.Moves;

// 1. Calculate the possible moves in Move (return List<Locations> legalmoves) (drawback would be passing in a copy of the board)

public abstract class Move {

//    // regular move
//    public List<Location> getMoves(List<Location> allyPieces, List<Location> enemyPieces, boolean take) {
//        List<Location> moveLocations = new ArrayList<>();
//        empty square : List<Locations>
//        enemy squares : List<Location>
//
//        if(take) {
//            moveLocations.addAll(enemyTakeLocations);
//        }
//        for(Location location : findMoves()) {
//            if((pieceAt(location) == null) == take) {
//                moveLocations.add(location);
//            }
//        }
//        return moveLocations;
//    }
//
//    protected boolean inBounds(int newRow, int newCol) {
//        return (newRow < 8 && newCol < 8 && newRow >= 0 && newCol >= 0); //FIXME: hardcoded row col
//    }
//
//    protected abstract void executeMove();

//    protected abstract List<Location> findMoves();

    //List<List<Location>>
  public void setMove(int dx, int dy){
    //todo
  }
}
