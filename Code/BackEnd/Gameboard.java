package BackEnd;

import java.util.ArrayList;
import java.util.function.Function;

import static BackEnd.Rotation.*;
import static BackEnd.TileType.GOAL;

/**
 * This class represents the Gameboard and all of its workings. It is by far the most complicated class in the
 * project. It deals with the sliding of the floor tiles, the placement of the fixed tiles, the placement of action
 * tiles and their radii, the movement of player pieces, and the location of tiles on the gameboard.
 *
 * @author Joshua Oladitan, Atif Ishaq and Christian Sanger.
 * @version 1.0
 */

public class Gameboard {

    /*
     * These attributes store information about the gameboard, such as it's width and height, the number of players
     * and the SilkBag that it is connected to.
     */

    private final int width;
    private final int height;
    private int numOfPlayers;
    private final SilkBag silkbag;

    /*
     * These arrays store information about tiles, and locations on the gameboard.
     * goalCoors stores the coordinates of every goal tile currently on the board.
     * playerLocations store the locations of every player.
     * boardTiles store the tiles on the board.
     */

    private ArrayList<Coordinate> goalCoors;
    private final Coordinate[][] playerLocations;
    private final ArrayList<FloorTile> boardTiles;

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
        this.goalCoors = new ArrayList<>();
        this.boardTiles = new ArrayList<>(width * height);
        this.playerLocations = new Coordinate[4][3];
    }

    /**
     * This method returns the width of the gameboard.
     *
     * @return width The width of the gameboard.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * This method returns the height of the gameboard.
     *
     * @return height The height of the gameboard.
     */
    public int getHeight() {
        return this.height;
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
     * This method sets the position of the specified player to the
     * given coordinate.
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
        insertedTile.setLocation(location);
        boardTiles.add(insertedTile);
        switch (direction) {
            case LEFT:
            case RIGHT:
                forAllFloorTiles((t) -> {
                    Coordinate current = t.getLocation();
                    if (current.getY() == location.getY()) {
                        t.setLocation(current.shift(shiftAmount));
                    }
                    return null;
                });
                break;
            case UP:
            case DOWN:
                forAllFloorTiles((t) -> {
                    Coordinate current = t.getLocation();
                    if (current.getX() == location.getX()) {
                        t.setLocation(current.shift(shiftAmount));
                    }
                    return null;
                });
                break;
        }
        // Remove tile outside of board
        ArrayList<FloorTile> tilesToRemove = new ArrayList<>();
        forAllFloorTiles((t) -> {
            Coordinate current = t.getLocation();
            if (current.getX() < 0 || current.getX() >= width ||
                    current.getY() < 0 || current.getY() >= height) {
                tilesToRemove.add(t);
            }
            return null;
        });

        if (tilesToRemove.size() > 1) {
            StringBuilder locations = new StringBuilder();
            for (FloorTile t : tilesToRemove) {
                locations.append(t.getLocation().toString());
                locations.append(":");
            }
            throw new Exception("Two tiles pushed off the board at" + locations);
        }

        if (tilesToRemove.size() == 1) {
            removeTile(tilesToRemove.get(0));
        }
    }

    private void removeTile(FloorTile floorTile) {
        silkbag.insertTile(floorTile);
        boardTiles.remove(floorTile);
    }

    private void forAllFloorTiles(Function<FloorTile, Void> func) throws Exception {
        for (FloorTile tile : boardTiles) {
            func.apply(tile);
        }
    }

    /**
     * This method gives the tile from the given coordinates.
     *
     * @param location to found tile at.
     * @return the tile at the given coordinates.
     */
    FloorTile tileAt(Coordinate location) {
        FloorTile found = null;
        for (FloorTile tile : boardTiles) {
            if (location.equals(tile.getLocation())) {
                if (found == null) {
                    found = tile;
                } else {
                    System.out.println("Two tiles at" + location.toString());
                }
            }
        }
        return found;
    }

    /**
     * This method returns the move directions a player could possible move in as an arrayList
     * for the specified player.
     *
     * @param player The player whose move directions is to be obtained.
     * @return moveLocations The move directions for the specified player.
     */
    public ArrayList<Coordinate> getMoveDirections(int player) throws Exception {
        Rotation[] directions = new Rotation[]{UP, DOWN, LEFT, RIGHT};
        ArrayList<Rotation> validDirections = new ArrayList<>();
        ArrayList<Coordinate> moveLocations = new ArrayList<>();
        Coordinate location = getPlayerPos(player);
        FloorTile playersTile = tileAt(location);

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
            tileInDirection = tileAt(locationToCheck);
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


    /**
     * This method checks to see if it is possible for a player to move in a given direction, and returns a
     * boolean, stating whether it is possible to move in that direction (true) or not possible to move in that
     * direction (false).
     *
     * @param playerTile The floorTile that the player piece is currently on.
     * @param direction  The direction the player would like to move in.
     * @return result Either true or false, depending on whether or not you can move in that direction.
     */
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
     *
     * @param rotation The given direction to be flipped.
     * @return The flipped direction.
     */
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
     *
     * @param coordinate The coordinate to be shifted.
     * @param direction  The direction to be shifted in.
     * @return The newly shifted coordinate.
     */
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
     *
     * @param location to use action tile
     * @param tile     type of action tile
     * @param player   refers to the player that's using the tile.
     */
    public void playActionTile(Coordinate location, ActionTile tile, int player) throws Exception {
        if (tile.getType() == TileType.FROZEN) {
            setFreezeCoords(location);
        } else if (tile.getType() == TileType.FIRE) {
            setFireCoords(location);
        } else if (tile.getType() == TileType.BACKTRACK) {
            backtrack(player);
        }
    }

    /**
     * Places a fixed floor tile and board tile in the coordinates specified.
     *
     * @param tile     to be placed
     * @param location this is the coordinate for the location.
     */
    public void placeFixedTile(FloorTile tile, Coordinate location) {
        if (tileAt(location) != null) {
            silkbag.insertTile(tileAt(location));
            removeTile(tileAt(location));

        }
        tile.setFixed();
        tile.setLocation(location);
        boardTiles.add(tile);
    }

    /**
     * Checks the board for goal tiles, adds the coordinates to the ArrayList.
     *
     * @return the ArrayList of goal tile locations.
     */
    public ArrayList<Coordinate> checkGoalTiles() throws Exception {
        ArrayList<Coordinate> goalCoors = new ArrayList<>();
        forAllFloorTiles((t) -> {
            if (t.getType() == GOAL) {
                goalCoors.add(t.getLocation());
            }
            return null;
        });
        return goalCoors;
    }

    /**
     * Checks to see if a player is on goal by going through all the players' locations to see
     * if they match the goal coordinates.
     *
     * @return if a player coordinate matches a goal coordinate, return true, else false.
     */
    public int isPlayerOnGoal() throws Exception {
        int players = getNumOfPlayers();
        goalCoors = checkGoalTiles();
        for (Coordinate goalCoor : goalCoors) {
            for (int j = 0; j < players; j++) {
                if (getPlayerPos(j).equals(goalCoor)) {
                    return j;
                }
            }
        }
        return -1;
    }

    /**
     * Checks the given coordinates if there is a player on that tile.
     *
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

    /**
     * This method sets fire in a 3x3 radius to the given coordinates. Making sure that the tiles
     * its setting fire to are on the board.
     *
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
     *
     * @param location the 3x3 area to freeze.
     */
    public void setFreezeCoords(Coordinate location) throws Exception {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Coordinate toSetOnFire = location.shift(i, j);
                if (toSetOnFire.getX() >= 0 && toSetOnFire.getX() < width &&
                        toSetOnFire.getY() >= 0 && toSetOnFire.getY() < height) {
                    tileAt(toSetOnFire).setFrozenTic(getNumOfPlayers());
                }
            }
        }
    }

    /**
     * This method gets the slide locations that a player can play a floor tile. It does this by
     * checking every row/column and checking if there is a fixed tile in that row/column. If there isnt
     * add it to the slide locations.
     *
     * @return the ArrayList of slide locations.
     */
    public ArrayList<Coordinate> getSlideLocations() throws Exception {

        ArrayList<Coordinate> allSlideLocations = new ArrayList<>();

        // Add all directions someone could slide
        for (int x = 0; x < width; x++) {
            allSlideLocations.add(new Coordinate(x, -1));
            allSlideLocations.add(new Coordinate(x, height));
        }

        for (int y = 0; y < width; y++) {
            allSlideLocations.add(new Coordinate(-1, y));
            allSlideLocations.add(new Coordinate(width, y));
        }

        forAllFloorTiles((t) -> {
            if (t != null && (t.isFixed() || t.isFrozen())) {
                // If fixed or Frozen
                // Remove all matching slide locations
                allSlideLocations.removeIf((c) -> (
                        c.getX() == t.getLocation().getX() ||
                                c.getY() == t.getLocation().getY()));
            }
            return null;
        });
        return allSlideLocations;
    }


    /**
     * This method check the players previous two locations, if the player has moved one or more times
     * and the previous location is not on fire and there is not a player on that tile. Then backtrack is valid.
     * The player will move back one or two positions if backtrack is played on the player.
     *
     * @param player
     */
    public void backtrack(int player) throws Exception {
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
     *
     * @return true, if there is a null location on the board. Else false.
     */
    public boolean isBoardNotFull() throws Exception {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (tileAt(new Coordinate(x, y)) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    /**
     * This method gets the number of players by checking the playerLocations array. Incrementing the number of players
     * if the array is not equal to null.
     *
     * @return the number of players.
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    /**
     * This method ticks all the given tiles, to remove their affect.
     *
     * @throws Exception An exception if null.
     */
    public void ticTiles() throws Exception {
        forAllFloorTiles((t) -> {
            if (t != null) {
                t.ticFire();
                t.ticFrozen();
            }
            return null;
        });
    }

    /**
     * This method places a fixed tile to the given location.
     *
     * @param floorTile The tile to be fixed.
     * @param x         x coordinate.
     * @param y         y coordinate.
     */
    public void placeFixedTile(FloorTile floorTile, int x, int y) {
        placeFixedTile(floorTile, new Coordinate(x, y));
    }
}
