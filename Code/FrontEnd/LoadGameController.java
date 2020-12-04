package FrontEnd;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

/***
 * Screen to load a previously saved game.
 * @author Christian Sanger
 */
public class LoadGameController extends StateLoad {
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
		String[] games;
		File gameSaveLocation = new File("SaveData\\GameSave");
		games = gameSaveLocation.list();
		if (games != null) {
			for (String game : games) {
				selectGame.getItems().add(game);
			}
		}
		selectGame.getSelectionModel().selectFirst();
		yesButton.setVisible(false);
		noButton.setVisible(false);
		confirm.setText("");

	}

	/***
	 * Returns to menu screen
	 * Called my backButton
	 */
	public void onBackButton() {
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
		getInitData().put("loadFile", selectGame.getValue());
		wl.load("GameScreen", getInitData());
	}

	/**
	 * Double checks that user wants to delete file
	 */
	public void onDeleteButton() {
		yesButton.setVisible(true);
		noButton.setVisible(true);
		confirm.setText("Are you sure?");
	}

	/**
	 * End deleting game save mode without deleting game
	 */
	public void onNoButton() {
		yesButton.setVisible(false);
		noButton.setVisible(false);
		confirm.setText("");
	}

	/**
	 * Deletes the selected game
	 * @throws IOException if game failed to delete
	 */
	public void onYesButton() throws IOException {
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
