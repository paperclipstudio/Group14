package BackEnd;
public class FloorTile extends Tile {
	boolean	isFixed;
	TileType type;
	Rotation rotation;

	public	FloorTile(TileType type){
		this.type = type;
		this.rotation = Rotation.UP;
	}

	public FloorTile(TileType type, Rotation rotation) {
		this.rotation = rotation;
		this.type = type;
	}

	public TileType getType() { return type;
	}

	public Rotation getRotation() {
		return rotation;
	}
}
