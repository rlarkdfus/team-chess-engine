package ooga.model;

public class InvalidPieceException extends Exception{

    public InvalidPieceException(String PieceName) {
        System.out.println(PieceName + "is not a valid initialized piece");
    }

}
