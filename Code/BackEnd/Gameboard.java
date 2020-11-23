
package BackEnd;

import java.util.ArrayList;
import java.util.List;

public class Gameboard {

    private int width;
    private int height;
    private static Coordinate goalCoor;
    private Coordinate[] playerLocations;
    private Coordinate[] slideLocations;
    private ActionTileLocations[] actionTiles;
    private FloorTile[][] boardTiles;
    private FloorTile removedTile;


    public Gameboard (int width, int height) {
        this.width = width;
        this.height = height;
        Coordinate locationOne = new Coordinate(0, -1);
        Coordinate locationTwo = new Coordinate(-1, -0);
        Coordinate locationThree = new Coordinate(-1, height);
        Coordinate locationFour = new Coordinate(width,0);
        //TODO Added as a quick fix by George.
        slideLocations = new Coordinate[10];
        //TODO turns out nothing has been initialised.
        boardTiles = new FloorTile[100][100];
        //TODO third null pointer exception
        playerLocations = new Coordinate[100];
        slideLocations[0] = locationOne;
        slideLocations[1] = locationTwo;
        slideLocations[2] = locationThree;
        slideLocations[3] = locationFour;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Coordinate getPlayerPos (int player){
        return playerLocations[player];
    }

    public void setPlayerPos (int player, Coordinate position){
        this.playerLocations[player] = position;
    }

    public Tile playFloorTile (Coordinate location, FloorTile insertedTile, Rotation rotation){
        for (int i = 0; i < slideLocations.length; i++){
            if (slideLocations[i].getX() == location.getX() && slideLocations[i].getY() == location.getY()){
                // Inserting the new tile from the left.
                if (location.getX() == -1){
                    for(int j = 0; j < width; j++){
                        boardTiles[j][location.getY()] = boardTiles[j+1][location.getY()];
                        removedTile = boardTiles[width][location.getY()];
                    }
                }
                // Inserting the new tile from the right.
                else if (location.getX() == width){
                    for(int j = 0; j < width; j++){
                        boardTiles[j][location.getY()] = boardTiles[j-1][location.getY()];
                        removedTile = boardTiles[-1][location.getY()];
                    }
                }
                // Inserting the new tile from the bottom.
                else if (location.getY() == -1){
                    for(int j = 0; j < height; j++){
                        boardTiles[location.getX()][j] = boardTiles[location.getX()][j+1];
                        removedTile = boardTiles[location.getX()][height];
                    }
                }
                // The last remaining case: Inserting the new tile from the top.
                else {
                    for(int j = 0; j < height; j++){
                        boardTiles[location.getX()][j] = boardTiles[location.getX()][j-1];
                        removedTile = boardTiles[location.getX()][height];
                    }
                }
            }
        }
        return removedTile;
    }


    public ArrayList<Coordinate> getMoveDirections(int player) {
        Rotation[] directions = new Rotation[]{Rotation.UP,Rotation.DOWN,Rotation.LEFT,Rotation.RIGHT};
        ArrayList<Rotation> validDirections = new ArrayList<>();
        ArrayList<Coordinate> moveLocations = new ArrayList<>();
        FloorTile playersTile = null;
        int playerTilesX = 0;
        int playerTilesY = 0;

        //Gets the players tile coordinates and sets the player tile.
        for (int i = 0; i < boardTiles.length; i++) {
            for (int j = 0; j < boardTiles[i].length; j++) {
                if (i == playerLocations[player].getX() && j == playerLocations[player].getY()){
                    playersTile = boardTiles[i][j];
                    playerTilesX = i;
                    playerTilesY = j;
                }
            }
        }

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
            if (direction == Rotation.UP) {
                tileInDirection = boardTiles[playerTilesX][playerTilesY + 1];
                flipDirection = Rotation.DOWN;
                if (validMove(tileInDirection, flipDirection)){
                    moveLocations.add(new Coordinate(playerTilesX, playerTilesY + 1));
                }
            }
            else if (direction == Rotation.DOWN){
                tileInDirection = boardTiles[playerTilesX][playerTilesY - 1];
                flipDirection = Rotation.UP;
                if (validMove(tileInDirection, flipDirection)){
                    moveLocations.add(new Coordinate(playerTilesX, playerTilesY - 1));
                }
            }
            else if (direction == Rotation.LEFT){
                tileInDirection = boardTiles[playerTilesX - 1][playerTilesY];
                flipDirection = Rotation.RIGHT;
                if (validMove(tileInDirection, flipDirection)){
                    moveLocations.add(new Coordinate(playerTilesX - 1, playerTilesY));
                }
            }
            else {
                tileInDirection = boardTiles[playerTilesX + 1][playerTilesY];
                flipDirection = Rotation.LEFT;
                if (validMove(tileInDirection, flipDirection)){
                    moveLocations.add(new Coordinate(playerTilesX + 1, playerTilesY));
                }
            }
        }
        return moveLocations;
    }

