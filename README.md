ooga
====

This project implements a player for multiple related games.

Names: Tarun Amasa (tra23), Richard Deng (rld39), Gordon Kim (kk388), Samuel Li (sbl28), Luis Pereda 
Amaya (lip4), Albert Yuan (aly11)

### Timeline

Start Date: 11/10

Finish Date: 12/6

Hours Spent: 150

### Primary Roles

Tarun - powerups
Richard - view, all things timer related, 
Gordon - view, editor mode, model, moves
Sam - model, endconditions, moves
Luis - login, loading and saving games, boardbuilder and all things config
Albert - boardbuilder and all things config, endconditions

### Resources Used

all piece image files were taken from Lichess's open source repository:
https://github.com/ornicar/lila/tree/master/public/piece

### Running the Program

Main class: Main

Data files needed: 
* data/chess/locations/chess.csv
* data/chess/pieces/*
* data/chess/powerups/noPowerups.json
* data/chess/profiles/profiles.json
* data/chess/rules/default.json
* data/defaultChess.json
* src/images/*

Features implemented:
* Users can pick Chess or Editor Variation
* Users can create their own pieces, powerup locations, end conditions, and 
board layouts
* Users can change the piece style, board color, language, and theme

Cheat Codes:
* 

### Notes/Assumptions

Assumptions or Simplifications:
* json file contents
    * keys are present, keys are named correctly, the existence of a data/chess/locations, data/chess/pieces, data/chess/rules,
      data/chess/locations, and data/chess/powerups directories,
* White always goes first and is at the bottom of the screen
* Upon a reset, the game reverts to default settings
* The user only speaks English, Spanish, French, Italian, or German

Interesting data files:
* data/chess/variantKillPawns - kill 3 pawns to win the game
* data/chess/variant4PawnLocations - move 4 pawns to the 4 left most squares on the 4th row from the bottom 
to win the game
* data/chess/Stalemate - test out stalemate my moving the bottom right rook to the left

Known Bugs:
* 

Noteworthy Features:
* Multiple different type of checkmates - regular, eliminating a list of pieces, positioning certain 
pieces in specific locations
* multiple different type of powerups - promotion, timer bonuses
* All types of chess moves (checking, en passant) are functional
* An editor mode that allows for the user to freely add and remove pieces on the board
* 

### Impressions


