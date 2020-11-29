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
    private final ArrayList<Tile> allTiles;
    private final Random randomGenerator;

    /**
     * This is a constant to save the seed for loading the game.
     */
    private int SEED;

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
    public int getSeed(){
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
    public FloorTile getFloorTile () {
        int index = randomGenerator.nextInt(allTiles.size());
        int startIndex = index;
        Tile tile = allTiles.get(index);
        while(!Tile.isFloorTile(tile)) {
            tile = allTiles.get(index);
            index = (index + 1) % allTiles.size();
            if (index == startIndex) {
                // We have looped and found no floor tile
                System.out.println("No FloorTile in silkBag");
                tile = new FloorTile(TileType.GOAL);
                break;
            }
        }
        return (FloorTile) tile;
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
