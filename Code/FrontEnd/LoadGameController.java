package FrontEnd;

import BackEnd.GameLoad;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String[] games;
		File gameSaveLocation = new File("SaveData\\GameSave");
		games = gameSaveLocation.list();
		for (String game : games) {
			selectGame.getItems().add(game);
		}
		selectGame.getSelectionModel().selectFirst();
	}

	/***
	 * Returns to menu screen
	 * Called my backButton
	 */
	public void onBackButton() {
		WindowLoader wl = new WindowLoader(backButton);
		wl.load("MenuScreen");
	}

	/***
	 * Continues to load game.
	 * Called by start button.
	 */
	public void onPlayButton() {
		try {
			GameLoad.loader(selectGame.getValue());
			Main.setLoadedGameFile(true);
			Main.setLoadFile(selectGame.getValue());
			WindowLoader wl = new WindowLoader(backButton);
			wl.load("GameScreen");
		} catch (FileNotFoundException e) {
			System.out.println("File not fonud please try again");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
