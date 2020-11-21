package BackEnd;
public class FloorTile extends Tile {

	boolean	isFixed;
	TileType type;
	Rotation rotation;
	//Added by Atif as a temp way to distinguish if a tile is on fire.
	private boolean isOnFire;
	private boolean isFrozen;
	//for the method update player position, to know if a player is on a tile on the board.
	private int hasPlayer;

	public	FloorTile(TileType type){
		this.type = type;
		this.rotation = Rotation.UP;
		//
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

	public void placePlayer(int player){
		hasPlayer = player;
	}

	public int playerOnTile(){
		return this.hasPlayer;
	}

	//temp
	public void setFire() {
		this.isOnFire = true;
	}
	public void setFrozen() {
		this.isFrozen = true;
	}

	public void setRotation(Rotation up) {
	}

}
