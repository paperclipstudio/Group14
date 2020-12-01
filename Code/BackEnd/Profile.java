package BackEnd;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;

/**
 * Stores the data about a player's profile like their name or scores across maps.
 * @author Brandon Chan
 * @version 1.0
 */
public class Profile
{
    private Player player;
    private int wins;
    private int losses;
    private int mapsPlayed;
    private String profileName;
    private String profileIcon;

    public Profile(String name, String playerIcon, int wins, int losses){
        setName (name);
        setIcon (playerIcon);
        setWins (wins);
        setlosses (losses);
    }

    public void setName (String name){
        profileName = name;
    }

    public void setIcon (String playerIcon){
        profileIcon = playerIcon;
    }

    public void setWins (int wins){
        this.wins = wins;
    }

    public void setlosses (int losses){
        this.losses = losses;
    }

    public String getName () {
        return profileName;
    }

    public int getWins () {
        return wins;
    }

    public int getLosses () {
        return losses;
    }

    public void incWins () {
        wins = wins + 1;
    }

    public void incLosses () {
        losses = losses + 1;
    }

	public String getIcon() {
        return this.profileIcon;
	}
}
