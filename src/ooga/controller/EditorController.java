package ooga.controller;

import ooga.Location;
import ooga.controller.Config.Builder;
import ooga.controller.Config.InvalidPieceConfigException;
import ooga.controller.Config.PieceBuilder;
import ooga.controller.Config.PieceViewBuilder;
import ooga.model.*;
import ooga.view.View;
import ooga.view.EditorView;
import ooga.view.ViewInterface;
import ooga.view.boardview.PieceMenuView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class EditorController extends Controller {

    private List<PieceViewBuilder> pieces;

    @Override
    protected Engine initializeModel(Builder boardBuilder) {
        return new EditorBoard(boardBuilder.getInitialPlayers());
    }

    @Override
    protected ViewInterface initializeView(List<PieceViewBuilder> pieces) {
        ViewInterface view = new EditorView(this);
        view.initializeDisplay(pieces);
        return view;
    }

    @Override
    public void setInitialTime(int minutes) {

    }

    @Override
    public void setIncrement(int seconds) {

    }
}