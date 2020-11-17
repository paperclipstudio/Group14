package BackEnd;

public class ActionTileLocations {

    private ActionTile tileType;
    private Coordinate tileCoor;

    public ActionTileLocations(ActionTile tileType, Coordinate tileCoor) {
        this.tileType = tileType;
        this.tileCoor = tileCoor;
    }

    public ActionTile getActionTile(){
        return tileType;
    }

    public void setActionTile(ActionTile tileType){
        this.tileType = tileType;
    }

    public Coordinate getTileCoor() {
        return tileCoor;
    }

    public void setTileCoor(Coordinate tileCoor){
        this.tileCoor = tileCoor;
    }
}
