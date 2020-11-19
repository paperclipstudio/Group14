package BackEnd;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Player
{
    /*
    private static int x;
    private static int y;
    ArrayList<Tile> playerInventory = new ArrayList<Tile>();
    */
    Tile lastDrawnTile;
    ArrayList<Tile> inventory;
    SilkBag silkBag;
    int playerNumber;
    private boolean backTracked;

    /*
    Boolean backtrackUsedOnMe = false;

    public Player (int x, int y){
        this.x = x;
        this.y = y;
    }
*/
    public Player(int no, int playerNo)
    {
        playerNumber = playerNo;
    }

    public Player(int playerNumber, SilkBag silkBag, Gameboard gameboard) {
        this.silkBag = silkBag;
        this.playerNumber = playerNumber;
    }


    /*
		public ArrayList<Tile> getInventory ()
		{
			return playerInventory;
		}
	*/
    public void drawTile()
    {
        if (isHolding() != null) {
            inventory.add(isHolding());
        }
       lastDrawnTile = silkBag.getTile();
    }

    public void playFloorTile (Coordinate slideLocations, Rotation rotation)
    {
        // if !(slideLocations in GAMEBOARD.getFrozenEffectedArea())  need to check entire row/column
        // {
        //      GAMEBOARD.slideTile(slideLocations);
        // }
    }

    public void playActionTile (Coordinate location, ActionTile tile)
    {
    }

    public Tile isHolding() {
        return lastDrawnTile;
    }

    public boolean hasBeenBacktracked() {
        return backTracked;
    }
/*
    public void movePlayer (Coor location)
    {
        // GAMEBOARD.movePlayer(playerNumber, location);
    }

    private void addToInventory (Tile tile)
    {
        playerInventory.add(tile);
        lastDrawnTile = tile;
    }

    private void removeFromInventory (Tile tile)
    {
        playerInventory.remove(new Tile(tile));
    }

     */
}
