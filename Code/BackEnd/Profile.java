package BackEnd;

import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;

/**
 * Stores the data about a player's profile such as their name or scores across maps.
 * @author Brandon Chan
 * @version 1.0
 */
public class Profile {

    /*
     * These attributes hold information about the player, such as, wins, losses and maps played.
     */
    private Player player;
    private int wins;
    private int losses;
    private int mapsPlayed;
    private String profileName;
    private String profileIcon;

    /**
     * This constructor initialises attributes from the parameters provided.
     * @param name name of the player.
     * @param playerIcon this is the player icon.
     * @param wins the amount of wins a player has.
     * @param losses the amount of losses a player has.
     */
    public Profile (String name, String playerIcon, int wins, int losses) {
        setName (name);
        setIcon (playerIcon);
        setWins (wins);
        setlosses (losses);
    }

    /**
     * This method sets the name of the player.
     * @param name name of the player.
     */
    public void setName (String name) {
        profileName = name;
    }

    /**
     * This method sets the players icon.
     * @param playerIcon player's icon.
     */
    public void setIcon (String playerIcon) {
        profileIcon = playerIcon;
    }

    /**
     * This method sets the number of wins for the player.
     * @param wins number of wins of the player
     */
    public void setWins (int wins){
        this.wins = wins;
    }

    /**
     * This method sets the number of losses for the player.
     * @param losses number of losses of the player.
     */
    public void setlosses (int losses){
        this.losses = losses;
    }

    /**
     * This method gets the name of the player.
     * @return the name of the player.
     */
    public String getName () {
        return profileName;
    }

    /**
     * This method gets the wins of the player.
     * @return the wins of the player.
     */
    public int getWins () {
        return wins;
    }

    /**
     * This method gets the losses of the player.
     * @return the losses of the player.
     */
    public int getLosses () {
        return losses;
    }

    /**
     * This method increments the number of wins.
     */
    public void incWins () {
        wins = wins + 1;
    }

    /**
     * This method increments the number of losses.
     */
    public void incLosses () {
        losses = losses + 1;
    }

    /**
     * This method returns the icon of the player.
     * @return the icon of the player.
     */
	public String getIcon() {
        return this.profileIcon;
	}
}
