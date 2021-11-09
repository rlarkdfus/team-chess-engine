## Backlog

#### use case 1: move a piece to empty square
```java
// occurs inside BoardView
void useCase1() {
    start = new Location(startX, startY);
    end = new Location(endX, endY)
    controller.movePiece(start, end);
}
```
#### use case 2: move a piece and take
```java
// occurs inside BoardView
void useCase() {
    start = ...;
    end = ...;
    controller.movePiece(start, end);
}

```

#### use case 3: Current game ends based on game rules

```java
// occurs inside
void useCase3() {
    if (controller.gameFinished() != -1) {
        displayGameResult(controller.gameFinished());
    }
}
```

#### use case 4: User starts a new game
```java
// occurs inside view, but would be called from controller
void useCase4() {
    boardView.initializeBoardView();
}
```

#### use case 5: User changes colors of board
```java
// occurs inside view, but would be called from controller  
void useCase5() {
    boardView.changeColors(color1, color2);
}
```

#### use case 6: User selects a piece, view shows legal moves
```java
// occurs inside view, but would be called from controller
void useCase6() {
    boardView.showLegalMoves(PieceView);
}
```


#### use case 7: user closes program
```java
void useCase7() {
    sys.exit();
}
```


#### use case 8: user offers draw
```java
void useCase8() {
    currentPlayer.offerDraw();
}
```

#### use case 9: user loads a file
```java
void useCase9() {
controller.loadFile();
}
``

#### use case 10: player 1 times out
```java
void useCase10() {
    // -1: running game
    // 0: draw
    // 1: decisive
    int gameState = -1;
    if (player1.timeRemaining == 0 || player2.timeRemaining == 0 || player1.checkmate() || player2.checkmate()) {
        gameState = 1;
    }
    if (draw) {
        gameState = 0;
    }
}
```

#### use case 11: player 2 times out
```java
void useCase11() {
    // -1: running game
    // 0: draw
    // 1: decisive
    int gameState = -1;
    if (player1.timeRemaining == 0 || player2.timeRemaining == 0 || player1.checkmate() || player2.checkmate()) {
        gameState = 1;
    }
    if (draw) {
        gameState = 0;
    }
}
```


#### use case 12: Player 1 checkmates
```java
void useCase12() {
    // -1: running game
    // 0: draw
    // 1: decisive
    int gameState = -1;
    if (player1.timeRemaining == 0 || player2.timeRemaining == 0 || player1.checkmate() || player2.checkmate()) {
        gameState = 1;
    }
    if (draw) {
        gameState = 0;
    }
}
```

#### use case 13: Player 2 checkmates
```java
void useCase13() {
    // -1: running game
    // 0: draw
    // 1: decisive
    int gameState = -1;
    if (player1.timeRemaining == 0 || player2.timeRemaining == 0 || player1.checkmate() || player2.checkmate()) {
        gameState = 1;
    }
    if (draw) {
        gameState = 0;
    }
}
```

#### use case 14: Player 1 check
```java
void useCase14(){
    if (player1.inCheck) {
        Board.validMoves.get(king);
    }
}
```

#### use case 15: user clicks on empty square
```java
Void useCase15() {
    if(Pieceview.getImage != null && Piece.owner == player) {
        BoardView.displayValidMoves();
    }
}
```
#### use case 16: user clicks on opponent’s piece
```java
Void useCase16() {
    if(Pieceview.getImage != null && Piece.owner == player) {
        BoardView.displayValidMoves();
    }
}
```

#### use case 17: user clicks on own piece
```java
Void useCase17() {
    if(Pieceview.getImage != null && Piece.owner == player) {
        BoardView.displayValidMoves();
    }
}
```

#### use case 18: user clicks on valid position after clicking on new piece
```java
Void useCase18() {
    if(startLocation == null) {
        startLocation = new Location(x, y)
    } else if(piece.getLegalMoves().contains(Location(x, y)) {
        endLocation = new Location(x, y);
        controller.movePiece(startLocation, endLocation);
    } else {
        startLocation == null;
    }
}
```

#### use case 19: user clicks on invalid position after clicking on new piece
```java
Void useCase19() {
    if(startLocation == null) {
        startLocation = new Location(x, y)
    } else if(piece.getLegalMoves().contains(Location(x, y)) {
        endLocation = new Location(x, y);
        controller.movePiece(startLocation, endLocation);
    } else {
        startLocation == null;
    }
}
```

#### use case 20: user uploads valid initial configuration - in controller
```java
Void usecase20(File file) {
    parser.parseFile(file);
}
```

#### use case 21: user uploads invalid initial configuration
```java
void useCase21() {
    view.showError("Invalid configuration!");
}
```
#### use case 22: user plays with default configuration
```java
void useCase22(){
    new board(default stuff);
}
```
#### use case 23: user changes game type to connect4
```java
Void useCase25() {
    connect4Button.setOnClick(e -> {
        myGameType = 2;
        hasGameTypeChanged = true;
    }
}
```
#### use case 24: user changes game type to checkers
```java
void useCase24() {
    checkersButton.setOnClick(e ->  {
        myGameType = 1;
        hasGameTypeChanged = true;
    }
}
```
#### use case 25: user changes game type to invalid game
```java
void useCase25() {
    try:
        checkersButton.setOnClick(e ->  {
        myGameType = 2;
        hasGameTypeChanged = true;
    catch:
        throw new Exception(invalidGame);
}
```
#### use case 26: user changes piece color
```java
Void usecase26(){
    board.setColor(color, color);
}
```

#### use case 27: user moves a piece to an occupied square
```java
void useCase27() {
    start = new Location(startX, startY);
    end = new Location(endX, endY)
    controller.movePiece(start, end);
}
```

#### use case 28: user moves a rook
```java
void useCase28() {
    start = new Location(startX, startY);
    end = new Location(endX, endY);
    controller.movePiece(start, end);
}
```
#### use case 29: user moves a king
```java
void useCase29() {
    start = new Location(startX, startY);
    end = new Location(endX, endY)
    controller.movePiece(start, end);
}
```

#### use case 30: user moves a knight
```java
void useCase30() {
    start = new Location(startX, startY);
    end = new Location(endX, endY)
    controller.movePiece(start, end);
}
```

#### use case 31: user moves a queen
```java
void useCase31() {
    start = new Location(startX, startY);
    end = new Location(endX, endY)
    controller.movePiece(start, end);
}
```

#### use case 32: user moves a bishop
```java
void useCase32() {
    start = new Location(startX, startY);
    end = new Location(endX, endY)
    controller.movePiece(start, end);
}
```
#### use case 33: user moves a pawn
```java
void useCase33() {
    start = new Location(startX, startY);
    end = new Location(endX, endY)
    controller.movePiece(start, end);
}
```
#### use case 34: user changes increment
```java
void useCase34() {
    controller.setIncrement(newIncrement);
}
```
#### use case 35: user sets time limit
```java
void useCase35() {
    controller.setTimeLimit(newTimeLimit);
}
```
#### use case 36: user changes language
```java
Void useCase36() {
    resourceBundle = “path/Spanish.properties”;
}
```