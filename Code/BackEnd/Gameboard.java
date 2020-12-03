package BackEnd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static BackEnd.Rotation.*;

/**
 * This class represents the Gameboard and all of its workings. It is by far the most complicated class in the
 * project. It deals with the sliding of the floor tiles, the placement of the fixed tiles, the placement of action
 * tiles and their radii, the movement of player pieces, and the location of tiles on the gameboard.
 *
 * @author Joshua Oladitan & Atif Ishaq
 * @version 1.0
 */

public class Gameboard {

    /*
     * These attributes store information about the gameboard, such as its' width and height, the number of players
     * and the SilkBag that it is connected to.
     */

    private int width;
    private int height;
    private int numOfPlayers = 4; //Currently initialized as 4 for testing. Can be set to another value.
    private SilkBag silkbag;
    private FloorTile removedTile;

    /*
     * These arrays store information about tiles, and locations on the gameboard.
     * goalCoors stores the coordinates of every goal tile currently on the board.
     * playerLocations and slideLocations store the locations of every player and slide-able row/column respectively.
     * boardTiles and fixedTiles store the tiles on the board and which of those are fixed in their given indexes.
     */

    private ArrayList<Coordinate> goalCoors;
    private Coordinate[][] playerLocations;
    private FloorTile[][] boardTiles;
    private FloorTile[][] fixedTiles;
    private Coordinate[] slideLocations;

    /**
     * This constructor of Gameboard initializes all of the attributes, save the removedTile, which gets initialized
     * in playFloorTile. It takes in the width, and height of the Gameboard, as well as the SilkBag that the board
     * is connected to.
     *
     * @param width   The width of the gameboard.
     * @param height  The height of the gameboard.
     * @param silkBag The SilkBag that this gameboard is connected to.
     */
    public Gameboard(int width, int height, SilkBag silkBag) {
        this.width = width;
        this.height = height;
        this.silkbag = silkBag;
        goalCoors = new ArrayList<>();
        slideLocations = new Coordinate[10];  //TODO change this.
        boardTiles = new FloorTile[width][height];
        fixedTiles = new FloorTile[width][height];
        playerLocations = new Coordinate[numOfPlayers][3];
    }

    /**
     * This method returns the width of the gameboard.
     *
     * @return width The width of the gameboard.
     */
    public int getWidth() {
        return width;
    }

    /**
     * This method returns the height of the gameboard.
     *
     * @return height The height of the gameboard.
     */
    public int getHeight() {
        return height;
    }

    /**
     * This method returns the position of the specified player.
     *
     * @param player The player whose position we want.
     * @return The coordinates of the specified player.
     */
    public Coordinate getPlayerPos(int player) {
        return getPrevPlayerPos(player, 0);
    }

    /**
     * This method returns the position of the specified player at a given number of turns ago.
     *
     * @param player  The player whose position we want.
     * @param history How many turns ago we want to know the player's position.
     * @return The coordinates of the specified player at that given history.
     */
    public Coordinate getPrevPlayerPos(int player, int history) {
        return playerLocations[player][history];
    }

    /**
     * This method sets the position of the specified player.
     *
     * @param player   The player whose position we want to set.
     * @param position The position to set.
     */
    public void setPlayerPos(int player, Coordinate position) {
        playerLocations[player][2] = playerLocations[player][1];
        playerLocations[player][1] = playerLocations[player][0];
        playerLocations[player][0] = position;
    }

