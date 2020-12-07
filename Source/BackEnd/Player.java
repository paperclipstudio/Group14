package BackEnd;

import java.util.ArrayList;

/**
 * Stores details about the player while they are in-game, for example their inventory or player number.
 *
 * @author Brandon Chan
 * @version 1.0
 */
public class Player {
    /*
     * Location variable will hold the players location.
     * lastDrawnTile is the players last drawn tile.
     * inventory variable is a list of the player tiles that he holds in his inventory.
     * silkbag is an instance of the SilkBag class
     * playerNumber is used to distinguish which player it is.
     * backTracked is used to check if the player has backtracked.
     * gameboard is an instance of the gameBoard class.
     */
    private Tile lastDrawnTile;
    private final ArrayList<ActionTile> inventory;
    private final SilkBag silkBag;
    private boolean backTracked;
    private final Gameboard gameboard;

    /**
     * Create a player and give them the silk bag and gameboard references.
     *
     * @param silkBag      Reference to the game's silk bag object.
     * @param gameboard    Reference to the game's gameboard object.
     */
    public Player(SilkBag silkBag, Gameboard gameboard) {
        this.silkBag = silkBag;
        this.inventory = new ArrayList<>();
        this.gameboard = gameboard;
        this.backTracked = false;
    }


    /**
     * Method to get the action tiles in the player's inventory.
     *
     * @return inventory The action tiles in the player's inventory.
     */
    public ArrayList<ActionTile> getInventory() {
        return this.inventory;
    }


    /**
     * Draw a tile - if the player's previous action card draw is still in its grace period, add it to inventory first.
     */
    public void drawTile() {
        if (isHolding() != null) {
            if (isHolding() instanceof ActionTile) {
                inventory.add((ActionTile) isHolding());
            } else {
                System.out.println("Left over floor tile");
            }
        }
        lastDrawnTile = silkBag.getTile();
    }


    /**
     * Play a floor tile in a given location and rotation.
     *
     * @param slideLocations Where the player wants to slide a tile in from
     * @param tile           What tile the player wants to play
     */
    public void playFloorTile(Coordinate slideLocations, FloorTile tile) throws Exception {
        gameboard.playFloorTile(slideLocations, tile);
        lastDrawnTile = null;
    }


    /**
     * Method for playing a freeze or fire tile
     *
     * @param location     The center of the freeze/fire location null if action doesn't has a location
     * @param tile         The freeze or fire action tile
     * @param playerNumber the player that this is played on. ignored if action isn't played on a player.
     */
    public void playActionTile(Coordinate location, ActionTile tile, int playerNumber) throws Exception {
        gameboard.playActionTile(location, tile, playerNumber);
        removeFromInventory(tile);
    }


    /**
     * Returns the player's last drawn tile
     *
     * @return lastDrawnTile The player's last drawn tile
     */
    public Tile isHolding() {
        return lastDrawnTile;
    }

    /**
     * Set that this player cannot be backtracked again.
     */
    public void setBeenBackTracked() {
        this.backTracked = true;
    }

    /**
     * Gets whether the player has had the backtrack action used on them in
     * the history of the current game.
     *
     * @return backTracked Record of if backtrack action has been used on this player
     */
    public boolean hasBeenBacktracked() {
        return backTracked;
    }

    /**
     * Takes an action tile out of the inventory
     *
     * @param tile The Action Tile to be removed
     */
    private void removeFromInventory(ActionTile tile) {
        inventory.remove(tile);
    }
}
