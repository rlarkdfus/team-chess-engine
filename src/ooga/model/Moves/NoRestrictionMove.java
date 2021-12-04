package ooga.model.Moves;

import ooga.Location;
import ooga.model.PieceInterface;

import java.util.ArrayList;
import java.util.List;

public class NoRestrictionMove extends TranslationMove {

    public NoRestrictionMove(int dRow, int dCol, boolean take, boolean limited) {
        super(dRow, dCol, take, limited);
    }

    @Override
    public List<Location> findAllEndLocations(PieceInterface piece, List<PieceInterface> pieces) {
        List<Location> locations = new ArrayList<>();
        int row = 0;
        int col = 0;
        while (MoveUtility.inBounds(row, col)) {
            while (MoveUtility.inBounds(row, col)) {
                locations.add(new Location(row, col));
                col++;
            }
            col = 0;
            row++;
        }
        System.out.println("move size: " + locations.size());

        return locations;
    }

    @Override
    protected boolean isLegal(PieceInterface piece, Location potentialLocation, List<PieceInterface> pieces) {
        return true;
    }
}
