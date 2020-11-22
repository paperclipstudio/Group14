package BackEnd;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import static BackEnd.TileType.FIRE;
import static BackEnd.TileType.FROZEN;

/**
 * Stores details about the player while they are in-game, for example their inventory or player number.
 * @author Brandon Chan
 * @version 1.0
 */
public class Player
{
    /**
     * Location variable will hold the players location.
     */
    private Coordinate location;
    /**
     * lastDrawnTile is the players last drawn tile.
     */
    private Tile lastDrawnTile;
    /**
     * inventory variable is a list of the player tiles that he holds in his inventory.
     */
    private ArrayList<Tile> inventory;
    /**
     * silkbag is an instance of the SilkBag class.
     */
    private SilkBag silkBag;
    /**
     * playerNumber is used to distinguish which player it is.
     */
    private int playerNumber;
    /**
     * backTracked is used to check if the player has backtracked.
     */
    private boolean backTracked;
    /**
     * gameboard is an instance of the gameBoard class.
     */
    private Gameboard gameboard;

    /**
     * Constructor for storing the starting coordinates of the player.
     * @param x The starting x coordinate of the Player object
     * @param y The starting y coordinate of the Player object
     */
    /*
    public Player(int x, int y) {
        location = new Coordinate(x, y);
    }

     */


    /**
     * Create a player and give them the silk bag and gameboard references.
     * @param playerNumber The player's number, used to distinguish who's turn it is.
     * @param silkBag Reference to the game's silk bag object.
     * @param gameboard Reference to the game's gameboard object.
     */
    public Player(int playerNumber, SilkBag silkBag, Gameboard gameboard) {
        this.silkBag = silkBag;
        this.playerNumber = playerNumber;
        this.gameboard = gameboard;
    }


    /**
     * Method to get the action tiles in the player's inventory.
     * @return inventory The action tiles in the player's inventory.
     */
    public ArrayList<Tile> getInventory () {
        return inventory;
    }


    /**
     * Draw a tile - if the player's previous action card draw is still in its grace period, add it to inventory first.
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
        //TODO call a playFloorTile function from the gameboard

        lastDrawnTile = null;
    }


    /**
     * Play the selected action tile in the given location (provided it needs to be)
     * @param location where the player wants to use the action
     * @param tile type of action tile
     */
    public void playActionTile (Coordinate location, ActionTile tile)    {
        //TODO call a playActionTile function from the gameboard
        gameboard.playActionTile();
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
}
