package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import BackEnd.Profile;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;


/**
 * After game setup, show numbers of choiceBox to let players select player profile for different player, set these files
 * to a array list for game board to use and run the game board.
 * @author zhan zhang
 */
public class PickPlayerController extends StateLoad {

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
	ChoiceBox<String>[] playerLists;

	/**
	 * show numbers of choice box, load player saved in the SaveData folder to each box and try to get the selection
	 * when this page is running.
	 */
	public void initialize(URL url, ResourceBundle rb) {
		if (getInitData() != null) {
			File playerLocation = new File("SaveData\\UserData\\");
			String[] players = playerLocation.list();
			label.setText("You decide to start a game with " + getInitData().get("PlayerCount") + " players");
			playerLists = new ChoiceBox[]{playerList1, playerList2, playerList3, playerList4};

			for (ChoiceBox playerList : playerLists) {
				playerList.setVisible(false);
				playerList.getSelectionModel().selectFirst();
				assert players != null;
				for (String player : players) {
					playerList.getItems().addAll(player.substring(0, player.length() - 4));
				}

			}

			for (String player : players) {
				String playerName = player.substring(0, player.length() - 4);
				playerList1.setVisible(true);
				int playerCount = Integer.parseInt(getInitData().get("PlayerCount"));
				if (playerCount >= 2) {
					playerList2.setVisible(true);
				}
				if (playerCount >= 3) {
					playerList3.setVisible(true);
				}
				if (playerCount >= 4) {
					playerList4.setVisible(true);
				}
			}
		}
	}


	/**
	 * add the chosen player's file to the arraylist and go to the game screen.
	 */
	public void savePlayersAndStart() {
		WindowLoader wl = new WindowLoader(backButton);
		int playerCount = Integer.parseInt(getInitData().get("PlayerCount"));
		for (int i = 0; i < playerLists.length; i++) {
			ChoiceBox<String> playerList = playerLists[i];
			getInitData().put("Profile" + i, playerList.getValue());
		}

		if (playerCount == 2) {
			if (playerList1.getValue().equals(playerList2.getValue())) {
				hint.setText("You have to select different players in each box.");
			}

		} else if (playerCount == 3) {
			if (playerList1.getValue().equals(playerList2.getValue()) ||
					playerList1.getValue().equals(playerList3.getValue()) ||
					playerList2.getValue().equals(playerList3.getValue())
			) {
				hint.setText("You have to select different players in each box.");
			}

		} else if (playerCount == 4) {
			if (playerList1.getValue().equals(playerList2.getValue()) ||
					playerList1.getValue().equals(playerList3.getValue()) ||
					playerList1.getValue().equals(playerList4.getValue()) ||
					playerList2.getValue().equals(playerList3.getValue()) ||
					playerList2.getValue().equals(playerList4.getValue()) ||
					playerList3.getValue().equals(playerList4.getValue())
			) {
				hint.setText("You have to select different players in each box.");
			}
		}
		wl.load("GameScreen", getInitData());
	}

	/**
	 * return to previous page
	 */
	public void onBackButton() {
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("GameSetup", getInitData());
	}
}

