package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * This class is used to control the GameSetup scene.
 *
 * @author Christian Sanger.
 * @version 1.0
 */

public class GameSetupController extends StateLoad {
	@FXML
	private Button backButton;

    @FXML
    private TextField saveName;

	@FXML
	private ChoiceBox<String> selectGameBoard;

    @FXML
    private ToggleGroup playerCount;

	/**
	 * Populates the choice box with the available gameboards when the page is initialized.
	 * @param location
	 * @param resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String[] gameBoards;
		File gameBoardLocation = new File("Gameboards"); // TODO move to folder //saveData//
		gameBoards = gameBoardLocation.list();
		if (gameBoards != null) {
			for (String gameBoard : gameBoards) {
				selectGameBoard.getItems().add(gameBoard);
			}
		}
		selectGameBoard.getSelectionModel().selectFirst();
	}

	/***
	 * Returns to main menu
	 */
	public void onBackButton() {
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("MenuScreen", getInitData());
	}

	/***
	 * This copies the gameboard file, appends the seed for the silk bag and Continues to GameScreen.
	 */
	public void onStartButton() {
		WindowLoader wl = new WindowLoader(backButton);
		getInitData().put("Seed", "" + ((new Random()).nextInt()));
		getInitData().put("Board", selectGameBoard.getValue());
		getInitData().put("PlayerCount", ((RadioButton) playerCount.getSelectedToggle()).getText());
		getInitData().put("LoadFile", saveName.getText());
		getInitData().put("isLoadedFile", "false");
		RadioButton selectedToggle = (RadioButton) playerCount.getSelectedToggle();
		String gameSaveName = saveName.getText();
		getInitData().put("SaveFile", gameSaveName);
		if ((gameSaveName.equals(""))) {
			saveName.setText("Must Enter game name");
			return;
		}
		File gameSaveFile = new File("SaveData\\GameSave\\" + gameSaveName + ".sav");
		if (gameSaveFile.exists()) {
			saveName.setText("Game already exists - Please enter new name");
			return;
		}
		try {
			FileWriter writer = new FileWriter(gameSaveFile, true);
			writer.write(selectGameBoard.getValue());
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			saveName.setText("Failed to make game please try again");
		}
		wl.load("PickPlayer", getInitData());
	}
}
