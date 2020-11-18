import javafx.scene.image.Image;

public class PlayerProfile
{
    Player player;
    int[] wins;
    int[] losses;
    int[] mapsPlayed;
    String profileName;
    Image profileIcon;

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

    // public String toString ()
    // {
    //      getName();
    //      getWins();
    //      getLosses();
    // }
}