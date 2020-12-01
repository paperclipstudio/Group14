package BackEnd;

import java.util.Random;

public class FloorTile extends Tile {
	private boolean	isFixed;
	TileType type;
	Rotation rotation;
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

	public TileType getType() {
		return type;
	}

	public Rotation getRotation() {
		return rotation;
	}

	public Boolean onFire() {
		return isOnFire;
	}

	public Boolean isFrozen() {
		return isFrozen;
	}

	public void setRotation(Rotation rotation) {
		this.rotation= rotation;
	}

	public void setFireTic (int numOfPlayers){
		this.isOnFire = true;
		ticFire = numOfPlayers * 2; //Fire tiles last for 2 cycles.
		System.out.println("Fire Tiles: " + numOfPlayers * 2);
	}

	public void ticFire (){
		ticFire --;
		if (ticFire == 0){
			isOnFire = false;
		}
	}

	public void setFrozenTic (int numOfPlayers){
		isFrozen = true;
		ticFrozen = numOfPlayers; //Frozen tiles last for 1 cycle.
		System.out.println("Frozen Tiles: " + numOfPlayers);
	}

	public void ticFrozen (){
		ticFrozen --;
		if (ticFrozen == 0){
			isFrozen = false;
		}
	}

	public boolean isFixed() {

		return (new Random()).nextBoolean();
	}

	public void setFixed(boolean fixed) {
		isFixed = fixed;
	}
}
