package FrontEnd;

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
import java.nio.file.Path;
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
	 * Continues to GameScreen
	 */
	public void onStartButton() throws IOException{
		File gameboard = new File("Gameboards\\" + selectGameboard.getValue().toString());
		File gameSave = new File("SaveData\\GameSave\\" + saveName.getText() + ".txt");
		try {
			copyFile(gameboard.toPath(), gameSave.toPath());
			WindowLoader wl = new WindowLoader(backButton);
			wl.load("GameScreen");
		} catch (Exception IOException) {
			Alert gameExists = new Alert(Alert.AlertType.ERROR);
			gameExists.setTitle("Error");
			gameExists.setContentText("Game already exists with the same name. Please enter another name.");
			gameExists.setHeaderText(null);
			gameExists.showAndWait();
		}

	}

	private static void copyFile(Path source, Path dest) throws IOException {
		Files.copy(source, dest);
	}

}
