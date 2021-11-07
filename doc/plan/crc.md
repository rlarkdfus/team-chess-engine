##Class Name
Piece
###Attributes
Int xPosition
Int yPosition
Int value
###Responsibilities
getPossibleMoves()
move()
isKilled()
##Subclasses
Chess(Pawn, knight, bishop, etc, override move methods)


## Rules Interface
### Attributes
Boolean win
Boolean tie
###

###Subclasses
Chess Interface, Checkers Interface, connect4?



##ClassName
Board (or Grid)
###Attributes
Int width
Int height
map<position, piece>
###Responsibilities
getLegalMoves()
movePiece()
selectPiece()

##ClassName
Square (cell?)
###Attributes
color
piece
###Responsibilities
assignPiece()


##Class Name
JSONparser
###Attributes
Scanner scanner
###Responsibilities
getProperties(FILE file)
jsonToString(FILE file)
createMap(JSONObject)

##Class Name
Controller
###Attributes
Board board
Model model
BoardDisplay display
JSONparser parser
Player currPlayer
###Responsibilities
runModel()
updateView()
changePlayer()ssswe
parseFile()ass

##Class Name
Player
###Attributes
Timer timer
Int score
Arraylist[] playersPieces
Arraylist[] capturedPieces
###Responsibilities
makeTurn()

## Class Name
Timer
###Attributes
Int secondsRemaining
Boolean timeUp
###Responsibilities
getTime()
timeUp()


## Class Name
PieceDisplay
###Attributes
ImageView image
###Responsibilities
setupDisplay()


## Class Name
BoardDisplay
###Attributes
PieceDisplay[][] pieceDisplayArray
###Responsibilities
updateBoardDisplay()


## Class Name
GameView
###Attributes
BoardDisplay boardDisplay
###Responsibilities
setupDisplay()

