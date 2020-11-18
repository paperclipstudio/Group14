import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents a Silk bag that holds and distributes tiles for the game.
 *  @author James Sam
 *  @version 1.0
 */


public class SilkBag {
    private ArrayList<Tile> allTiles = new ArrayList<Tile>();
    private Random randomGenerator = new Random();

    /**
     * Get a tile from bag.
     * @return a Tile
     */
    public Tile getTile() {
        int index = randomGenerator.nextInt(allTiles.size());
        return allTiles.get(index);
    }

    public Tile getFloorTile () {
        int index = randomGenerator.nextInt(allTiles.size());
        if (getTile().getTileType().equals("FloorTile")) {
           return allTiles.get(index);
       }
        else {
            return null;
        }
    }

    /**
     * Inserts tile into the bag.
     * @param tile
     */
    public void insertTile (Tile tile) {
        allTiles.add(tile);
    }
}
