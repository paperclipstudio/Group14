package BackEnd;
public class FloorTile extends Tile {

	boolean	isFixed;
	TileType type;
	Rotation rotation;
	//Added by Atif as a temp way to distinguish if a tile is on fire.
	private boolean isOnFire;
	private boolean isFrozen;

	//These will be used for the tics, it will stop the tiles being affected after 3 turns.
	private static int ticFire;
	private static int ticFrozen;

	public	FloorTile(TileType type){
		this.type = type;
		this.rotation = Rotation.UP;
		this.isOnFire = false;
		this.isFrozen = false;
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

	// change by Atif to return the attribute.
	public Boolean onFire() {
		return isOnFire;
	}

	public Boolean isFrozen() {
		return isFrozen;
	}

	public void setFire() {
		this.isOnFire = true;
	}
	public void setFrozen() {
		this.isFrozen = true;
	}

	public void setRotation(Rotation rotation) {
		this.rotation= rotation;
	}

	//temp
	public static void setFireTic (){
		ticFire = 3;
	}



}
