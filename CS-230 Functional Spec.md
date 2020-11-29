# CS-230 Functional Spec

## Overview

* 2-4 Player
* Unique Theming (Name and theme)
* Advance Features that are not possible with physical board games
* Many boards available to play
* Players can save game state at any time
* Message of the day pulled from http://cswebcat.swan.ac.uk
* Play end when a player lands on the goal tile.
* Extra Features.
* Save and load games.

## Overall idea and components

* The game is played on a board with various tiles and player pieces.

* Tiles are all the same size

* Two main tile variations
* Action tiles can be renamed

  * Action Tiles
    * Ice tiles
      * Choose a square on the board
      * The 3x3 area around these tiles are now fixed tiles including center(frozen)
      * Lasts until at the start of your next turn
    * Fire tiles
      * Choose a square on the board
        * Can't be placed in such a way that a player piece is set on fire.
      * The 3x3 area around these tiles are now on fire including center
      * if on file they cannot be stepped on
      * Lasts until the end of your next turn
    * Double action tiles
      * You may move again.
    * Backtrack action tiles
      * Choose an opponent
      * Their player piece moves back to where they were two turns ago.
      * provided that this tile is not on file.
        1. Is there previous tile on fire?
           1. Yes -> Don't move
           2. No, Is the location they were two turns ago on fire?
              1. Yes -> move them back one space
              2. No -> Move them back two spaces
      * Can only be applied once to each player.
  * Floor Tiles
    * Straight
    * Corner 
    * T-Shape 
    * Goal
    * is Fixed?
    * is Frozen until
    * is on fire?
    * is on fire until

* Game Board

  * Fixed Rectangular size

  * Tiles can be placed on

  * tiles can be slid in from the edge, 

    * pushing all the tiles and player pieces in a row or column along one space
    * Any space that would push a fixed tile.
    * Any space that would push a frozen tile
    * 

  * There are fixed tiles

    * Each one 

  * Has a exact set of tiles (in addition to the fixed tiles) including action tiles

  * game must indicate the fixed tiles

  * Game must indicate available places to push tiles

  * known four starting positions

  * Each board is stored in its own simple ASCII file.
  * Player pieces that are pushed off are placed on the oppesite side of the board
  * tiles slid off are put in the silk bag

  * ```
    7,3  <- width  height
    5    <- Number of fixed tiles
    0,0,CORNER ,2 <- Each fixed tile
    6,0,CORNER ,1 <- x,y, type, rotation
    0,2,CORNER ,3
    6,2,CORNER ,0
    3,3,GOAL ,0
    ```

  * Must have a fixed goal tile (not in spec)

  * Unique identifier (not in spec)

* **SILK BAG**

  * Contains tiles
  * Floor tiles can be drawn

* Players

  * Each has one player piece
  * Has profile
    * Name
    * Number of games played
    * number of wins
    * number of loses

* Player piece

  * Can stand on a tile
  * Can move to adjacent tiles 
    * in any of the four cardinal direction
    * only if has a connected path
  * Holds history of where they moved (for backtrack action tiles)

* Leader board

  * shows best players on each game board.
  * Don't show player that haven't played that board.
  * lists in order of wins.

## Game play

### Setup

* User selects player profile
* A game board is chosen
* gaps are randomly filled with floor tiles from the **SILK BAG**
* Place the player pieces for the number of people paying
* Choose who goes first

### Turn

1. Draw a tile
   * if Action tile, keep hold of it hidden, move to step 2.(you cannot play it this turn)
   * If Floor tile, you must slide it onto the board from an available edge space. The ejected tile is returned to the silk bag, Player pieces that are pushed off will be placed back on the board on the new tile
2. Playing an action tile
   * You cannot play an action tile you have drawn this turn
   * You may choose to play an action tile.
   * You can only play one action tile.
   * Once used it is removed from the game.
3. Moving
   1. You must move a piece
   2. Up down left or right
   3. only if not on fire
   4. only if has connected path
   5. only if there isn't a player there.
4. End turn
   * The play move to the player to the left(?)

### Graphics

Not important (but sources say they are)

