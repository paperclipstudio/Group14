package BackEnd;

/**
 * Class represents an action tile
 *
 * @author James
 */
public class ActionTile extends Tile {
    TileType type;

    /**
     * Creates an action tile
     * @param type type of this action card.
     */
    public	ActionTile(TileType type){
        this.type = type;
    }


    public TileType getType() {
        return type;
    }
}

