package BackEnd;
public class ActionTile extends Tile {
    TileType type;

    public	ActionTile(TileType type){
        this.type = type;
    }

    public TileType getType() {
        return type;
    }
}

