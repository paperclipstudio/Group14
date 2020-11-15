import java.util.ArrayList;

public class Player
{
    ArrayList<Tile> playerInventory = new ArrayList<Tile>();
    Tile lastDrawnTile;
    int playerNumber;
    Boolean backtrackUsedOnMe = false;

    public Player (int playerNo)
    {
        playerNumber = playerNo;
    }

    public ArrayList<Tile> getInventory ()
    {
        return playerInventory;
    }

    public void drawTileFromBag ()
    {
        // if newTile.type = ACTION_TILE
        // {
        //     addToInventory(newTile);
        // }

        // else
        // {
        //      use front end input here
        // }
    }

    public void playFloorTile (Coor slideLocations)
    {
        // if !(slideLocations in GAMEBOARD.getFrozenEffectedArea())  need to check entire row/column
        // {
        //      GAMEBOARD.slideTile(slideLocations);
        // }
    }

    public void playActionTile (Coor location, Tile tile)
    {
        if (tile instanceof FREEZE_TILE)
        {
            // GAMEBOARD.setFreezeCoords(location);
            // removeFromInventory(tile);
        }

        if (tile instanceof FIRE_TILE)
        {
            // GAMEBOARD.setFireCoords(location);
            // removeFromInventory(tile);
        }

        if (tile instanceof DOUBLE_MOVE_TILE)
        {
            // move twice somewhere, change this according to how front end team implements user input
            // removeFromInventory(tile);
        }

        if (tile instanceof BACKTRACK_TILE)
        {
            // if (!CHOSEN_PLAYER.backtrackUsedOnMe)
            // {
            //     if !(Coor[playerNumber][1] = Coor[OTHER_PLAYER_NUMBERS][0])
            //     or !(Coor[playerNumber[1] in GAMEBOARD.getFireEffectedArea())
            //     {
            //          if !(Coor[playerNumber][2] = Coor[OTHER_PLAYER_NUMBERS][0])
            //          and !(Coor[playerNumber[2] in GAMEBOARD.getFireEffectedArea())
            //          {
            //              movePlayer(Coor[playerNumber][2]);
            //          }
            //          else
            //          {
            //              movePlayer(Coor[playerNumber][1]);
            //          }
            //      }
            //      backtrackUsedOnMe = true;
            //      removeFromInventory(tile);
            // }
        }
    }

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
}