    //Method checks to see if its possible for the player to move in that direction.
    private boolean validMove(FloorTile playerTile, Rotation direction) {
        switch (playerTile.getType()){
            case CORNER:
                if (playerTile.getRotation() == Rotation.UP && direction == Rotation.RIGHT ||
                        playerTile.getRotation() == Rotation.UP && direction == Rotation.DOWN){
                    return true;
                }
                else if (playerTile.getRotation() == Rotation.DOWN && direction == Rotation.UP ||
                        playerTile.getRotation() == Rotation.DOWN && direction == Rotation.LEFT){
                    return true;
                }
                else if (playerTile.getRotation() == Rotation.RIGHT && direction == Rotation.DOWN ||
                        playerTile.getRotation() == Rotation.RIGHT && direction == Rotation.LEFT){
                    return true;
                }
                else if (playerTile.getRotation() == Rotation.LEFT && direction == Rotation.UP ||
                        playerTile.getRotation() == Rotation.LEFT && direction == Rotation.RIGHT){
                    return true;
                }
                else{
                    return false;
                }
            case T_SHAPE:
                if (playerTile.getRotation() == Rotation.UP && direction == Rotation.LEFT ||
                        playerTile.getRotation() == Rotation.UP && direction == Rotation.RIGHT ||
                        playerTile.getRotation() == Rotation.UP && direction == Rotation.DOWN){
                    return true;
                }
                else if (playerTile.getRotation() == Rotation.DOWN && direction == Rotation.LEFT ||
                        playerTile.getRotation() == Rotation.DOWN && direction == Rotation.RIGHT ||
                        playerTile.getRotation() == Rotation.DOWN && direction == Rotation.UP){
                    return true;
                }
                else if (playerTile.getRotation() == Rotation.RIGHT && direction == Rotation.UP ||
                        playerTile.getRotation() == Rotation.RIGHT && direction == Rotation.LEFT ||
                        playerTile.getRotation() == Rotation.RIGHT && direction == Rotation.DOWN){
                    return true;
                }
                else if (playerTile.getRotation() == Rotation.LEFT && direction == Rotation.UP ||
                        playerTile.getRotation() == Rotation.LEFT && direction == Rotation.DOWN ||
                        playerTile.getRotation() == Rotation.LEFT && direction == Rotation.RIGHT){
                    return true;
                }
                else{
                    return false;
                }
            case STRAIGHT:
                if (playerTile.getRotation() == Rotation.UP && direction == Rotation.LEFT ||
                        playerTile.getRotation() == Rotation.UP && direction == Rotation.RIGHT ||
                        playerTile.getRotation() == Rotation.DOWN && direction == Rotation.LEFT ||
                        playerTile.getRotation() == Rotation.DOWN && direction == Rotation.RIGHT){
                    return true;
                }
                else if (playerTile.getRotation() == Rotation.LEFT && direction == Rotation.UP ||
                        playerTile.getRotation() == Rotation.LEFT && direction == Rotation.DOWN ||
                        playerTile.getRotation() == Rotation.RIGHT && direction == Rotation.UP ||
                        playerTile.getRotation() == Rotation.RIGHT && direction == Rotation.DOWN){
                    return true;
                }
                else{
                    return false;
                }
        }
        return true;
    }

    public void playActionTile () {

    }


    //places a fixed floor tile in the coordinates specified.
    public void placeFixedTile (FloorTile tile, int x, int y) {
        for (int i = 0; i < boardTiles.length; i++){
            for (int j = 0; j < boardTiles[i].length; j++){
                if (i == x && j == y){
                    boardTiles[i][j] = tile;
                }
            }
        }
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

    public Tile getPlayerTile(int player) {
        for (int i = 0; i < boardTiles.length; i++) {
            for (int j = 0; j < boardTiles[i].length; j++) {
                if (i == playerLocations[player].getX() && j == playerLocations[player].getY()){
                    return boardTiles[i][j];
                }
            }
        }
        //temp
        return null;
    }

    private void updatePlayerPos(int player){
        for (int i = 0; i < boardTiles.length; i++) {
            for (int j = 0; j < boardTiles[i].length; j++) {
                if (boardTiles[i][j].playerOnTile() == player)
                    playerLocations[player] = new Coordinate(i, j);
            }
        }
    }

    private void setFireCoords(Coordinate location){
        for (int i = 0; i < boardTiles.length; i++){
            for (int j = 0; j < boardTiles[i].length; j++){
                if (i == location.getX() && j == location.getY()){
                    //Assuming 0,0 is bottom left. Sets a 3x3 radius of the tiles on fire.
                    boardTiles[i][j].setFire(); //mid
                    boardTiles[i+1][j].setFire(); //right
                    boardTiles[i-1][j].setFire(); //left
                    boardTiles[i][j+1].setFire(); //up
                    boardTiles[i+1][j+1].setFire(); //upper right
                    boardTiles[i-1][j+1].setFire(); //upper left
                    boardTiles[i][j-1].setFire(); //down
                    boardTiles[i+1][j-1].setFire(); //down right
                    boardTiles[i-1][j-1].setFire(); //down left
                }
            }
        }
    }

    private void setFreezeCoords(Coordinate location){
        for (int i = 0; i < boardTiles.length; i++){
            for (int j = 0; j < boardTiles[i].length; j++){
                if (i == location.getX() && j == location.getY()){
                    //Assuming  0,0 is bottom left. Freezes a 3x3 radius of tiles.
                    boardTiles[i][j].setFrozen(); //mid
                    boardTiles[i+1][j].setFrozen(); //right
                    boardTiles[i-1][j].setFrozen(); //left
                    boardTiles[i][j+1].setFrozen(); //up
                    boardTiles[i+1][j+1].setFrozen(); //upper right
                    boardTiles[i-1][j+1].setFrozen(); //upper left
                    boardTiles[i][j-1].setFrozen(); //down
                    boardTiles[i+1][j-1].setFrozen(); //down right
                    boardTiles[i-1][j-1].setFrozen(); //down left
                }
            }
        }
    }

    public Coordinate[] getSlideLocations() {
        Coordinate test = new Coordinate(-1, 0);
        Coordinate test2 = new Coordinate(1,-1);
        Coordinate test3 = new Coordinate(-1,1);
        return new Coordinate[] {test, test3, test2};
    }

    public FloorTile TileAt(Coordinate coordinate) {
        return null;
    }
}
