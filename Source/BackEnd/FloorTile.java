package BackEnd;

/**
 * The FloorTile class will be the floor tiles of the Game board. These will hold information about the floor tile
 * such as the tile type, if the tile is on fire and the rotaion of the tile.
 *
 * @author Atif Ishaq & Joshua Oladitan
 * @version 1.0
 */
public class FloorTile extends Tile {

    /*
     * These attributes hold information about the floor tile. Information such as if the tile is on fire in the form
     * of a boolean and information such as TileType which is in the form of an enum.
     */
    final TileType type;
    Rotation rotation;
    private Coordinate location;

    /*
     * These attributes will be used for the action tiles freeze and fire and tick down after every players
     * turn.
     */
    private int ticFire;
    private int ticFrozen;
    private boolean isFixed;

    /**
     * This constructor of FloorTile initiates all the attributes apart from the static attributes, takes in a
     * TileType and initiates it. Sets the default rotation to UP.
     *
     * @param type This is the type of tile that in the form of an enum.
     */
    public FloorTile(TileType type) {
        this.type = type;
        this.rotation = Rotation.UP;
        this.location = null;
        this.ticFrozen = 0;
        this.ticFire = 0;

    }


    /**
     * This constructor of FloorTile initiates all attributes apart from the static ones. Takes in a TileType and
     * Rotation in the form of enums and sets the corresponding attributes.
     *
     * @param type     This is the type of tile that in the form of an enum.
     * @param rotation This is the rotation of the tile in the form of an enum.
     */
    public FloorTile(TileType type, Rotation rotation) {
        this.rotation = rotation;
        this.type = type;
    }

    /**
     * This method sets the location of the tile.
     *
     * @param location setTheCurrentLocationOfTile;
     */
    public void setLocation(Coordinate location) {
        this.location = location;
    }

    /**
     * This method returns the location of the tile.
     *
     * @return The current location of this tile.
     */
    public Coordinate getLocation() {
        return this.location;
    }

    /**
     * This method sets the location of the tile to null as its no longer present
     * on the board.
     */
    public void notOnBoard() {
        location = null;
        clearTic();
    }

    /**
     * This method returns the type of the tile.
     *
     * @return returns TileType.
     */
    public TileType getType() {
        return this.type;
    }

    /**
     * This method returns the rotation of the tile.
     *
     * @return returns rotation.
     */
    public Rotation getRotation() {
        return this.rotation;
    }

    /**
     * This method checks if the tile is on fire.
     *
     * @return true if the tile is on fire, else false otherwise.
     */
    public Boolean onFire() {
        return ticFire != 0;
    }

    /**
     * This method checks if the tile is frozen.
     *
     * @return true if the tile is frozen, else false otherwise.
     */
    public Boolean isFrozen() {
        return ticFrozen != 0;
    }

    /**
     * This method sets the rotation of the tile.
     *
     * @param rotation this is the rotation the tile is set to.
     */
    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    /**
     * This method sets the boolean onFire to true, which means the tile is now on fire
     * also ticks down until two player cycles has happened. isOnFire is then set to false as the
     * fire has worn out.
     *
     * @param numOfPlayers This lets us know how many ticks are in one cycle. Each player is one tick.
     */
    public void setFireTic(int numOfPlayers) {
        ticFire = numOfPlayers * 2; //Fire tiles last for 2 cycles.
    }

    private void clearTic() {
        ticFire = 0;
        ticFrozen = 0;
    }

    /**
     * This method decrements the ticFire after every players turn. If it reaches zero, sets isOnFire to false.
     */
    public void ticFire() {
        ticFire = ticFire <= 0 ? 0 : ticFire - 1;
    }

    /**
     * This method sets the boolean isFrozen to true, which means the tile is now frozen
     * also ticks down until one player cycles has happened. isFrozen is then set to false as the
     * freeze has worn out.
     *
     * @param numOfPlayers This lets us know how many ticks are in one cycle. Each player is one tick.
     */
    public void setFrozenTic(int numOfPlayers) {
        ticFrozen = numOfPlayers; //Frozen tiles last for 1 cycle.
    }

    /**
     * This method decrements the ticFrozen after every players turn. If it reaches zero, sets isFrozen to false.
     */
    public void ticFrozen() {
        ticFrozen = ticFrozen <= 0 ? 0 : ticFrozen - 1;
    }

    /**
     * This method checks whether a given floorTile is fixed or not, it returns true if it is, and false if it isn't.
     * @return if true if the tile is fixed
     */
    public boolean isFixed() {
        return isFixed;
    }

    /**
     * This method sets the isFixed value of a given tile to either true, or false.
     */
    public void setFixed() {
        this.isFixed = true;
    }

}
