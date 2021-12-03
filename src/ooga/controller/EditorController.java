package ooga.controller;

import ooga.Location;
import ooga.model.Board;
import ooga.model.PieceInterface;
import ooga.model.PlayerInterface;
import ooga.view.View;
import ooga.view.EditorView;

import java.util.ArrayList;
import java.util.List;

public class EditorController extends Controller {
    
    private View editorView;
    
    @Override
    public boolean canMovePiece(Location location) {
        List<Location> locations = new ArrayList<>();
        for(PlayerInterface player : model.getPlayers()) {
            for(PieceInterface piece : player.getPieces()) {
                locations.add(piece.getLocation());
            }
        }
        return location.inList(locations);
    }

    @Override
    protected void start() {
        model = new Board(boardBuilder.getInitialPlayers());
        editorView = new EditorView(this);
    }

    @Override
    public void movePiece(Location start, Location end) {
        model.movePiece(start, end);
    }

    @Override
    public List<Location> getLegalMoves(Location location) {
        return null;
    }
}