    /**
     * This method plays a floorTile in a given row/column, and moves every floorTile in that direction, if it isn't
     * fixed or frozen. Once a floorTile is inserted, the tile that is on the end is placed into the SilkBag, and the
     * player piece that is on the end is now placed on the newly inserted tile. Inserting from the left is denoted as
     * selecting a location with a x-coordinate of -1, inserting from the bottom is denoted as selecting a location
     * with a y-coordinate of -1, inserting from the right is denoted as selecting a location with a y-coordinate
     * of the width of the board, and inserting from the top is denoted as selecting a location with a y-coordinate
     * of the height of the board.
     *
     * @param location     The location of the given side to slide in the floorTile from.
     * @param insertedTile The floorTile to slide into that location.
     * @return removedTile The floorTile that was on the opposite edge and was pushed off of the Gameboard.
     */
    public void playFloorTile(Coordinate location, FloorTile insertedTile) throws Exception {
        // Shifting the player.
        Rotation direction;
        Coordinate shiftAmount;
        if (location.getX() <= -1) {
            direction = LEFT;
            shiftAmount = new Coordinate(1, 0);
        } else if (location.getX() >= width) {
            direction = RIGHT;
            shiftAmount = new Coordinate(-1, 0);
        } else if (location.getY() <= -1) {
            direction = UP;
            shiftAmount = new Coordinate(0, 1);
        } else if (location.getY() >= height) {
            direction = DOWN;
            shiftAmount = new Coordinate(0, -1);
        } else {
            throw new Exception("Invalid slide location :" + location.toString());
        }
        // Shift all players on the correct row // column.
        if (direction == LEFT || direction == RIGHT) {
            for (int i = 0; i < getNumOfPlayers(); i++) {
                Coordinate current = getPlayerPos(i);
                if (current.getY() == location.getY()) {
                    setPlayerPos(i, current.shift(shiftAmount));
                }
            }
        } else {
            for (int i = 0; i < getNumOfPlayers(); i++) {
                Coordinate current = getPlayerPos(i);
                if (current.getX() == location.getX()) {
                    setPlayerPos(i, current.shift(shiftAmount));
                }
            }
        }
        // Check to see if any players are out-of-bounds.
        for (int i = 0; i < getNumOfPlayers(); i++) {
            Coordinate current = getPlayerPos(i);
            if (current.getX() < 0) {
                setPlayerPos(i, new Coordinate(width - 1, current.getY()));
            }
            if (current.getX() >= width) {
                setPlayerPos(i, new Coordinate(0, current.getY()));
            }
            if (current.getY() < 0) {
                setPlayerPos(i, new Coordinate(current.getX(), height - 1));
            }
            if (current.getY() >= height) {
                setPlayerPos(i, new Coordinate(current.getX(), 0));
            }
        }
        // Inserting the new tile from the left.
        if (location.getX() == -1) {
            removedTile = boardTiles[width - 1][location.getY()];
            for (int j = width - 2; j >= 0; j--) {
                boardTiles[j + 1][location.getY()] = boardTiles[j][location.getY()];
            }
            boardTiles[0][location.getY()] = insertedTile;
            if (removedTile != null) {
                silkbag.insertTile(removedTile);
            }
        }
        // Inserting the new tile from the right.
        else if (location.getX() == width) {
            removedTile = boardTiles[0][location.getY()];
            for (int j = 0; j < width - 1; j++) {
                boardTiles[j][location.getY()] = boardTiles[j + 1][location.getY()];
            }
            boardTiles[width - 1][location.getY()] = insertedTile;
            if (removedTile != null) {
                silkbag.insertTile(removedTile);
            }
        }
        // Inserting the new tile from the bottom.
        else if (location.getY() == -1) {
            removedTile = boardTiles[location.getX()][height - 1];
            for (int j = height - 2; j >= 0; j--) {
                boardTiles[location.getX()][j + 1] = boardTiles[location.getX()][j];
            }
            boardTiles[location.getX()][0] = insertedTile;
            if (removedTile != null) {
                silkbag.insertTile(removedTile);
            }
        }
        // The last remaining case: Inserting the new tile from the top.
        else {
            removedTile = boardTiles[location.getX()][0];
            for (int j = 0; j < width - 1; j++) {
                boardTiles[location.getX()][j] = boardTiles[location.getX()][j + 1];
            }
            boardTiles[location.getX()][height - 1] = insertedTile;
            if (removedTile != null) {
                silkbag.insertTile(removedTile);
            }
        }
    }

    /**
     * This method returns the move directions a player could possible move in as an arrayList
	 * for the specified player.
	 * @param player The player whose move directions is to be obtained.
	 * @return moveLocations The move directions for the specified player.
     */
    public ArrayList<Coordinate> getMoveDirections(int player) {
        Rotation[] directions = new Rotation[]{UP, DOWN, LEFT, RIGHT};
        ArrayList<Rotation> validDirections = new ArrayList<>();
        ArrayList<Coordinate> moveLocations = new ArrayList<>();
        Coordinate location = getPlayerPos(player);
        FloorTile playersTile = boardTiles[location.getX()][location.getY()];

        //Loops through all four directions to see if the tile the player is on connects to the direction.
        for (Rotation direction : directions) {
            if (validMove(playersTile, direction)) {
                validDirections.add(direction);
            }
        }

        /* Loops through all connected valid directions, and gets the tile in that valid direction to see if it
        connects with the tile the player is on by flipping the rotation.
        If it does, it is then added as a coordinate to move locations. */
        for (Rotation direction : validDirections) {
            FloorTile tileInDirection;
            Rotation flipDirection = flipDirection(direction);
            Coordinate locationToCheck = shift(location, direction);
            // Check if a player piece is on the tile.
            if (checkTileForPlayer(locationToCheck)) {
                continue;
            }
            // Check if the new direction is off of the board.
            if ((locationToCheck.getX() < 0 || locationToCheck.getX() >= width) ||
                    (locationToCheck.getY() < 0 || locationToCheck.getY() >= height)) {
                continue;
            }
            tileInDirection = boardTiles[locationToCheck.getX()][locationToCheck.getY()];
            // Check if the tile on fire.
            if (tileInDirection.onFire()) {
                continue;
            }
            // If tile passes all previous tests then it is a valid place to move.
            if (validMove(tileInDirection, flipDirection)) {
                moveLocations.add(locationToCheck);
            }
        }
        return moveLocations;
    }

