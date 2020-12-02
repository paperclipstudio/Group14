package BackEnd;

/**
 * This class represents an Action Tile in the game. An action tile is a tile that the player can use to affect
 * the state of either the floor tiles, other players, or themselves.
 * @author Joshua Oladitan & Atif Ishaq.
 * @version 1.0
 */
public class ActionTile extends Tile {

    /*
     * This type relates to the enum TileType, which every floor and action tile has one of.
     */
    TileType type;

    /**
     * This constructor of ActionTile creates an action tile of a given type.
     * @param type The type of the action tile.
     */
    public ActionTile(TileType type){
        this.type = type;
    }

    /**
     * Returns the type of a given action tile.
     * @return type The type of the action tile.
     */
    public TileType getType() {
        return type;
    }
}

