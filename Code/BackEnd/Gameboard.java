package BackEnd;
public class Gameboard {

    private int width;



    private int height;
    private static Coordinate goalCoor;
    private Coordinate[] playerLocations;
    private Coordinate[] slideLocations;
    private ActionTileLocations[] actionTiles;
    private Tile[][] boardTiles;


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
        boardTiles = new Tile[100][100];
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

        for (int i = 0; i < slideLocations.length; i++){
            if (slideLocations[i].getX() == location.getX() && slideLocations[i].getY() == location.getY()){
                if (location.getX() == -1){
                    //move everything to the right.
                }
                else if (location.getX() == width){
                    //move everything to the left.
                }
                else if (location.getY() == -1){
                    //move everything up
                }
                else{
                    //move everything down
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
    public void placeFixedTile (FloorTile tile, int x, int y) {
        for (int i = 0; i < boardTiles.length; i++){
            for (int j = 0; j < boardTiles[i].length; j++){
                if (i == x && j == y){
                    boardTiles[i][j] = tile;
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
}
