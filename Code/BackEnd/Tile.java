package BackEnd;
import java.util.InputMismatchException;
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
        Tile result;
       switch (type) {
           case CORNER:
           case T_SHAPE:
           case STRAIGHT:
           case GOAL:
               result = new FloorTile(type);
               break;
           case FIRE:
           case FROZEN:
           case BACKTRACK:
           case DOUBLE_MOVE:
               result = new ActionTile(type);
               break;
           default:
               result = new FloorTile(TileType.GOAL);

       }
       return result;
    }
    /**
     * returns the type of tile.
     * @return tile type string.
     */
    public abstract TileType getType();
}