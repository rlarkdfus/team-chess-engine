package ooga.view.ui;

import javafx.scene.layout.StackPane;
import ooga.controller.Controller;
import ooga.view.PieceView;
import ooga.view.ViewController;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class PieceViewMenuUI extends GridPane implements UIInterface {
    
    private Controller controller;
    private ViewController viewController;
    private List<Set<PieceViewHolder>> pieceViewList;
    private PieceView selectedPiece;
    
    public PieceViewMenuUI(Controller controller, ViewController viewController) {
        this.controller = controller;
        this.viewController = viewController;
        this.pieceViewList = new ArrayList<>();
    }
    
    @Override
    public void createUI() {
        int index = 0;
        for(int i = 0; i < pieceViewList.size(); i++) {
            for(PieceViewHolder pieceView : pieceViewList.get(i)) {
                this.add(pieceView, index, i);
                index++;
            }
            index = 0;
        }
    }
    
    private void selectPiece(PieceView pieceView) {
        selectedPiece = pieceView;
    }
    
    private class PieceViewHolder extends StackPane {
        
        private PieceView pieceView;
        
        private PieceViewHolder() {
            this.pieceView = pieceView;
            this.getChildren().add(pieceView);
            this.setOnMouseClicked(e -> selectPiece(pieceView));
        }
    }
}
