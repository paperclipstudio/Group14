/**
 * This represents an abstract class tile to be put on the Gameboard.
 * @author James Sam
 * @version 1.0
 */

public abstract class Tile {
    protected String tileType;

    /**
     * returns the type of tile.
     * @return tile type string.
     */
    public String getTileType() {
        return tileType;
    }
}
