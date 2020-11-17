package BackEnd;

public class Gameboard {

    private static int width;
    private static int height;
    private static Coordinate goalCoor;
    private Coordinate[] playerLocations;
    private slideLocations[] slideLocations;
    private ActionTileLocations[] actionTiles;


    public Gameboard (int WIDTH, int HEIGHT) {
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



    }
}
