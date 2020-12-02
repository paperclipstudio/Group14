package FrontEnd;

import BackEnd.GameLoad;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import javax.annotation.Generated;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/***
 * Screen to load a previously saved game.
 * @author Christian Sanger
 */
public class LoadGameController implements Initializable {
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
		for (String game : games) {
			selectGame.getItems().add(game);
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
	public void onBackButton() throws IOException {
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("MenuScreen");
	}

	/***
	 * Continues to load game.
	 * Called by start button.
	 */
	public void onPlayButton() throws IOException {
		Main.setLoadedGameFile(true);
		Main.setLoadFile(selectGame.getValue());
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("GameScreen");
	}
	public  void onDeleteButton() {
		yesButton.setVisible(true);
		noButton.setVisible(true);
		confirm.setText("Are you sure?");
	}
	public  void onNoButton() {
		yesButton.setVisible(false);
		noButton.setVisible(false);
		confirm.setText("");
	}
	public void onYesButton() {
		File saveFile = new File("SaveData\\GameSave\\" + selectGame.getValue());
		saveFile.delete();
		selectGame.getItems().remove(selectGame.getValue());
		selectGame.getSelectionModel().selectFirst();
		yesButton.setVisible(false);
		noButton.setVisible(false);
		confirm.setText("");
	}

}
