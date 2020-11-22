package BackEnd;

import javafx.scene.image.Image;

/**
 * Stores the data about a player's profile like their name or scores across maps.
 * @author Brandon Chan
 * @version 1.0
 */
public class PlayerProfile
{
    private Player player;
    private int[] wins;
    private int[] losses;
    private int[] mapsPlayed;
    private String profileName;
    private Image profileIcon;

    public PlayerProfile (String name, Image playerIcon)
    {
        setName (name);
        setIcon (playerIcon);
    }

    public void setName (String name)
    {
        profileName = name;
    }

    public void setIcon (Image playerIcon)
    {
        profileIcon = playerIcon;
    }

    public String getName ()
    {
        return profileName;
    }

    public int getWins (int map)
    {
        return wins[map];
    }

    public int getLosses (int map)
    {
        return losses[map];
    }

    public void incWins (int map)
    {
        wins[map] = wins[map] + 1;
    }

    public void incLosses (int map)
    {
        losses[map] = losses[map] + 1;
    }

    /* Small improvements to the structure. (Joshua)
    public String toString () {
        return getName() + "'s Profile:"+ "\n"
                + getWins() + " Wins" + "\n"
                + getLosses() + " Losses";
    } */
}
