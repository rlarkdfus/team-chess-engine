package ooga.model;

import ooga.Location;
import ooga.controller.BoardBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ModelTestHelper {
    public static final File DEFAULT_CHESS_CONFIGURATION = new File("data/chess/defaultChess.json");

    public static Engine createBoard() {
        BoardBuilder boardBuilder = new BoardBuilder(DEFAULT_CHESS_CONFIGURATION);
        return new GameBoard(boardBuilder.getInitialPlayers());
    }

    public static List<Location> getPieceLocations(List<PieceInterface> pieces) {
        List<Location> locations = new ArrayList<>();
        for(PieceInterface piece : pieces) {
            locations.add(piece.getLocation());
        }
        return locations;
    }
}
