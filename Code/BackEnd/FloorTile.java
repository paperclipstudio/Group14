package BackEnd;
public class FloorTile extends Tile {
	boolean	isFixed;
	String type;
	Rotation rotation;

	public	FloorTile(String type){

	}

	public FloorTile(String type, Rotation rotation) {

	}

	public String getType() { return type;
	}

	public Rotation getRotation() {
		return rotation;
	}
}
