import sun.security.ssl.CookieExtension;

public class Gameboard {

    private static int width;
    private static int height;
    private static Coordinate goalCoor;
    private Coordinate[] playerLocations;
    private Coordinate[] slideLocations;
    private ActionTileLocations[] actionTiles;
    private Tile[][] boardTiles;


    public Gameboard (int width, int height) {
        width = this.width;
        height = this.height;
    }

    public Coordinate getPlayerPos (int player){
        return playerLocations[player];
    }

    public void setPlayerPos (int player, Coordinate position){
        this.playerLocations[player] = position;
    }

    public void playFloorTile (Coordinate slideLocation, FloorTile tileType, Rotation rotation){



        //Tile topLeft = boardTiles[0][height-1];
        //System.out.println(topLeft.getType());

    }

    public void placeFixedTile (FloorTile tile, int x, int y, int rotation) {
        for (int i = 0; i < boardTiles.length; i++){
            for (int j = 0; j < boardTiles[i].length; j++){
                if (i == x && j == y){
                    boardTiles[i][j] = tile;
                    boardTiles[i][j].setRotation(rotation);
                }
            }
        }
    }
}