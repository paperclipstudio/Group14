package BackEnd;
public class FloorTile extends Tile {
	boolean	isFixed;
	String type;
	Rotation rotation;

	public	FloorTile(String type){
		this.type = type;
		this.rotation = Rotation.UP;
	}

	public FloorTile(String type, Rotation rotation) {
		this.rotation = rotation;
		this.type = type;
	}

	public String getType() { return type;
	}

	public Rotation getRotation() {
		return rotation;
	}
}
