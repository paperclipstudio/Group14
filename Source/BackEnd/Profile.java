package BackEnd;

import java.io.*;
import java.util.Scanner;

/**
 * Stores the data about a player's profile such as their name or scores across maps.
 *
 * @author Brandon Chan
 * @version 1.0
 */
public class Profile {

	/*
	 * These attributes hold information about the player, such as, wins, losses and win streak.
	 */
	private int wins;
	private int winStreak;
	private int losses;
	private String profileName;
	private String profileIcon;

	/**
	 * This constructor initialises attributes from the parameters provided.
	 *
	 * @param name       name of the player.
	 * @param playerIcon this is the player icon.
	 * @param wins       the amount of wins a player has.
	 * @param losses     the amount of losses a player has.
	 * @param winStreak  the current win streak of this player
	 */
	public Profile(String name, String playerIcon, int wins, int losses, int winStreak) {
		setName(name);
		setIcon(playerIcon);
		setWins(wins);
		setLosses(losses);
		setWinStreak(winStreak);
	}

	/**
	 * This method sets the name of the player.
	 *
	 * @param name name of the player.
	 */
	private void setName(String name) {
		this.profileName = name;
	}

	/**
	 * This method sets the players icon.
	 *
	 * @param playerIcon player's icon.
	 */
	private void setIcon(String playerIcon) {
		this.profileIcon = playerIcon;
	}

	/**
	 * This method sets the number of wins for the player.
	 *
	 * @param wins number of wins of the player
	 */
	private void setWins(int wins) {
		this.wins = wins;
	}

	/**
	 * This method sets the number of losses for the player.
	 *
	 * @param losses number of losses of the player.
	 */
	private void setLosses(int losses) {
		this.losses = losses;
	}

	/**
	 * This method sets the current win streak of the player.
	 *
	 * @param winStreak number of games won in a row
	 */
	private void setWinStreak(int winStreak) {
		this.winStreak = winStreak;
	}

	/**
	 * This method gets the name of the player.
	 *
	 * @return the name of the player.
	 */
	public String getName() {
		return this.profileName;
	}

	/**
	 * This method gets the wins of the player.
	 *
	 * @return the wins of the player.
	 */
	public int getWins() {
		return this.wins;
	}

	/**
	 * This method gets the losses of the player.
	 *
	 * @return the losses of the player.
	 */
	public int getLosses() {
		return this.losses;
	}

	/**
	 * This method gets the win streak of the player.
	 *
	 * @return the win streak of the player
	 */
	public int getWinStreak() {
		return this.winStreak;
	}

	/**
	 * This method increments the number of wins.
	 */
	public void incWins() {
		this.wins = this.wins + 1;
	}

	/**
	 * This method increments the number of losses.
	 */
	public void incLosses() {
		this.losses = this.losses + 1;
	}

	/**
	 * This method increases the win streak by 1.
	 */
	public void incWinStreak() {
		this.winStreak = this.winStreak + 1;
	}

	/**
	 * This method returns the icon of the player.
	 *
	 * @return the icon of the player.
	 */
	public String getIcon() {
		return this.profileIcon;
	}

	/**
	 * This method resets the player's current win streak to 0.
	 */
	public void resetWinStreak() {
		this.winStreak = 0;
	}

	/**
	 * This method reads in a profile text file and creates a profile object instance of it.
	 *
	 * @param profileFile read files from UserData and turns them into profiles
	 * @return get the profile output
	 * @throws IOException Wrong input
	 */
	public static Profile readProfile(String profileFile) throws IOException {
		Scanner reader = new Scanner(new File("SaveData\\UserData\\" + profileFile + ".txt"));

		int wins = reader.nextInt();
		int losses = reader.nextInt();
		int winStreak = reader.nextInt();
		String playerIcon = reader.next();

		return new Profile(profileFile, playerIcon, wins, losses, winStreak);
	}

	/**
	 * This method creates a profile text file out of a profile object.
	 *
	 * @param profile profile to convert into a text file in UserData
	 * @throws IOException Wrong save file format
	 */
	public static void writeProfile(Profile profile) throws IOException {
		String name = profile.getName();
		int wins = profile.getWins();
		int loss = profile.getLosses();
		int winStreak = profile.getWinStreak();
		String playerIcon = profile.getIcon();
		FileWriter writer = new FileWriter("SaveData\\UserData\\" + name + ".txt");

		String information = String.format("%d %d %d %s",
				wins,
				loss,
				winStreak,
				playerIcon);
		System.out.println(information);
		writer.write(information);
		writer.flush();
		writer.close();
	}

	/**
	 * This method causes all profile object instances to save into text files.
	 *
	 * @param profiles profile object instances
	 * @throws IOException Wrong input
	 */
	public static void saveAllProfiles(Profile[] profiles) throws IOException {
		for (Profile profile : profiles) {
			writeProfile(profile);
		}
	}
}
