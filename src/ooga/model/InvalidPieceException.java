package ooga.model;

public class InvalidPieceException extends Exception{
    public InvalidPieceException(Class<? extends Exception> reason) {
        System.out.println(reason);
    }

    public InvalidPieceException(String PieceName) {
        System.out.println(PieceName + "is not a valid initialized piece");
    }

    public InvalidPieceException(){
        this("Piece Doesn't exist");
    }
}
