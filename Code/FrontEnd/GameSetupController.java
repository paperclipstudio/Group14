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
		try {
			File gameboard = new File("Gameboards\\" + selectGameboard.getValue());
			if (!(saveName.getText().equals(""))) {
				// So that for testing you arn't forced to type a new save file name every time you run
				File gameSaveFile = new File("SaveData\\GameSave\\" + saveName.getText() + ".txt");
				this.gameSaveName = (saveName.getText());
				// there is no silk bag right now.
				// so seed can be created here.
				int seed = (new Random()).nextInt();
				Files.write(gameSaveFile.toPath(), ("\n" + seed).getBytes(), StandardOpenOption.APPEND);
			}
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
		wl.load("GameScreen");

	}
	/**
	 * This returns the name of the save file
	 */
	public static String getSaveName() {
		return gameSaveName;
	}
}
