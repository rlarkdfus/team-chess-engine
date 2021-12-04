package ooga.controller;

import ooga.Location;
import ooga.model.*;
import ooga.view.View;
import ooga.view.EditorView;

import java.util.ArrayList;
import java.util.List;

public class EditorController extends Controller {
    
    private View editorView;

    @Override
    protected void start() {
        model = new EditorBoard(boardBuilder.getInitialPlayers());
        editorView = new EditorView(this);
        editorView.initializeDisplay(boardBuilder.getInitialPieceViews());
    }

    @Override
    public void movePiece(Location start, Location end) {
        List<PieceViewBuilder> pieceViewList = new ArrayList<>();
        for (PieceInterface piece : model.movePiece(start, end)) {
            pieceViewList.add(new PieceViewBuilder(piece));
        }
        editorView.updateDisplay(pieceViewList);
    }

    @Override
    public void setInitialTime(int minutes) {

    }

    @Override
    public void setIncrement(int seconds) {

    }
}