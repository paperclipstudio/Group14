package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.AudioClip;

import java.io.File;
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

	/* These final variables are used for the game's Sound Effects (SFX) */

	private final String MAIN_MENU_SFX = "Assets\\SFX\\mainmenu.mp3";
	private final AudioClip MAIN_MENU_AUDIO = new AudioClip(new File(MAIN_MENU_SFX).toURI().toString());
	private final String RETURN_SFX = "Assets\\SFX\\return.mp3";
	private final AudioClip RETURN_AUDIO = new AudioClip(new File(RETURN_SFX).toURI().toString());
	private final String ERROR_SFX = "Assets\\SFX\\error.mp3";
	private final AudioClip ERROR_AUDIO = new AudioClip(new File(ERROR_SFX).toURI().toString());


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
		if (selectGameBoard.getValue() == null) {
			String[] gameBoards;
			File gameBoardLocation = new File("Gameboards");
			gameBoards = gameBoardLocation.list();
			if (gameBoards != null) {
				for (String gameBoard : gameBoards) {
					gameBoard = gameBoard.substring(0, gameBoard.length() - 4);
					selectGameBoard.getItems().add(gameBoard);
				}
			}
			selectGameBoard.getSelectionModel().selectFirst();
		}
		saveName.setPromptText("Enter game name");
	}

	/***
	 * Returns to main menu
	 */
	public void onBackButton() {
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("MenuScreen", getInitData());
		RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
	}

	/***
	 * This copies the gameboard file, appends the seed for the silk bag and Continues to GameScreen.
	 */
	public void onStartButton() {
		WindowLoader wl = new WindowLoader(backButton);
		getInitData().put("Seed", "" + ((new Random()).nextInt()));
		String gameBoard = selectGameBoard.getValue() + ".txt";
		getInitData().put("Board", gameBoard);
		getInitData().put("PlayerCount", ((RadioButton) playerCount.getSelectedToggle()).getText());
		getInitData().put("LoadFile", saveName.getText());
		getInitData().put("isLoadedFile", "false");
		String gameSaveName = saveName.getText();
		getInitData().put("SaveFile", gameSaveName);
		if ((gameSaveName.equals(""))) {
			ERROR_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
			saveName.setStyle("-fx-prompt-text-fill:red;");
			return;
		}
		File gameSaveFile = new File("SaveData\\GameSave\\" + gameSaveName + ".sav");
		if (gameSaveFile.exists()) {
			ERROR_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
			saveName.clear();
			saveName.setPromptText("Game already exists.");
			saveName.setStyle("-fx-prompt-text-fill:red;");
			return;
		}
		wl.load("PickPlayer", getInitData());
		MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
	}
}
