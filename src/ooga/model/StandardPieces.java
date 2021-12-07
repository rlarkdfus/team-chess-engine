package ooga.model;

public enum StandardPieces {
    KING("K"),
    QUEEN("Q"),
    BISHOP("B"),
    ROOK("R"),
    PAWN("P");


    private String pieceName;

    StandardPieces(String pieceName) {
        this.pieceName = pieceName;
    }

    public String getName(){
        return pieceName;
    }
}

