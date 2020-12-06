package FrontEnd;

import BackEnd.GameLoad;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/***
 * Screen to load a previously saved game.
 * @author Christian Sanger
 */
public class LoadGameController extends StateLoad {

	private final String MAIN_MENU_SFX = "Assets\\SFX\\mainmenu.mp3";
	private final AudioClip MAIN_MENU_AUDIO = new AudioClip(new File(MAIN_MENU_SFX).toURI().toString());
	private final String RETURN_SFX = "Assets\\SFX\\return.mp3";
	private final AudioClip RETURN_AUDIO = new AudioClip(new File(RETURN_SFX).toURI().toString());
	private final String ERROR_SFX = "Assets\\SFX\\error.mp3";
	private final AudioClip ERROR_AUDIO = new AudioClip(new File(ERROR_SFX).toURI().toString());
	private final String START_SFX = "Assets\\SFX\\start.mp3";
	private final AudioClip START_AUDIO = new AudioClip(new File(START_SFX).toURI().toString());


	@FXML
	private Button backButton;

    @FXML
    private ChoiceBox<String> selectGame;

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;

    @FXML
    private Text confirm;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (selectGame.getValue() == null) {
			String[] games;
			File gameSaveLocation = new File("SaveData\\GameSave");
			games = gameSaveLocation.list();
			if (games != null) {
				for (String game : games) {
					game = game.substring(0, game.length() - 4);
					selectGame.getItems().add(game);
				}
			}
			selectGame.getSelectionModel().selectFirst();
		}
		yesButton.setVisible(false);
		noButton.setVisible(false);
		confirm.setText("");
    }

	/***
	 * Returns to menu screen
	 * Called my backButton
	 */
	public void onBackButton() {
		RETURN_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("MenuScreen", getInitData());
	}

	/***
	 * Continues to load game.
	 * Called by start button.
	 */
	public void onPlayButton() {
		WindowLoader wl = new WindowLoader(backButton);
		getInitData().put("isLoadedFile", "true");
		String loadFile = selectGame.getValue() + ".sav";
		getInitData().put("LoadFile", loadFile);
		try {
			GameLoad.loader(getInitData());
			wl.load("GameScreen", getInitData());
			START_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
		} catch (Exception e) {
			ERROR_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
			confirm.setText("File Corrupted due too: " + e.getMessage());
		}
	}

	/**
	 * Double checks that user wants to delete file.
	 */
	public void onDeleteButton() {
		MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
		yesButton.setVisible(true);
		noButton.setVisible(true);
		confirm.setText("Are you sure?");
	}

	/**
	 * End deleting game save mode without deleting game.
	 */
	public void onNoButton() {
		MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
		yesButton.setVisible(false);
		noButton.setVisible(false);
		confirm.setText("");
	}

	/**
	 * Deletes the selected game.
	 * @throws IOException if game failed to delete.
	 */
	public void onYesButton() throws IOException {
		MAIN_MENU_AUDIO.play(Double.parseDouble(getInitData().get("SFXVol")));
		File saveFile = new File("SaveData\\GameSave\\" + selectGame.getValue());
		if (!saveFile.delete()) {
			throw new IOException("Failed to delete file" + saveFile.toString());
		}
		selectGame.getItems().remove(selectGame.getValue());
		selectGame.getSelectionModel().selectFirst();
		yesButton.setVisible(false);
		noButton.setVisible(false);
		confirm.setText("");
	}
}
