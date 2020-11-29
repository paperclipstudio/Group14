package BackEnd;
import java.util.InputMismatchException;

import static BackEnd.TileType.*;

/**
 * This represents an abstract class tile to be put on the Gameboard.
 * @author James Sam
 * @version 1.0
 */
public abstract class Tile {
    /**
     * Creates any type of tile.
     * @param type the type of the tile
     * @return Floor or Action tile cast to Tile.
     */
    public static Tile createTile(TileType type) {
        Tile result = null;
        if (isFloorTile(type)) {
            result = new FloorTile(type);
        } else {
            result = new ActionTile(type);
        }
       return result;
    }

    static boolean isFloorTile(Tile tile) {
       return tile instanceof FloorTile;
    }

    static boolean isFloorTile(TileType type) {
        if (type == CORNER || type == T_SHAPE || type == STRAIGHT || type == GOAL) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * returns the type of tile.
     * @return tile type string.
     */
    public abstract TileType getType();
}