    //Method checks to see if its possible for the player to move in that direction.
	/**
	 * This method checks to see if it is possible for a player to move in a given direction, and returns a
	 * boolean, stating whether it is possible to move in that direction (true) or not possible to move in that
	 * direction (false).
	 * @param playerTile The floorTile that the player piece is currently on.
	 * @param direction The direction the player would like to move in.
	 * @return result Either true or false, depending on whether or not you can move in that direction.
	 * */
    private boolean validMove(FloorTile playerTile, Rotation direction) {
        Rotation rotation = playerTile.getRotation();
        boolean result = false;
        switch (playerTile.getType()) {
            case CORNER:
                result = (rotation == UP && (direction == RIGHT || direction == DOWN)) ||
                        (rotation == RIGHT && (direction == DOWN || direction == LEFT)) ||
                        (rotation == DOWN && (direction == LEFT || direction == UP)) ||
                        (rotation == LEFT && (direction == UP || direction == RIGHT));
                break;
            case T_SHAPE:
                result = rotation != direction;
                break;
            case STRAIGHT:
                result = rotation == direction || rotation == flipDirection(direction);
                break;
            case GOAL:
                result = true;
                break;
        }
        return result;
    }
	/**
	 * This method flips a given direction, and returns the opposite direction.
	 * @param rotation The given direction to be flipped.
	 * @return The flipped direction.
	 * */
    private Rotation flipDirection(Rotation rotation) {
        switch (rotation) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case RIGHT:
                return LEFT;
            case LEFT:
                return RIGHT;
        }
        return UP;
    }
	/**
	 * This method shifts in a given direction, depending on what direction it is, and returns the shifted coordinate.
	 * @param coordinate The coordinate to be shifted.
	 * @param direction The direction to be shifted in.
	 * @return The newly shifted coordinate.
	 * */
    private Coordinate shift(Coordinate coordinate, Rotation direction) {
        int shiftX = 0;
        int shiftY = 0;
        switch (direction) {
            case UP:
                shiftY = -1;
                break;
            case RIGHT:
                shiftX = 1;
                break;
            case DOWN:
                shiftY = 1;
                break;
            case LEFT:
                shiftX = -1;
                break;
        }
        return new Coordinate(coordinate.getX() + shiftX, coordinate.getY() + shiftY);
    }

    /**
     * This function is used to play action tiles depending on the action tile given.
     * @param location to use action tile
     * @param tile type of action tile
     * @param player refers to the player that's using the tile.
     */
    public void playActionTile(Coordinate location, ActionTile tile, int player) {
        if (tile.getType() == TileType.FROZEN) {
            setFreezeCoords(location);
        } else if (tile.getType() == TileType.FIRE) {
            setFireCoords(location);
        } else if (tile.getType() == TileType.BACKTRACK) {
            backtrack(player);
        }
    }

    /**
     * places a fixed floor tile and board tile in the coordinates specified.
     * @param tile to be placed
     * @param x co-ordinate
     * @param y co-ordinate
     */
    public void placeFixedTile(FloorTile tile, int x, int y) {
        boardTiles[x][y] = tile;
        boardTiles[x][y].setFixed();
    }

    /**
     * Checks the board for goal tiles, adds the coordinates to the ArrayList.
     * @return the ArrayList of goal tile locations.
     */
    public ArrayList<Coordinate> checkGoalTiles() {
        for (int i = 0; i < boardTiles.length; i++) {
            for (int j = 0; j < boardTiles[i].length; j++) {
                if (boardTiles[i][j] != null && boardTiles[i][j].getType() == TileType.GOAL) {
                    goalCoors.add(new Coordinate(i, j));
                }
            }
        }
        return goalCoors;
    }

    /**
     * Checks to see if a player is on goal by going through all the players' locations to see
     * if they match the goal coordinates.
     * @return if a player coordinate matches a goal coordinate, return true, else false.
     */
    //checks to see if a player is on goal by going through all the players' locations to see
    //if they match any of the goal coordinates.
    public int isPlayerOnGoal() {
        int players = getNumOfPlayers();
        goalCoors = checkGoalTiles();
        for (int i = 0; i < goalCoors.size(); i++) {
            for (int j = 0; j < players; j++) {
                if (getPlayerPos(j).getX() == goalCoors.get(i).getX() && getPlayerPos(j).getY() == goalCoors.get(i).getY()) {
                    return j;
                }
            }
        }
        return -1;
    }

    /**
     * Checks the given coordinates if there is a player on that tile.
     * @param toCheck coordinates to be checked
     * @return true, if there is player on the given tile, else false.
     */
    private boolean checkTileForPlayer(Coordinate toCheck) {
        for (Coordinate[] playerLocation : playerLocations) {
            if (playerLocation[0] != null && playerLocation[0].equals(toCheck)) {
                return true;
            }
        }
        return false;
    }


    /*
    private int checkTileForPlayerInt(int x, int y) {
        //This is 4, as the 4 players are listed as 0,1,2, and 3, if it returns 4, then there is no player on this tile.
        int playerNumber = 4;
        for (int i = 0; i < playerLocations.length; i++) {
            Coordinate playerPos = getPlayerPos(i);
            if (playerPos != null && playerPos.getX() == x && playerPos.getY() == y) {
                playerNumber = i;
                return playerNumber;
            }
        }
        return playerNumber;
    }
     */

    /**
     * This method returns the tile that the player is currently on.
     * @param player the player to be checked
     * @return the tile that the player is on.
     */
    public Tile getPlayerTile(int player) {
        Coordinate location = getPlayerPos(player);
        return boardTiles[location.getX()][location.getY()];
    }

    /**
     * This method sets fire in a 3x3 radius to the given coordinates. Making sure that the tiles
     * its setting fire to are on the board.
     * @param location the 3x3 area to set fire to.
     */
    public void setFireCoords(Coordinate location) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Coordinate toSetOnFire = location.shift(i, j);
                if (toSetOnFire.getX() >= 0 && toSetOnFire.getX() < width &&
                        toSetOnFire.getY() >= 0 && toSetOnFire.getY() < height) {
                    tileAt(toSetOnFire).setFireTic(getNumOfPlayers());
                }
            }
        }
    }

    /**
     * This method freezes in a 3x3 radius to the given coordinates. Making sure that the tiles
     * its getting froze to are on the board.
     * @param location the 3x3 area to freeze.
     */
    public void setFreezeCoords(Coordinate location) {
        int players = getNumOfPlayers();
        for (int i = 0; i < boardTiles.length; i++) {
            for (int j = 0; j < boardTiles[i].length; j++) {
                if (i == location.getX() && j == location.getY()) {
                    //Assuming  0,0 is bottom left. Freezes a 3x3 radius of tiles.
                    boardTiles[i][j].setFrozenTic(players); //mid
                    if (i != width - 1) {
                        boardTiles[i + 1][j].setFrozenTic(players); //right
                    }
                    if (i != 0) {
                        boardTiles[i - 1][j].setFrozenTic(players); //left
                    }
                    if (j != height - 1) {
                        boardTiles[i][j + 1].setFrozenTic(players); //up
                    }
                    if (i != width - 1 && j != height - 1) {
                        boardTiles[i + 1][j + 1].setFrozenTic(players); //upper right
                    }
                    if (i != 0 && j != height - 1) {
                        boardTiles[i - 1][j + 1].setFrozenTic(players); //upper left
                    }
                    if (j != 0) {
                        boardTiles[i][j - 1].setFrozenTic(players); //down
                    }
                    if (i != width - 1 && j != 0) {
                        boardTiles[i + 1][j - 1].setFrozenTic(players); //down right
                    }
                    if (i != 0 && j != 0) {
                        boardTiles[i - 1][j - 1].setFrozenTic(players); //down left
                    }
                }
            }
        }
    }

    /**
     * This method gets the slide locations that a player can play a floor tile. It does this by
     * checking every row/column and checking if there is a fixed tile in that row/column. If there isnt
     * add it to the slide locations.
     * @return the ArrayList of slide locations.
     */

    public ArrayList<Coordinate> getSlideLocations() {

        ArrayList<Coordinate> locations = new ArrayList<>();
        ArrayList<Coordinate> slideLocations = new ArrayList<>();
        ArrayList<Integer> xSlideLocations = new ArrayList<>();
        ArrayList<Integer> ySlideLocations = new ArrayList<>();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (boardTiles[i][j] == null || (!boardTiles[i][j].isFixed() && !boardTiles[i][j].isFrozen())) {
                    locations.add(new Coordinate(i, j));
                }
            }
        }

        for (int x = 0; x < locations.size(); x++) {
            xSlideLocations.add(locations.get(x).getX());
        }

        for (int y = 0; y < locations.size(); y++) {
            ySlideLocations.add(locations.get(y).getY());
        }
        Collections.sort(ySlideLocations);

        for (int x1 = 0; x1 < xSlideLocations.size();){
            if (Collections.frequency(xSlideLocations,xSlideLocations.get(x1)) == height) {
                slideLocations.add(new Coordinate(xSlideLocations.get(x1), -1));
                slideLocations.add(new Coordinate (xSlideLocations.get(x1), height));
                x1 += width;
            }
            else {
                x1++;
            }
        }
        for (int y1 = 0; y1 < ySlideLocations.size();) {
            if (Collections.frequency(ySlideLocations,ySlideLocations.get(y1)) == width) {
                slideLocations.add(new Coordinate(-1, ySlideLocations.get(y1)));
                slideLocations.add(new Coordinate (width, ySlideLocations.get(y1)));
                y1 += height;
            }
            else {
                y1++;
            }
        }
        for(int k = 0; k < slideLocations.size(); k++){
           System.out.println(slideLocations.get(k));
        }
        return slideLocations;
    }

    /**
     * This method gives the tile from the given coordinates.
     * @param coordinate the tile to be checked.
     * @return the tile at the given coordinates.
     */
    public FloorTile tileAt(Coordinate coordinate) {
        return boardTiles[coordinate.getX()][coordinate.getY()];
    }

    /**
     * This method check the players previous two locations, if the player has moved one or more times
     * and the previous location is not on fire and there is not a player on that tile. Then backtrack is valid.
     * The player will move back one or two positions if backtrack is played on the player.
     * @param player
     */
    public void backtrack(int player) {
        //gets the players current position.
        Coordinate posOneTurnAgo = getPrevPlayerPos(player, 1);
        FloorTile tileOneTurn = null;
        if (posOneTurnAgo != null) {
            tileOneTurn = tileAt(posOneTurnAgo);//gets the tile, one and two turns ago that the player was on.
        }
        Coordinate posTwoTurnsAgo = getPrevPlayerPos(player, 2);
        FloorTile tileTwoTurn = null;
        if (posTwoTurnsAgo != null) {
            tileTwoTurn = tileAt(posTwoTurnsAgo);
        }

        //checks to see if the tile two turns ago is on fire, if not, sets that as the players position
        if (tileOneTurn != null && !tileOneTurn.onFire() && !checkTileForPlayer(posOneTurnAgo)) {
            if (tileTwoTurn != null && !tileTwoTurn.onFire() && !checkTileForPlayer(posTwoTurnsAgo)) {
                // They can move two steps back
                setPlayerPos(player, posTwoTurnsAgo);
            } else {
                // They can only move one step back
                setPlayerPos(player, posOneTurnAgo);
            }
        }
        // they can't move backward
    }

    /**
     * This method goes through the board width and height checking the tiles to see if any of them are null
     * (they dont contain a tile). If at least one tile doesn't contain a tile return true.
     * @return true, if there is a null location on the board. Else false.
     */
    public boolean containsNull() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (boardTiles[x][y] == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        playerLocations = Arrays.copyOf(playerLocations,numOfPlayers);
        this.numOfPlayers = numOfPlayers;
    }

    /**
     * This method gets the number of players by checking the playerLocations array. Incrementing the number of players
     * if the array is not equal to null.
     * @return the number of players.
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void ticTiles(){
        for (int i = 0; i < boardTiles.length; i++){
            for (int j = 0; j < boardTiles[i].length; j++ ){
                if (boardTiles[i][j].isFrozen()){
                    boardTiles[i][j].ticFrozen();
                }
                if (boardTiles[i][j].onFire()){
                    boardTiles[i][j].ticFire();
                }
            }
        }
    }
}
