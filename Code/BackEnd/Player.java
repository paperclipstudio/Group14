package BackEnd;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * Stores details about the player while they are in-game, for example their inventory or player number.
 * @author Brandon Chan
 * @version 1.0
 */
public class Player
{
    Coordinate location;
    Tile lastDrawnTile;
    ArrayList<Tile> inventory;
    SilkBag silkBag;
    int playerNumber;
    private boolean backTracked;

    /**
     * Constructor for storing the starting coordinates of the player
     * @param x The starting x coordinate of the Player object
     * @param y The starting y coordinate of the Player object
     */
    public Player(int x, int y) {
        location = new Coordinate(x, y);
    }


    /**
     * idk what this is
     * @param playerNumber
     * @param silkBag
     * @param gameboard
     */
    public Player(int playerNumber, SilkBag silkBag, Gameboard gameboard) {
        this.silkBag = silkBag;
        this.playerNumber = playerNumber;
    }


    /**
     * Method to get the action tiles in the player's inventory.
     * @return inventory The action tiles in the player's inventory.
     */
    public ArrayList<Tile> getInventory () {
        return inventory;
    }


    /**
     *
     */
    public void drawTile()    {
        if (isHolding() != null) {
            inventory.add(isHolding());
        }
       lastDrawnTile = silkBag.getTile();
    }


    /**
     * Play a floor tile in a given location and rotation.
     * @param slideLocations Where the player wants to slide a tile in from
     * @param rotation What orientation the player wants the tile to be slid in at
     */
    public void playFloorTile (Coordinate slideLocations, Rotation rotation)    {


        lastDrawnTile = null;
    }


    /**
     * Play the selected action tile in the given location (provided it needs to be)
     * @param location where the player wants to use the action
     * @param tile type of action tile
     */
    public void playActionTile (Coordinate location, Tile tile)    {

        inventory.remove(tile);
    }


    /**
     * Returns the player's last drawn tile
     * @return lastDrawnTile The player's last drawn tile
     */
    public Tile isHolding() {
        return lastDrawnTile;
    }


    /**
     * Gets whether the player has had the backtrack action used on them in
     * the history of the current game.
     * @return backTracked Record of if backtrack action has been used on this player
     */
    public boolean hasBeenBacktracked() {
        return backTracked;
    }
/*
    public void movePlayer (Coordinate location)
    {
        // GAMEBOARD.movePlayer(playerNumber, location);
    }
*/
}
