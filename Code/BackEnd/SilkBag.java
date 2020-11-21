package BackEnd;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents a Silk bag that holds and distributes tiles for the game.
 *  @author James Sam
 *  @version 1.0
 */


public class SilkBag {
    private  ArrayList<Tile> allTiles = new ArrayList<>();
    private Random randomGenerator = new Random();

	public SilkBag(int seed) {
	    randomGenerator = new Random(seed);
	}

    public SilkBag() {

    }


    /**
     * Get a tile from bag.
     * @return a Tile
     */
    public Tile getTile() {
        int index = randomGenerator.nextInt(allTiles.size());
        return allTiles.remove(index);
    }

    /**
     * Gets a floor tile from bag for setup
     * @return a Tile
     */
    public Tile getFloorTile () {
        Tile tile = getTile();
        //TODO Small change from getTileType -> GetType so it can compile. (Joshua)
        if (tile instanceof FloorTile ) {
           return tile;
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

    private void removeTile(int index){
        allTiles.remove(index);
    }
}
