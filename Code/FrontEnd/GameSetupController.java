package FrontEnd;

import BackEnd.SilkBag;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Use to control the GameSetup scene.
 *
 * @author Chrisitan Sanger
 */

public class GameSetupController implements Initializable {
	@FXML
	private Button backButton;

	@FXML
	private TextField saveName;

	@FXML
	private ChoiceBox<String> selectGameboard;

	@FXML
	private ToggleGroup playerCount;

	@FXML
	private RadioButton selectedToggle;

	private static String gameSaveName;

	/**
	 * Populates the choice box with available gameboards when the page is initialized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String[] gameboards;
		File gameboardLocation = new File("Gameboards");
		gameboards = gameboardLocation.list();
		for (String gameboard : gameboards) {
			selectGameboard.getItems().add(gameboard);
		}
		selectGameboard.getSelectionModel().selectFirst();
	}

	/***
	 * Returns to main menu
	 */
	public void onBackButton() {
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("MenuScreen");
	}

	/***
	 * This copys the gameboard file, appends the seed for the silk bag and Continues to GameScreen.
	 */
	public void onStartButton() {
		WindowLoader wl = new WindowLoader(backButton);
		Main.setSeed((new Random()).nextInt());
		Main.setBoardFile( selectGameboard.getValue());
		selectedToggle = (RadioButton) playerCount.getSelectedToggle();
		int numOfPlayers = Integer.parseInt(selectedToggle.getText());
		Main.setNumberOfPlayers(numOfPlayers);
		try {
			this.gameSaveName = (saveName.getText());
			if (!(gameSaveName.equals(""))) {
				File gameSaveFile = new File("SaveData\\GameSave\\" + gameSaveName + ".sav");
				if(!(gameSaveFile.exists())){
					FileWriter writer = new FileWriter(gameSaveFile, true);
					writer.write(selectGameboard.getValue() + "\n" + numOfPlayers);
					writer.flush();
					writer.close();
					//wl.load("PickPlayer");
				} else {
					saveName.setText("Game already exists - Please enter new name");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		wl.load("PickPlayer"); // here for testing
	}
	/**
	 * This returns the name of the save file
	 */
	public static String getSaveName() {
		return gameSaveName;
	}
}
