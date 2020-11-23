package BackEnd;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents a Silk bag that holds and distributes tiles for the game.
 *  @author James Sam
 *  @version 1.0
 */


public class SilkBag {
    /**
     * These are variables are used for the silk bag, a list to hold all tiles and
     * a random generator to generate a random tile.
     */
    private  ArrayList<Tile> allTiles;
    private Random randomGenerator;

    /**
     * This is a constant to save the seed for loading the game.
     */
    private static int SEED;

    /**
     * First constructor of the silk bag, which initialises attributes.
     * Giving a random integer seed for the random generator.
     * @param seed the integer seed of the random generator.
     */
	public SilkBag(int seed) {
	    allTiles = new ArrayList<>();
	    randomGenerator = new Random(seed);
	    this.SEED = seed;
	}

    /**
     * Second constructor of the silk bag, which initialises attributes.
     */
    public SilkBag() {
        allTiles = new ArrayList<>();
        randomGenerator = new Random();
    }

    /**
     * Returns the seed.
     */
    public static int getSeed(){
        return SEED;
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
        if (tile instanceof FloorTile == false ) {
           return getFloorTile();
       }
        return tile;
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
