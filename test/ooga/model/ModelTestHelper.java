package ooga.model;

import ooga.Location;
import ooga.controller.Config.BoardBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import ooga.model.Powerups.PowerupInterface;

public class ModelTestHelper {
    public static final File DEFAULT_CHESS_CONFIGURATION = new File("data/chess/defaultChess.json");

    public static Engine createBoard() {
        BoardBuilder boardBuilder = new BoardBuilder(DEFAULT_CHESS_CONFIGURATION);
        List<PowerupInterface> powerups = boardBuilder.getPowerupsHandler();
        return new GameBoard(boardBuilder.getInitialPlayers(), boardBuilder.getEndConditionHandler(), powerups);
    }

    public static List<Location> getPieceLocations(List<PieceInterface> pieces) {
        List<Location> locations = new ArrayList<>();
        for(PieceInterface piece : pieces) {
            locations.add(piece.getLocation());
        }
        return locations;
    }
}
