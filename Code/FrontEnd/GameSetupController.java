package FrontEnd;

import BackEnd.SilkBag;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
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
	private ChoiceBox selectGameboard;
	/**
	 * Constant for the name of the save file.
	 */
	private static String SAVE_NAME;

	/**
	 * Populates the choice box with available gameboards when the page is initialized.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String[] gameboards;
		File gameboardLocation = new File("Gameboards");
		gameboards = gameboardLocation.list();
		for (String gameboard : gameboards)
			selectGameboard.getItems().add(gameboard);
	}

	/***
	 * Returns to main menu
	 */
	public void onBackButton() {
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("MenuScreen");
	}

	/***
	 * Copys gameboard file, appends the seed for the silk bag and Continues to GameScreen.
	 */
	public void onStartButton() {
		try {
			File gameboard = new File("Gameboards\\" + selectGameboard.getValue().toString());
			File gameSave = new File("SaveData\\GameSave\\" + saveName.getText() + ".txt");
			Files.copy(gameboard.toPath(), gameSave.toPath());
			WindowLoader wl = new WindowLoader(backButton);
			wl.load("GameScreen");
			this.SAVE_NAME = (saveName.getText());
			String seed = ("\n" + SilkBag.getSeed());
			Files.write(gameSave.toPath(), seed.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			Alert gameExists = new Alert(Alert.AlertType.ERROR);
			gameExists.setTitle("Error");
			gameExists.setContentText("Game already exists with the same name. Please enter another name.");
			gameExists.setHeaderText(null);
			gameExists.showAndWait();
		} catch (Exception e) {
			Alert noGameboard = new Alert(Alert.AlertType.ERROR);
			noGameboard.setTitle("Error");
			noGameboard.setContentText("Please select a Gameboard.");
			noGameboard.setHeaderText(null);
			noGameboard.showAndWait();
		}

	}
	/**
	 * This returns the name of the save file
	 */
	public static String getSaveName() {
		return SAVE_NAME;
	}
}
