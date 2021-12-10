# OOGA Design Final
### Tarun Amasa tra23, Richard Deng rld39, Gordon Kim kk388, Samuel Li sbl28, Luis Pereda Amaya lip4, Albert Yuan aly11,

## Team Roles and Responsibilities

* Tarun Amasa Tarun - powerups, cheats, property files.

* Richard Deng - timer, view

* Gordon Kim - view, editor mode, model, move

* Samuel Li - model, puzzles, move, cheats

* Luis Pereda Amaya - login, boardbuilder, end condition, data files, saving/downloading files

* Albert Yuan - end game conditions, boardbuilder, end game screen


## Design goals

#### What Features are Easy to Add
* New End Conditions
* New Pieces (movement, value, how it takes opposing pieces)
* New Powerups

## High-level Design

#### Core Classes
* View - main display
* Boardbuilder
* Controller
* Board
* EndConditionInterface
* Move
* Powerup
* Player
* Piece

## Assumptions that Affect the Design

* json file keys are present, keys are named correctly, the existence of a data/chess/locations, data/chess/pieces, data/chess/rules,
data/chess/locations, and data/chess/powerups directories
* The team that goes first is at the bottom of the screen
* Upon a reset, the game reverts to default settings
* The user only speaks English, Spanish, French, Italian, or German
* There are only 5 types of end conditions - checkmate, elimination, location, puzzle, and timer
* Checkmate end condition is in any game type
* The existence of a default chess json with a corresponding csv, rules json, powerups json, and the piece jsons

#### Features Affected by Assumptions
* Incorrectly formatted json files will result in a error thrown to the view
* We only implemented English, Spanish, French, Italian, and German
* Custom pieces can’t be named bP,bK,bN,bQ,bR,bB,wP,wK,wN,wQ,wR,wB
* There can’t be a game that doesn’t have checkmate end condition in its rules
* The board initiates a default chess board
* Game csvs need a king on either side, and checkmate is part of every game


## Significant differences from Original Plan

* The most significant difference was the existence of a boardbuilder. We initially had parsers parse 
the file and then send the data to view and model for them to build objects. Instead we found that 
it’d be a lot easier to parse the file (in controller), build objects, and then send the built objects 
to the model and view.
* View got partially split up into UI classes like time, settings, etc.
* Model changed a bit to accept boardbuilder objects in the constructor, and it also moved all move 
logic to the Move class.
* Controller stayed mostly the same, but with many more methods added that transferred specific data 
between the model and view.


## New Features HowTo

#### Easy to Add Features
With our current design it would be pretty easy to add any variation to chess as long as there is a 
checkmate rule. We could add new types of pieces, new endconditions, or new powerups. If we wanted to 
add a similar board game like checkers, it wouldn’t be easy because at the moment we check if moving 
a piece will cause a check (which is illegal). This means we’ve hard coded a check-checker in our Move 
class logic, which would make it hard to implement other types of non-checkmate games.

#### Other Features not yet Done
* Move history
* No draw button
