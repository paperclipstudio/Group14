
package BackEnd;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Gameboard {

    private int width;
    private int height;
    private SilkBag silkbag;
    private static Coordinate[] goalCoors;
    private Coordinate[][] playerLocations;
    private ActionTileLocations[] actionTiles;
    private FloorTile[][] boardTiles;
    private FloorTile removedTile;
    private Coordinate[] slideLocations;


    public Gameboard(int width, int height, SilkBag silkBag) {
        this.width = width;
        this.height = height;
        this.silkbag = silkBag;
        slideLocations = new Coordinate[10];
        //TODO turns out nothing has been initialised.
        boardTiles = new FloorTile[100][100];
        //TODO third null pointer exception
        playerLocations = new Coordinate[100][1000];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Coordinate getPlayerPos(int player) {
        //gets the last location the player was in.
        int length = 0;
        for (Coordinate coor : playerLocations[player]) {
            if (coor != null) {
                length++;
            }
        }
        return playerLocations[player][length - 1];
    }

    public void setPlayerPos(int player, Coordinate position) {
        //sets the last location the player was in.
        int length = 0;
        for (Coordinate coor : playerLocations[player]) {
            if (coor != null) {
                length++;
            }
        }
        this.playerLocations[player][length] = position;
    }

    public Tile playFloorTile(Coordinate location, FloorTile insertedTile) {
        // Inserting the new tile from the left.
        if (location.getX() == -1) {
            removedTile = boardTiles[width - 1][location.getY()];
            for (int j = 0; j < width; j++) {
                boardTiles[j][location.getY()] = boardTiles[j + 1][location.getY()];
            }
            boardTiles[0][location.getY()] = insertedTile;
            if (removedTile != null) {
                silkbag.insertTile(removedTile);
            }
        }
        // Inserting the new tile from the right.
        else if (location.getX() == width) {
            removedTile = boardTiles[0][location.getY()];
            for (int j = width; j > 0; j--) {
                boardTiles[j][location.getY()] = boardTiles[j - 1][location.getY()];
            }
            boardTiles[width - 1][location.getY()] = insertedTile;
            if (removedTile != null) {
                silkbag.insertTile(removedTile);
            }
        }
        // Inserting the new tile from the bottom.
        else if (location.getY() == -1) {
            removedTile = boardTiles[location.getX()][height - 1];
            for (int j = 0; j < height; j++) {
                boardTiles[location.getX()][j] = boardTiles[location.getX()][j + 1];
            }
            boardTiles[location.getX()][0] = insertedTile;
            if (removedTile != null) {
                silkbag.insertTile(removedTile);
            }
        }
        // The last remaining case: Inserting the new tile from the top.
        else {
            removedTile = boardTiles[location.getX()][0];
            for (int j = height; j > 0; j--) {
                boardTiles[location.getX()][j] = boardTiles[location.getX()][j - 1];
            }
            boardTiles[location.getX()][height - 1] = insertedTile;
            if (removedTile != null) {
                silkbag.insertTile(removedTile);
            }
        }
        return removedTile;
    }


    public ArrayList<Coordinate> getMoveDirections(int player) {
        Rotation[] directions = new Rotation[]{Rotation.UP, Rotation.DOWN, Rotation.LEFT, Rotation.RIGHT};
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

        //Loops through all connected valid directions, gets the tile in that valid direction to see if it
        //connects with the tile the player is on by flipping the rotation, if yes add it as a coordinate to move locations.
        for (Rotation direction : validDirections) {
            FloorTile tileInDirection;
            Rotation flipDirection;
            if (direction == Rotation.UP && location.getY() != height - 1) {
                tileInDirection = boardTiles[location.getX()][location.getY() + 1];
                flipDirection = Rotation.DOWN;
                if (validMove(tileInDirection, flipDirection) && !tileInDirection.onFire()) {
                    moveLocations.add(new Coordinate(location.getX(), location.getY() + 1));
                }
            } else if (direction == Rotation.DOWN && location.getY() != 0) {
                tileInDirection = boardTiles[location.getX()][location.getY() - 1];
                flipDirection = Rotation.UP;
                if (validMove(tileInDirection, flipDirection) && !tileInDirection.onFire()) {
                    moveLocations.add(new Coordinate(location.getX(), location.getY() - 1));
                }
            } else if (direction == Rotation.LEFT && location.getX() != 0) {
                tileInDirection = boardTiles[location.getX() - 1][location.getY()];
                flipDirection = Rotation.RIGHT;
                if (validMove(tileInDirection, flipDirection) && !tileInDirection.onFire()) {
                    moveLocations.add(new Coordinate(location.getX() - 1, location.getY()));
                }
            } else if (direction == Rotation.RIGHT && location.getX() != width - 1) {
                tileInDirection = boardTiles[location.getX() + 1][location.getY()];
                flipDirection = Rotation.LEFT;
                if (validMove(tileInDirection, flipDirection) && !tileInDirection.onFire()) {
                    moveLocations.add(new Coordinate(location.getX() + 1, location.getY()));
                }
            }
        }
        return moveLocations;
    }

    //Method checks to see if its possible for the player to move in that direction.
    private boolean validMove(FloorTile playerTile, Rotation direction) {
        switch (playerTile.getType()) {
            case CORNER:
                if (playerTile.getRotation() == Rotation.UP && direction == Rotation.RIGHT ||
                        playerTile.getRotation() == Rotation.UP && direction == Rotation.DOWN) {
                    return true;
                } else if (playerTile.getRotation() == Rotation.DOWN && direction == Rotation.UP ||
                        playerTile.getRotation() == Rotation.DOWN && direction == Rotation.LEFT) {
                    return true;
                } else if (playerTile.getRotation() == Rotation.RIGHT && direction == Rotation.DOWN ||
                        playerTile.getRotation() == Rotation.RIGHT && direction == Rotation.LEFT) {
                    return true;
                } else if (playerTile.getRotation() == Rotation.LEFT && direction == Rotation.UP ||
                        playerTile.getRotation() == Rotation.LEFT && direction == Rotation.RIGHT) {
                    return true;
                } else {
                    return false;
                }
            case T_SHAPE:
                if (playerTile.getRotation() == Rotation.UP && direction == Rotation.LEFT ||
                        playerTile.getRotation() == Rotation.UP && direction == Rotation.RIGHT ||
                        playerTile.getRotation() == Rotation.UP && direction == Rotation.DOWN) {
                    return true;
                } else if (playerTile.getRotation() == Rotation.DOWN && direction == Rotation.LEFT ||
                        playerTile.getRotation() == Rotation.DOWN && direction == Rotation.RIGHT ||
                        playerTile.getRotation() == Rotation.DOWN && direction == Rotation.UP) {
                    return true;
                } else if (playerTile.getRotation() == Rotation.RIGHT && direction == Rotation.UP ||
                        playerTile.getRotation() == Rotation.RIGHT && direction == Rotation.LEFT ||
                        playerTile.getRotation() == Rotation.RIGHT && direction == Rotation.DOWN) {
                    return true;
                } else if (playerTile.getRotation() == Rotation.LEFT && direction == Rotation.UP ||
                        playerTile.getRotation() == Rotation.LEFT && direction == Rotation.DOWN ||
                        playerTile.getRotation() == Rotation.LEFT && direction == Rotation.RIGHT) {
                    return true;
                } else {
                    return false;
                }
            case STRAIGHT:
                if (playerTile.getRotation() == Rotation.UP && direction == Rotation.LEFT ||
                        playerTile.getRotation() == Rotation.UP && direction == Rotation.RIGHT ||
                        playerTile.getRotation() == Rotation.DOWN && direction == Rotation.LEFT ||
                        playerTile.getRotation() == Rotation.DOWN && direction == Rotation.RIGHT) {
                    return true;
                } else if (playerTile.getRotation() == Rotation.LEFT && direction == Rotation.UP ||
                        playerTile.getRotation() == Rotation.LEFT && direction == Rotation.DOWN ||
                        playerTile.getRotation() == Rotation.RIGHT && direction == Rotation.UP ||
                        playerTile.getRotation() == Rotation.RIGHT && direction == Rotation.DOWN) {
                    return true;
                } else {
                    return false;
                }
        }
        return true;
    }

    public void playActionTile(Coordinate location, ActionTile tile) {
        if (tile.getType() == TileType.FROZEN) {
            setFreezeCoords(location);
        } else if (tile.getType() == TileType.FIRE) {
            setFireCoords(location);
        }
    }


    //places a fixed floor tile in the coordinates specified.
    public void placeFixedTile(FloorTile tile, int x, int y) {
        boardTiles[x][y] = tile;
    }

    //checks to see if a player is on goal by going through all the players' locations to see
    //if they match the goal coordinates.
    /* public boolean isPlayerOnGoal() {
        int goalX = getGoalCoor().getX();
        int goalY = getGoalCoor().getY();
        for (int i = 0; i < playerLocations.length; i++){
            int playerX = playerLocations[i].getX();
            int playerY = playerLocations[i].getY();
            if (playerX == goalX && playerY == goalY){
                return true;
            }
        }
        return false;
    } */

  //  private Coordinate[] checkGoalTiles(){

  //  }


    public Tile getPlayerTile(int player) {
        Coordinate location = getPlayerPos(player);
        return boardTiles[location.getX()][location.getY()];
    }

    private void setFireCoords(Coordinate location) {
        for (int i = 0; i < boardTiles.length; i++) {
            for (int j = 0; j < boardTiles[i].length; j++) {
                if (i == location.getX() && j == location.getY()) {
                    //Assuming 0,0 is bottom left. Sets a 3x3 radius of the tiles on fire.
                    boardTiles[i][j].setFire(); //mid
                    boardTiles[i + 1][j].setFire(); //right
                    boardTiles[i - 1][j].setFire(); //left
                    boardTiles[i][j + 1].setFire(); //up
                    boardTiles[i + 1][j + 1].setFire(); //upper right
                    boardTiles[i - 1][j + 1].setFire(); //upper left
                    boardTiles[i][j - 1].setFire(); //down
                    boardTiles[i + 1][j - 1].setFire(); //down right
                    boardTiles[i - 1][j - 1].setFire(); //down left
                }
            }
        }
    }

    private void setFreezeCoords(Coordinate location) {
        for (int i = 0; i < boardTiles.length; i++) {
            for (int j = 0; j < boardTiles[i].length; j++) {
                if (i == location.getX() && j == location.getY()) {
                    //Assuming  0,0 is bottom left. Freezes a 3x3 radius of tiles.
                    boardTiles[i][j].setFrozen(); //mid
                    boardTiles[i + 1][j].setFrozen(); //right
                    boardTiles[i - 1][j].setFrozen(); //left
                    boardTiles[i][j + 1].setFrozen(); //up
                    boardTiles[i + 1][j + 1].setFrozen(); //upper right
                    boardTiles[i - 1][j + 1].setFrozen(); //upper left
                    boardTiles[i][j - 1].setFrozen(); //down
                    boardTiles[i + 1][j - 1].setFrozen(); //down right
                    boardTiles[i - 1][j - 1].setFrozen(); //down left
                }
            }
        }
    }

    public Coordinate[] getSlideLocations() {
        ArrayList<Coordinate> locations = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            locations.add(new Coordinate(x, -1));
            locations.add(new Coordinate(x, height));
        }
        for (int y = 0; y < width; y++) {
            locations.add(new Coordinate(-1, y));
            locations.add(new Coordinate(width, y));
        }
        return locations.toArray(slideLocations);
    }

    public FloorTile TileAt(Coordinate coordinate) {
        return boardTiles[coordinate.getX()][coordinate.getY()];
    }

    public void backtrack(int player) {
        Coordinate posTwoTurnsAgo;
        Coordinate posOneTurnAgo;
        FloorTile tileTwoTurns;
        FloorTile tileOneTurn;
        //gets the players current pos
        int length = 0;
        for (Coordinate coor : playerLocations[player]) {
            if (coor != null) {
                length++;
            }
        }
        //gets the tile, one and two turns ago that the player was on.
        posTwoTurnsAgo = playerLocations[player][length - 2];
        posOneTurnAgo = playerLocations[player][length - 1];
        tileTwoTurns = boardTiles[posTwoTurnsAgo.getY()][posTwoTurnsAgo.getY()];
        tileOneTurn = boardTiles[posOneTurnAgo.getY()][posOneTurnAgo.getY()];
        //checks to see if the tile two turns ago is on fire, if not sets that as the players position
        if (!tileTwoTurns.onFire()) {
            setPlayerPos(player, posTwoTurnsAgo);
        }
        ////checks to see if the tile one turn ago is on fire, if not sets that as the players position
        else if (!tileOneTurn.onFire()) {
            setPlayerPos(player, posOneTurnAgo);
        }
    }

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
}
