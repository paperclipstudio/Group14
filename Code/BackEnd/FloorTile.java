package BackEnd;
public class FloorTile extends Tile {
	boolean	isFixed;
	TileType type;
	Rotation rotation;
	//Added by Atif as a temp way to distinguish if a tile is on fire.
	private boolean isOnFire;

	public	FloorTile(TileType type){
		this.type = type;
		this.rotation = Rotation.UP;
		//
		this.isOnFire = false;
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

	// change by Atif to return the bool.
	public Boolean onFire() {
		return isOnFire;
	}

	//temp
	public void setFire() {
		this.isOnFire = true;
	}

	public Boolean Frozen() {
		return false;
	}
}
