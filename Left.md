# CS-230 Functional Spec

Code stuff
  Better exception handling.
  
## Overview
// Zhan
* 2-4 Player
// This meeting
Theme: Getaway: All bank robber racing to a safe house.
// Sprint 3
* Advance Features that are not possible with physical board games
// Josh and Atif
* Message of the day pulled from http://cswebcat.swan.ac.uk
// David. Front end. Make a game finish screen that goes back to the main menu.

* Play end when a player lands on the goal tile.
// Sprint 3
* Extra Features

## Overall idea and components

// George
* Action tiles can be renamed
// Josh  + Atif
* The 3x3 area around these tiles are now fixed tiles including center(frozen)
// Josh + Atif
* Lasts until at the start of your next turn


// Josh + Atif
* Game Board
    * Any space that would push a fixed tile.
    * Any space that would push a frozen tile

// George.
  * There are fixed tiles
  * game must indicate the fixed tiles

// Daniel
* Leader board
  * shows best players on each game board.
  * Don't show player that haven't played that board.
  * lists in order of wins.

## Game play

### Setup
// Zhan
* User selects player profile
* Place the player pieces for the number of people paying
* Choose who goes first

### Turn
// George Frontend
// Brandon
1. Draw a tile
   * if Action tile, keep hold of it hidden, move to step 2.(you cannot play it this turn)
// 
// Get playable action tiles.
2. Playing an action tile
   * You cannot play an action tile you have drawn this turn
   * You may choose to play an action tile.
3. Moving
   3. only if not on fire
4. End turn
   * The play move to the player to the left(?)



