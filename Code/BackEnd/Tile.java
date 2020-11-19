package BackEnd;

import java.util.InputMismatchException;

/**
 * This represents an abstract class tile to be put on the Gameboard.
 * @author James Sam
 * @version 1.0
 */

public abstract class Tile {
    protected String tileType;
    public static Tile createTile(String type) {
        Tile result;
       switch (type) {
           case "Corner":
           case "T-Shape":
           case "Straight":
           case "Goal":
               result = (Tile) new FloorTile(type);
               break;
           case "Fire":
           case "Ice":
           case "Backtrack":
           case "DoubleMove":
               result = (Tile) new ActionTile(type);
               break;
           default:
               throw new InputMismatchException(type);
       }
       return result;
    }




    /**
     * returns the type of tile.
     * @return tile type string.
     */
    public String getTileType() {
        return tileType;
    }
}