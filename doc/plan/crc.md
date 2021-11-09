# APIs
>Builder

>Controller

>Parser

>Board

>Engine

>Piece

>BoardView

>PieceView


## Class Name
Movable interface
### Responsibilities
Get location
Boolean isValidMove()
SetLocation

## Class Name
Location
##Attribute
### Responsibilities

## Class Name 
Constraints interface
## Responsibilities
SetMoves()
setValue()

## Class Name
Abstract Piece implements Movable Interface, ConstraintsInterfaces
### Attributes
Location getLocationg
Int piece color
Int value
### Responsibilities
getPossibleMoves()
move()
isKilled()
## Subclasses
Chess(Pawn, knight, bishop, etc, override move methods)


## Rules Interface
### Attributes
Boolean win
Boolean tie

### Subclasses
Chess Interface, Checkers Interface, connect4?



## ClassName
Board (or Grid)
### Attributes
Int width
Int height
map<position, piece>
### Responsibilities
getLegalMoves()

## Class Name
JSONparser
### Attributes
Scanner scanner
### Responsibilities
getProperties(FILE file)
jsonToString(FILE file)
createMap(JSONObject)

## Class Name
GameBuilder
### Attributes
map<String, String>
### Responsibilities
createGame()

## Class Name
Controller

### Attributes
Board board
Model model
BoardDisplay display
JSONparser parser
Player currPlayer
View view

### Responsibilities

changePlayer()
parseFile()ass
movePiece()
selectPiece()


## Class Name 
Player
### Attributes
Timer timer 
Int score
Arraylist[] playersPieces
Arraylist[] capturedPieces
###Responsibilities
makeTurn()

## Class Name
Timer 
### Attributes
Int secondsRemaining
Boolean timeUp
###Responsibilities
getTime()
timeUp()


## Class Name
PieceDisplay
### Attributes
ImageView image
### Responsibilities
setupDisplay()


## Class Name
BoardDisplay
###Attributes
PieceDisplay[][] pieceDisplayArray
### Responsibilities
updateBoardDisplay()

## Class Name
SquareDisplay (or CellDisplay)
### Attributes
Color
PieceDisplay
### Responsibilities
updateBoardDisplay()


## Class Name
GameView
### Attributes
BoardDisplay boardDisplay
SettingsPanelView settingsPanel
CurrentGameConfigurationView currentGame
### Responsibilities
setupDisplay()

## Class Name
SettingsPanelView
### Attributes
List<Game variations>
double time
double increment
List<Color> boardColor
List<piecestyle?> pieceStyle
### Responsibilities
createNewGame()

## Class Name
CurrentGameConfigurationView
### Attributes
Variation
Time Control
Board color
Piece style
### Responsibilities
updateDisplay()

## Class Name
GameInfoView
### Attributes
black/white timer 
Move history
Resign button
Draw button
Download current game
### Responsibilities
setupDisplay()
