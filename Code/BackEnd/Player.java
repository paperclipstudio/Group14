package BackEnd;

import java.awt.event.ActionEvent;

public class Player
{
    /*
    private static int x;
    private static int y;
    ArrayList<Tile> playerInventory = new ArrayList<Tile>();
    */
    Tile lastDrawnTile;

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
/*
    public ArrayList<Tile> getInventory ()
    {
        return playerInventory;
    }
*/
    public void drawTile()
    {
        // {
        //     addToInventory(newTile);
        // }

        // else
        // {
        //      use front end input here
        // }
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
