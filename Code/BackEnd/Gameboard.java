package BackEnd;
public class Gameboard {

    private int width;
    private int height;
    private static Coordinate goalCoor;
    private Coordinate[] playerLocations;
    private Coordinate[] slideLocations;
    private ActionTileLocations[] actionTiles;
    private FloorTile[][] boardTiles;


    public Gameboard (int width, int height) {
        this.width = width;
        this.height = height;
        Coordinate locationOne = new Coordinate(0, -1);
        Coordinate locationTwo = new Coordinate(-1, -0);
        Coordinate locationThree = new Coordinate(-1, height-1);
        Coordinate locationFour = new Coordinate(width, height-1);
        Coordinate locationFive = new Coordinate(width, 0);
        //TODO Added as a quick fix by George.
        slideLocations = new Coordinate[100];
        //TODO turns out nothing has been initalised.
        boardTiles = new FloorTile[100][100];
        //TODO third null pointer exception
        playerLocations = new Coordinate[100];
        slideLocations[0] = locationOne;
        slideLocations[1] = locationTwo;
        slideLocations[2] = locationThree;
        slideLocations[3] = locationFour;
        slideLocations[4] = locationFive;
    }

    public Coordinate getPlayerPos (int player){
        return playerLocations[player];
    }

    public void setPlayerPos (int player, Coordinate position){
        this.playerLocations[player] = position;
    }

    public void playFloorTile (Coordinate location, FloorTile tileType){
        // Loop based off slide location length? Could use a loop based off width and height depending
        // on whether it was slid in from vertical or horizontal. (Joshua)
        for (int i = 0; i < slideLocations.length; i++){
            if (slideLocations[i].getX() == location.getX() && slideLocations[i].getY() == location.getY()){
                if (location.getX() == -1){
                    //move everything to the right.
                }
                else if (location.getX() == width){
                    //move everything to the left.
                }
                else if (location.getY() == -1){
                    //move everything up.
                }
                else{
                    //move everything down.
                }
            }
        }
        //Tile topLeft = boardTiles[0][height-1];
        //System.out.println(topLeft.getType());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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

    public static void setGoalCoor(Coordinate goalCoor) {
        goalCoor = goalCoor;
    }

    public static Coordinate getGoalCoor() {
        return goalCoor;
    }

    //checks to see if a player is on goal by going through all the players' locations to see
    //if they match the goal coordinates.
    public boolean isPlayerOnGoal() {
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
    }

    //this method would be used to help with the movement of the player.
    private Coordinate checkTileMovement(FloorTile tile, Rotation rotation){


        return new Coordinate(0,0)
    }

    /*
    public Tile getPlayerTile(int player) {

    }

    public Coordinate[] getPlayerMoveLocations(int player) {
        Coordinate[] moveLocations;
    }
    */

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
