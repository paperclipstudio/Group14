package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

import BackEnd.Profile;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;


/**
 * After game setup, show numbers of choiceBox to let players select player profile for different player, set these files
 * to a array list for game board to use and run the game board.
 * @author Zhan Zhang
 */
public class PickPlayerController {

	@FXML
	public ChoiceBox<String> playerList1;

	@FXML
	public ChoiceBox<String> playerList2;

	@FXML
	public ChoiceBox<String> playerList3;

	@FXML
	public ChoiceBox<String> playerList4;

	@FXML
	public Label label;

	@FXML
	public Label hint;

	@FXML
	private Button backButton;

	ArrayList<Profile> profiles = new ArrayList<>();

	/**
	 * show numbers of choice box, load player saved in the SaveData folder to each box and try to get the selection
	 * when this page is running.
	 */
	public void initialize() {

		File playerLocation = new File("SaveData\\UserData\\");

		String[] players = playerLocation.list();
		
		label.setText("You decide to start a game with " + Main.getNumberOfPlayers() + " players");

		ChoiceBox<String>[] playerLists = new ChoiceBox[]{playerList1, playerList2, playerList3, playerList4};
		for (ChoiceBox<String> playerList : playerLists) {

			playerList.setVisible(false);
			playerList.getSelectionModel().selectFirst();
			assert players != null;
			for(String player : players){
				playerList.getItems().addAll(player.substring(0, player.length() - 4));
			}

		}

		for (String player : players) {
			String playerName = player.substring(0, player.length() - 4);
			playerList1.setVisible(true);
			if (Main.getNumberOfPlayers() >= 2) {
				playerList2.setVisible(true);
			}
			if (Main.getNumberOfPlayers() >= 3) {
				playerList3.setVisible(true);
			}
			if (Main.getNumberOfPlayers() >= 4) {
				playerList4.setVisible(true);
			}
		}
	}


	/**
	 * @param profileFile read files with name chose from UserData folder and turns them into profiles.
	 * @return get the profile output.
	 * @throws IOException when FileReader get wrong input.
	 */
	public Profile readProfile(File profileFile) throws IOException {
		String name = profileFile.getName();
		String playerIcon = null;
		String line;
		int wins = 0;
		int losses = 0;
		BufferedReader reader = new BufferedReader(new FileReader(profileFile));
		while ((line = reader.readLine()) != null) {
			String[] parts = line.split(" ", 3);

			if (parts.length >= 1) {
				wins = Integer.parseInt(parts[0]);
				losses = Integer.parseInt(parts[1]);
			}
		}
		return new Profile(name, playerIcon, wins, losses);

	}



	/**
	 * identify the player chose is right in number and style. Then use readProfile to turn these files to profiles and
	 * send them to game board profile class. Then run the game board class.
	 */
	public void savePlayersAndStart() {
		WindowLoader wl = new WindowLoader(backButton);
		try {

			if (Main.getNumberOfPlayers() == 2) {

				if (!playerList1.getValue().equals(playerList2.getValue())) {

					profiles.add(Profile.readProfile(playerList1.getValue()));
					profiles.add(Profile.readProfile(playerList2.getValue()));
					Main.setProfiles(profiles.toArray(new Profile[0]));

					wl.load("GameScreen");

				} else {
					hint.setText("You have to select different players in each box.");
				}

			} else if (Main.getNumberOfPlayers() == 3) {

				if (!playerList1.getValue().equals(playerList2.getValue()) &&
						!playerList1.getValue().equals(playerList3.getValue()) &&
						!playerList2.getValue().equals(playerList3.getValue())
				) {
					profiles.add(Profile.readProfile(playerList1.getValue()));
					profiles.add(Profile.readProfile(playerList2.getValue()));
					profiles.add(Profile.readProfile(playerList3.getValue()));
					Main.setProfiles(profiles.toArray(new Profile[0]));
					wl.load("GameScreen");

				} else {
					hint.setText("You have to select different players in each box.");
				}

			} else if (Main.getNumberOfPlayers() == 4) {

				if (!playerList1.getValue().equals(playerList2.getValue()) &&
						!playerList1.getValue().equals(playerList3.getValue()) &&
						!playerList1.getValue().equals(playerList4.getValue()) &&
						!playerList2.getValue().equals(playerList3.getValue()) &&
						!playerList2.getValue().equals(playerList4.getValue()) &&
						!playerList3.getValue().equals(playerList4.getValue())
				) {

					profiles.add(Profile.readProfile(playerList1.getValue()));
					profiles.add(Profile.readProfile(playerList2.getValue()));
					profiles.add(Profile.readProfile(playerList3.getValue()));
					profiles.add(Profile.readProfile(playerList4.getValue()));
					Main.setProfiles(profiles.toArray(new Profile[0]));

					wl.load("GameScreen");

				} else {
					hint.setText("You have to select different players in each box.");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	/**
	 * return to previous page
	 */

	public void onBackButton() {
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("GameSetup");
	}
}

