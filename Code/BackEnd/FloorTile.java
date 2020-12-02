package BackEnd;

import java.util.Random;

/**
 * The FloorTile class will be the floor tiles of the Game board. These will hold information about the floor tile
 * such as the tile type, if the tile is on fire and the rotaion of the tile.
 * @author Atif Ishaq & Joshua Oladitan
 * @version 1.0
 */
public class FloorTile extends Tile {

	/*
	 * These attributes hold information about the floor tile. Information such as if the tile is on fire in the form
	 * of a boolean and information such as TileType which is in the form of an enum.
	 */
	private boolean	isFixed;
	TileType type;
	Rotation rotation;
	private boolean isOnFire;
	private boolean isFrozen;

	/*
	 * These static attributes, will be used for the action tiles freeze and fire and tick down after every players
	 * turn.
	 */
	private static int ticFire;
	private static int ticFrozen;

	/**
	 * This constructor of FloorTile initiates all the attributes apart from the static attributes, takes in a
	 * TileType and initiates it. Sets the default rotation to UP.
	 * @param type This is the type of tile that in the form of an enum.
	 */
	public FloorTile (TileType type) {
		this.type = type;
		this.rotation = Rotation.UP;
		this.isOnFire = false;
		this.isFrozen = false;
	}

	/**
	 * This constructor of FloorTile initiates all attributes apart from the static ones. Takes in a TileType and
	 * Rotation in the form of enums and sets the corresponding attributes.
	 * @param type This is the type of tile that in the form of an enum.
	 * @param rotation This is the rotation of the tile in the form of an enum.
	 */
	public FloorTile (TileType type, Rotation rotation) {
		this.rotation = rotation;
		this.type = type;
		this.isOnFire = false;
		this.isFrozen = false;
	}

	/**
	 * This method returns the type of the tile.
	 * @return returns TileType.
	 */
	public TileType getType () {
		return this.type;
	}

	/**
	 * This method returns the rotation of the tile.
	 * @return returns rotation.
	 */
	public Rotation getRotation () {
		return this.rotation;
	}

	/**
	 * This method checks if the tile is on fire.
	 * @return true if the tile is on fire, else false otherwise.
	 */
	public Boolean onFire () {
		return this.isOnFire;
	}

	/**
	 * This method checks if the tile is frozen.
	 * @return true if the tile is frozen, else false otherwise.
	 */
	public Boolean isFrozen () {
		return this.isFrozen;
	}

	/**
	 * This method sets the rotation of the tile.
	 * @param rotation this is the rotation the tile is set to.
	 */
	public void setRotation (Rotation rotation) {
		this.rotation = rotation;
	}

	/**
	 * This method sets the boolean onFire to true, which means the tile is now on fire
	 * also ticks down until two player cycles has happened. isOnFire is then set to false as the
	 * fire has worn out.
	 * @param numOfPlayers This lets us know how many ticks are in one cycle. Each player is one tick.
	 */
	public void setFireTic (int numOfPlayers) {
		this.isOnFire = true;
		ticFire = numOfPlayers * 2; //Fire tiles last for 2 cycles.
		System.out.println("Fire Tiles: " + numOfPlayers * 2);
	}

	/**
	 * This method decrements the ticFire after every players turn. If it reaches zero, sets isOnFire to false.
	 */
	public void ticFire () {
		ticFire --;
		if (ticFire == 0){
			isOnFire = false;
		}
	}

	/**
	 * This method sets the boolean isFrozen to true, which means the tile is now frozen
	 * also ticks down until one player cycles has happened. isFrozen is then set to false as the
	 * freeze has worn out.
	 * @param numOfPlayers This lets us know how many ticks are in one cycle. Each player is one tick.
	 */
	public void setFrozenTic (int numOfPlayers) {
		this.isFrozen = true;
		ticFrozen = numOfPlayers; //Frozen tiles last for 1 cycle.
		System.out.println("Frozen Tiles: " + numOfPlayers);
	}

	/**
	 * This method decrements the ticFrozen after every players turn. If it reaches zero, sets isFrozen to false.
	 */
	public void ticFrozen () {
		ticFrozen --;
		if (ticFrozen == 0){
			isFrozen = false;
		}
	}

	/*
	public boolean isFixed() {

		return (new Random()).nextBoolean();
	}
	 */

	/*
	public void setFixed(boolean fixed) {
		isFixed = fixed;
	}
	 */
}
