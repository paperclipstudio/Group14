package BackEnd;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents the Silk bag that holds and distributes tiles for the game.
 *
 * @author James Sam
 * @version 1.0
 */

public class SilkBag {

    /*
     * These are variables are used for the silk bag, a list to hold all tiles and
     * a random generator to generate a random tile.
     */
    private final ArrayList<Tile> allTiles;
    private final Random randomGenerator;

    /*
     * This is a constant to save the seed for loading the game.
     */
    private final int SEED;

    /**
     * First constructor of the silk bag, which initialises attributes.
     * Giving a random integer seed for the random generator.
     *
     * @param seed the integer seed of the random generator.
     */
    public SilkBag(int seed) {
        allTiles = new ArrayList<>();
        randomGenerator = new Random(seed);
        this.SEED = seed;
    }


    /**
     * This method gets a random tile from the bag, also removes it from the bag
     *
     * @return Tile, A random tile that was generated.
     */
    public Tile getTile() {
        int index = randomGenerator.nextInt(allTiles.size());
        return allTiles.remove(index);
    }

    /**
     * This method gets a floor tile, for the game setup of the board.
     *
     * @return tile, A random floor tile.
     * @throws Exception
     */
    public FloorTile getFloorTile() throws Exception {
        // Create a random index.
        int index = randomGenerator.nextInt(allTiles.size());
        // Note the index we started at.
        int startIndex = index;
        Tile tile = allTiles.get(index);
        // Loop though from starting index until we have a floor tile
        while (!Tile.isFloorTile(tile)) {
            index = (index + 1) % allTiles.size();
            tile = allTiles.get(index);
            if (index == startIndex) {
                // We have looped and found no floor tile
                throw new Exception("No FloorTile in Silk bag");
            }
        }
        if (!allTiles.remove(tile)) {
            System.out.println("dfg");
        }
        return (FloorTile) tile;
    }

    /**
     * This method inserts a tile into the bag.
     *
     * @param tile, tile to be inserted into the bag.
     */
    public void insertTile(Tile tile) {
        allTiles.add(tile);
    }
}
